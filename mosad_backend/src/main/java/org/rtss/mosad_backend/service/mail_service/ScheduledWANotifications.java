package org.rtss.mosad_backend.service.mail_service;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.rtss.mosad_backend.entity.user_management.UserContacts;
import org.rtss.mosad_backend.repository.credit_repository.RepaymentRepository;
import org.rtss.mosad_backend.service.credit_management.CreditService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Component
public class ScheduledWANotifications {

    private final WhatsAppNotificationService whatsAppNotificationService;
    private final CreditService creditService;
    private final RepaymentRepository  repaymentRepository;

    public ScheduledWANotifications(WhatsAppNotificationService whatsAppNotificationService, CreditService creditService, RepaymentRepository repaymentRepository) {
        this.whatsAppNotificationService = whatsAppNotificationService;
        this.creditService = creditService;
        this.repaymentRepository = repaymentRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void creditReminder() {

        LocalDate dueDate=LocalDate.now().plusDays(7);
        // Format the date as YYYY-MM-DD
        String formattedDueDate = dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        List<Credit> credits=creditService.getCreditsBtDueDate(formattedDueDate);
        for(Credit credit:credits){
            if(credit.getCompleted()){
                continue;
            }
            double totalRepayed=0;
            double balance=credit.getBalance();
            Set<Repayment> repayments=repaymentRepository.findRepaymentsByCredit(credit);

            for(Repayment repayment:repayments){
                totalRepayed=totalRepayed+repayment.getAmount();
            }
            double rb=balance-totalRepayed;

            String customerName=null;
            String contactNumber=null;
            if(credit.getCustomer()!=null){
                customerName=credit.getCustomer().getCustomerName();
                contactNumber=credit.getCustomer().getCustomerContact().getContactNumber();
            }
            else{
                customerName=credit.getUser().getFirstName();
                Set<UserContacts> contacts=credit.getUser().getUserContacts();
                contactNumber=contacts.iterator().next().getContactNum();

            }

            String remainingBalance="Rs."+ rb;


            whatsAppNotificationService.sendCreditReminder(contactNumber,customerName,remainingBalance,formattedDueDate);
        }

    }

}

package org.rtss.mosad_backend.service.mail_service;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.NotificationDTO;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.entity.user_management.UserContacts;
import org.rtss.mosad_backend.repository.credit_repository.RepaymentRepository;
import org.rtss.mosad_backend.service.NotificationService;
import org.rtss.mosad_backend.service.credit_management.CreditService;

import org.rtss.mosad_backend.service.stock_management_service.ItemService;
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
    private final NotificationService notificationService;
    private final ItemService itemService;

    public ScheduledWANotifications(WhatsAppNotificationService whatsAppNotificationService, CreditService creditService, RepaymentRepository repaymentRepository, NotificationService notificationService, ItemService itemService) {
        this.whatsAppNotificationService = whatsAppNotificationService;
        this.creditService = creditService;
        this.repaymentRepository = repaymentRepository;
        this.notificationService = notificationService;
        this.itemService = itemService;
    }

    //@Scheduled(cron = "0 0 0 * * *")
    //@Scheduled(fixedRate = 5000)
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

            String messageForOwner=customerName +" should pay "+remainingBalance+" by "+formattedDueDate;
            ResponseDTO ndto=notificationService.addNotification(new NotificationDTO("Credit Reminder",messageForOwner));



            whatsAppNotificationService.sendCreditReminder(contactNumber,customerName,remainingBalance,formattedDueDate);
        }

    }

    //@Scheduled(fixedRate = 5000)
    public void lowStockNotification(){
        List<Item> items=itemService.stockQty(10);
        for(Item item:items){
            String message=item.getItemName()+" is running low in stock.";
            ResponseDTO ndto=notificationService.addNotification(new NotificationDTO("Low Stock Notification",message));

        }
    }



    //@Scheduled(fixedRate = 3000)
    public void sendHelloWorldTemplate(){
        whatsAppNotificationService.sendHelloWorldTemplate("94717529331");
    }

}

package org.rtss.mosad_backend.service.retail_management;
import org.rtss.mosad_backend.dto.retail_management.IncompleteTransactionsDTO;
import org.rtss.mosad_backend.dto.retail_management.PaymentHistoryDTO;
import org.rtss.mosad_backend.dto.retail_management.PurchaseHistoryDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetailService {

    private final BillRepository billRepository;
    private final UsersRepo userRepository;


    public RetailService(BillRepository billRepository, UsersRepo userRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
    }

    //Payment history
    /*public List<PaymentHistoryDTO> getPaymentHistory(String username, Long billId) {
        Optional<Users> retailUser = userRepository.findByUsername(username);
        if (retailUser.isEmpty()) {
            throw new IllegalArgumentException("User is not a retail user");
        }

        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isEmpty()) {
            throw new IllegalArgumentException("Bill not found");
        }

        Bill fetchedBill = bill.get();
        return List.of(new PaymentHistoryDTO(
                fetchedBill.getDate(),
                fetchedBill.getItems().stream().map(BillItem::getDescription).collect(Collectors.joining(", ")),
                getPaymentMethod(fetchedBill),
                fetchedBill.getTotalAmount()
        ));
    }

    private String getPaymentMethod(Bill bill) {
        if (bill.getBalance() == 0) {
            return "Completed";
        }
        return "Credit";
    }


    //Purchase History


    public List<PurchaseHistoryDTO> getPurchaseHistory(String username) {
        Optional<Users> retailUser = userRepository.findByUsername(username);
        if (retailUser.isEmpty()) {
            throw new IllegalArgumentException("User is not a retail user");
        }

        List<Bill> bills = billRepository.findAll();
        List<PurchaseHistoryDTO> purchaseHistory = new ArrayList<>();
        for (Bill bill : bills) {
            for (BillItem item : bill.getItems()) {
                purchaseHistory.add(new PurchaseHistoryDTO(
                        (Date) bill.getDate(),
                        item.getDescription(),
                        item.getQuantity(),
                        item.getQuantity() * item.getUnitPrice()
                ));
            }
        }
        return purchaseHistory;
    }





    //Incomple Transaction

    public List<IncompleteTransactionsDTO> getIncompleteTransactions(String username) {
        Optional<Users> retailUser = userRepository.findByUsername(username);
        if (retailUser.isEmpty()) {
            throw new IllegalArgumentException("User is not a retail user");
        }

        List<Bill> bills = billRepository.findAll();
        return bills.stream()
                .filter(bill -> bill.getBalance() > 0)
                .map(bill -> new IncompleteTransactionsDTO(
                        bill.getDate(),
                        bill.getItems().stream().map(BillItem::getDescription).collect(Collectors.joining(", ")),
                        bill.getBalance(),
                        calculateDueDate(bill.getDate())
                ))
                .collect(Collectors.toList());
    }

    private Date calculateDueDate(Date date) {

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dueDate = localDate.plusDays(30);
        return (Date) Date.from(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }*/

}

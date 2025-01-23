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
    public List<PaymentHistoryDTO> getPaymentHistory(String username) {
        // Fetch the logged-in user
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User is not a retail user"));
        // Check if the logged-in user is an admin
        if ("admin".equalsIgnoreCase(user.getUsername())) {
            // Admin: Fetch all users' payment history
            List<Bill> allBills = billRepository.findAll();
            return allBills.stream()
                    .map(bill -> new PaymentHistoryDTO(
                            bill.getDate(),
                            bill.getItems().stream()
                                    .map(BillItem::getDescription)
                                    .collect(Collectors.joining(", ")),
                            returnPaymentStatus(bill),
                            bill.getTotalAmount()
                    ))
                    .collect(Collectors.toList());
        } else {
            // Regular user: Fetch only their payment history
            List<Bill> userBills = billRepository.findByUser(user); // Assuming this method exists
            return userBills.stream()
                    .map(bill -> new PaymentHistoryDTO(
                            bill.getDate(),
                            bill.getItems().stream()
                                    .map(BillItem::getDescription)
                                    .collect(Collectors.joining(", ")),
                            returnPaymentStatus(bill),
                            bill.getTotalAmount()
                    ))
                    .collect(Collectors.toList());
        }
    }
    private String returnPaymentStatus(Bill bill) {
        if (bill.getBalance() == 0) {
            return "Completed";
        }
        return "Credit";
    }
    //Purchase History
    public List<PurchaseHistoryDTO> getPurchaseHistory(String username) {
        // Fetch the logged-in user
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User is not a retail user"));
        // Check if the logged-in user is an admin
        if ("admin".equalsIgnoreCase(user.getUsername())) {
            // Admin: Fetch all users' purchase history
            List<Bill> allBills = billRepository.findAll();
            return allBills.stream()
                    .flatMap(bill -> bill.getItems().stream()
                            .map(item -> new PurchaseHistoryDTO(
                                    bill.getDate(),
                                    item.getDescription(),
                                    item.getQuantity(),
                                    item.getQuantity() * item.getUnitPrice()
                            )))
                    .collect(Collectors.toList());
        } else {
            // Regular user: Fetch only their purchase history
            List<Bill> userBills = billRepository.findByUser(user); // Assuming this method exists
            return userBills.stream()
                    .flatMap(bill -> bill.getItems().stream()
                            .map(item -> new PurchaseHistoryDTO(
                                    bill.getDate(),
                                    item.getDescription(),
                                    item.getQuantity(),
                                    item.getQuantity() * item.getUnitPrice()
                            )))
                    .collect(Collectors.toList());
        }
    }
    // IncompleteTransaction
    public List<IncompleteTransactionsDTO> getIncompleteTransactions(String username) {
        // Fetch the logged-in user to check their role
        Optional<Users> retailUser = userRepository.findByUsername(username);
        if (retailUser.isEmpty()) {
            throw new IllegalArgumentException("User is not a retail user");
        }
        Users user = retailUser.get();
        // Check if the logged-in user is an admin
        if ("admin".equalsIgnoreCase(user.getUsername())) {
            // Admin: Fetch all users' transactions
            List<Bill> allBills = billRepository.findAll();
            return allBills.stream()
                    .filter(bill -> bill.getBalance() > 0)
                    .map(bill -> new IncompleteTransactionsDTO(
                            bill.getDate(), // Directly use the date
                            bill.getItems().stream()
                                    .map(BillItem::getDescription)
                                    .collect(Collectors.joining(", ")),
                            bill.getBalance(),
                            calculateDueDate(bill.getDate()) // Directly use the date
                    ))
                    .collect(Collectors.toList());
        } else {
            // Regular user: Fetch only their transactions
            List<Bill> userBills = billRepository.findByUser(user); // Assuming this method exists
            return userBills.stream()
                    .filter(bill -> bill.getBalance() > 0)
                    .map(bill -> new IncompleteTransactionsDTO(
                            bill.getDate(), // Directly use the date
                            bill.getItems().stream()
                                    .map(BillItem::getDescription)
                                    .collect(Collectors.joining(", ")),
                            bill.getBalance(),
                            calculateDueDate(bill.getDate()) // Directly use the date
                    ))
                    .collect(Collectors.toList());
        }
    }
    private Date calculateDueDate(Date date) {
        // Convert java.util.Date to LocalDate
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        // Add 30 days
        LocalDate dueDate = localDate.plusDays(30);
        // Convert back to java.util.Date
        return Date.from(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

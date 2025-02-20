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
    public static final String RETAIL_USER_NOT_FOUND = "User is not a retail user";
    public static final String ADMIN = "admin";
    private final BillRepository billRepository;
    private final UsersRepo userRepository;
    public RetailService(BillRepository billRepository, UsersRepo userRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
    }
//    public List<PaymentHistoryDTO> getPaymentHistory(String username) {
//        // Fetch the logged-in user
//        Users user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException(RETAIL_USER_NOT_FOUND));
//        // Check if the logged-in user is an admin
//        if (ADMIN.equalsIgnoreCase(user.getUsername())) {
//            // Admin: Fetch all users' payment history
//            List<Bill> allBills = billRepository.findAll();
//            return allBills.stream()
//                    .map(bill -> new PaymentHistoryDTO(
//                            bill.getDate(),
//                            bill.getBillItems().stream()
//                                    .map(BillItem::getDescription)
//                                    .collect(Collectors.joining(", ")),
//                            returnPaymentStatus(bill),
//                            bill.getTotalAmount()
//                    ))
//                    .toList();
//        } else {
//            // Regular user: Fetch only their payment history
//            List<Bill> userBills = billRepository.findBillByUser(user); // Assuming this method exists
//            return userBills.stream()
//                    .map(bill -> new PaymentHistoryDTO(
//                            bill.getDate(),
//                            bill.getBillItems().stream()
//                                    .map(BillItem::getDescription)
//                                    .collect(Collectors.joining(", ")),
//                            returnPaymentStatus(bill),
//                            bill.getTotalAmount()
//                    ))
//                    .toList();
//        }
//    }
//    private String returnPaymentStatus(Bill bill) {
//        if (bill.getBalance() == 0) {
//            return "Completed";
//        }
//        return "Credit";
//    }

    public List<PaymentHistoryDTO> getPaymentHistory(String username) {
        // Fetch the logged-in user
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(RETAIL_USER_NOT_FOUND));

        List<Bill> bills;

        // Check if the logged-in user is an admin
        if (ADMIN.equalsIgnoreCase(user.getUsername())) {
            // Admin: Fetch all retail customers' payment history
            // Fetch only bills with userId (retail customer bills where userId is not null)
            bills = billRepository.findAll().stream()
                    .filter(bill -> bill.getUser() != null && bill.getCustomer() == null)
                    .collect(Collectors.toList());
        } else {
            // Regular user: Fetch only their payment history
            bills = billRepository.findBillByUser(user); // Assuming this method exists in billRepository
        }

        return bills.stream()
                .map(bill -> new PaymentHistoryDTO(
                        bill.getDate(),
                        combineUserName(bill.getUser()), // Use the new method to combine the name
                        returnPaymentStatus(bill),
                        bill.getTotalAmount()
                ))
                .toList();
    }


    private String combineUserName(Users user) {
        if (user != null) {
            return user.getFirstName() + " " + user.getLastName();
        }
        return "Unknown"; // Return "Unknown" if the user is null
    }



    private String returnPaymentStatus(Bill bill) {
        // Null check for the bill object
        if (bill == null) {
            throw new IllegalArgumentException("Bill cannot be null");
        }

        // Null checks for the balance, advance, and totalAmount
        if (bill.getBalance() == null) {
            throw new IllegalArgumentException("Balance cannot be null for bill ID: " + bill.getBillId());
        }
        if (bill.getAdvance() == null) {
            throw new IllegalArgumentException("Advance cannot be null for bill ID: " + bill.getBillId());
        }
        if (bill.getTotalAmount() == null) {
            throw new IllegalArgumentException("Total Amount cannot be null for bill ID: " + bill.getBillId());
        }

        // Handle various cases based on the balance, advance, and totalAmount
        if (bill.getBalance() == 0 && bill.getAdvance().equals(bill.getTotalAmount())) {
            return "Completed";  // Fully paid with advance equal to total amount
        } else if (bill.getBalance() > 0) {
            return "Credit";  // Outstanding balance remains
        } else if (bill.getBalance() < 0) {
            return "Overpaid";  // Advance was more than total amount
        } else if (bill.getAdvance() < bill.getTotalAmount()) {
            return "Pending";  // The customer hasn't paid enough to cover the full bill
        }

        // Default case (if for some reason the balance is zero but conditions aren't met)
        return "Unknown Status";
    }




    //Purchase History
    public List<PurchaseHistoryDTO> getPurchaseHistory(String username) {
        // Fetch the logged-in user
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(RETAIL_USER_NOT_FOUND));
        // Check if the logged-in user is an admin
        if (ADMIN.equalsIgnoreCase(user.getUsername())) {
            // Admin: Fetch all users' purchase history
            List<Bill> allBills = billRepository.findAll();
            return allBills.stream()
                    .flatMap(bill -> bill.getBillItems().stream()
                            .map(item -> new PurchaseHistoryDTO(
                                    bill.getDate(),
                                    item.getDescription(),
                                    item.getQuantity(),
                                    item.getQuantity() * item.getUnitPrice()
                            )))
                    .toList();
        } else {
            // Regular user: Fetch only their purchase history
            List<Bill> userBills = billRepository.findBillByUser(user); // Assuming this method exists
            return userBills.stream()
                    .flatMap(bill -> bill.getBillItems().stream()
                            .map(item -> new PurchaseHistoryDTO(
                                    bill.getDate(),
                                    item.getDescription(),
                                    item.getQuantity(),
                                    item.getQuantity() * item.getUnitPrice()
                            )))
                    .toList();
        }
    }
    // IncompleteTransaction
//    public List<IncompleteTransactionsDTO> getIncompleteTransactions(String username) {
//        // Fetch the logged-in user to check their role
//        Optional<Users> retailUser = userRepository.findByUsername(username);
//        if (retailUser.isEmpty()) {
//            throw new IllegalArgumentException(RETAIL_USER_NOT_FOUND);
//        }
//        Users user = retailUser.get();
//        // Check if the logged-in user is an admin
//        if (ADMIN.equalsIgnoreCase(user.getUsername())) {
//            // Admin: Fetch all users' transactions
//            List<Bill> allBills = billRepository.findAll();
//            return allBills.stream()
//                    .filter(bill -> bill.getBalance() > 0)
//                    .map(bill -> new IncompleteTransactionsDTO(
//                            bill.getDate(), // Directly use the date
//                            bill.getBillItems().stream()
//                                    .map(BillItem::getDescription)
//                                    .collect(Collectors.joining(", ")),
//                            bill.getBalance(),
//                            calculateDueDate(bill.getDate()) // Directly use the date
//                    ))
//                    .toList();
//        } else {
//            // Regular user: Fetch only their transactions
//            List<Bill> userBills = billRepository.findBillByUser(user); // Assuming this method exists
//            return userBills.stream()
//                    .filter(bill -> bill.getBalance() > 0)
//                    .map(bill -> new IncompleteTransactionsDTO(
//                            bill.getDate(), // Directly use the date
//                            bill.getBillItems().stream()
//                                    .map(BillItem::getDescription)
//                                    .collect(Collectors.joining(", ")),
//                            bill.getBalance(),
//                            calculateDueDate(bill.getDate()) // Directly use the date
//                    ))
//                    .toList();
//        }
//    }
    private Date calculateDueDate(LocalDate date) {
        // Add 30 days
        LocalDate dueDate = date.plusDays(30);
        // Convert back to java.util.Date
        return Date.from(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public List<IncompleteTransactionsDTO> getIncompleteTransactions(String username) {
        // Fetch the logged-in user to check their role
        Optional<Users> retailUser = userRepository.findByUsername(username);
        if (retailUser.isEmpty()) {
            throw new IllegalArgumentException(RETAIL_USER_NOT_FOUND);
        }
        Users user = retailUser.get();

        List<Bill> bills;

        // Check if the logged-in user is an admin
        if (ADMIN.equalsIgnoreCase(user.getUsername())) {
            // Admin: Fetch all retail customers' transactions, excluding completed bills (balance > 0)
            bills = billRepository.findAll().stream()
                    .filter(bill -> bill.getUser() != null && bill.getCustomer() == null && bill.getBalance() > 0)
                    .collect(Collectors.toList());
        } else {
            // Regular user: Fetch only their transactions, excluding completed bills (balance > 0)
            bills = billRepository.findBillByUser(user); // Assuming this method exists in billRepository
            // Filter for only unpaid bills (balance > 0)
            bills = bills.stream()
                    .filter(bill -> bill.getBalance() > 0)
                    .collect(Collectors.toList());
        }

        return bills.stream()
                .map(bill -> new IncompleteTransactionsDTO(
                        bill.getDate(), // Directly use the date
                        combineUserName(bill.getUser()), // Use the new method to combine the name
                        bill.getBalance(),
                        calculateDueDate(bill.getDate()) // Directly use the date
                ))
                .toList();
    }
}

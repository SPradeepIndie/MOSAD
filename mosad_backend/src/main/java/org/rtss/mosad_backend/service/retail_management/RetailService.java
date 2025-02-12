package org.rtss.mosad_backend.service.retail_management;

import org.rtss.mosad_backend.dto.retail_management.IncompleteTransactionsDTO;
import org.rtss.mosad_backend.dto.retail_management.PaymentHistoryDTO;
import org.rtss.mosad_backend.dto.retail_management.PurchaseHistoryDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.rtss.mosad_backend.repository.stock_management_repository.CategoryRepo;
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
    private final CategoryRepo categoryRepo;  // Injecting CategoryRepository

    public RetailService(BillRepository billRepository, UsersRepo userRepository, CategoryRepo categoryRepo) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.categoryRepo = categoryRepo;
    }

    // New method to get all categories
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public List<PaymentHistoryDTO> getPaymentHistory(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User is not a retail user"));

        if ("admin".equalsIgnoreCase(user.getUsername())) {
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
            List<Bill> userBills = billRepository.findByUser(user);
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

    public List<PurchaseHistoryDTO> getPurchaseHistory(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User is not a retail user"));

        if ("admin".equalsIgnoreCase(user.getUsername())) {
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
            List<Bill> userBills = billRepository.findByUser(user);
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

    public List<IncompleteTransactionsDTO> getIncompleteTransactions(String username) {
        Optional<Users> retailUser = userRepository.findByUsername(username);
        if (retailUser.isEmpty()) {
            throw new IllegalArgumentException("User is not a retail user");
        }
        Users user = retailUser.get();

        if ("admin".equalsIgnoreCase(user.getUsername())) {
            List<Bill> allBills = billRepository.findAll();
            return allBills.stream()
                    .filter(bill -> bill.getBalance() > 0)
                    .map(bill -> new IncompleteTransactionsDTO(
                            bill.getDate(),
                            bill.getItems().stream()
                                    .map(BillItem::getDescription)
                                    .collect(Collectors.joining(", ")),
                            bill.getBalance(),
                            calculateDueDate(bill.getDate())
                    ))
                    .collect(Collectors.toList());
        } else {
            List<Bill> userBills = billRepository.findByUser(user);
            return userBills.stream()
                    .filter(bill -> bill.getBalance() > 0)
                    .map(bill -> new IncompleteTransactionsDTO(
                            bill.getDate(),
                            bill.getItems().stream()
                                    .map(BillItem::getDescription)
                                    .collect(Collectors.joining(", ")),
                            bill.getBalance(),
                            calculateDueDate(bill.getDate())
                    ))
                    .collect(Collectors.toList());
        }
    }

    private Date calculateDueDate(Date date) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate dueDate = localDate.plusDays(30);
        return Date.from(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

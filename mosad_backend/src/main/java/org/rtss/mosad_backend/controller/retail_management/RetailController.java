package org.rtss.mosad_backend.controller.retail_management;

import org.rtss.mosad_backend.dto.retail_management.IncompleteTransactionsDTO;
import org.rtss.mosad_backend.dto.retail_management.PaymentHistoryDTO;
import org.rtss.mosad_backend.dto.retail_management.PurchaseHistoryDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.rtss.mosad_backend.service.retail_management.RetailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/retail")
@CrossOrigin(origins = "http://localhost:3000") // Adjust for frontend connection
public class RetailController {

    private final RetailService retailService;

    public RetailController(RetailService retailService) {
        this.retailService = retailService;
    }

    @GetMapping("/paymentHistory")
    public List<PaymentHistoryDTO> getPaymentHistory(@RequestParam String username) {
        return retailService.getPaymentHistory(username);
    }

    @GetMapping("/purchaseHistory")
    public List<PurchaseHistoryDTO> getPurchaseHistory(@RequestParam String username) {
        return retailService.getPurchaseHistory(username);
    }

    @GetMapping("/incompleteTransaction")
    public List<IncompleteTransactionsDTO> getIncompleteTransactions(@RequestParam String username) {
        return retailService.getIncompleteTransactions(username);
    }

    // New endpoint to fetch categories
    @GetMapping("/getAllCategoriesNames")
    public List<Category> getCategories() {
        return retailService.getAllCategories();
    }
}

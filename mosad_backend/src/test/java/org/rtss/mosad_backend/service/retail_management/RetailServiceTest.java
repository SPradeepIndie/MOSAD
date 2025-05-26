//package org.rtss.mosad_backend.service.retail_management;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.rtss.mosad_backend.dto.retail_management.IncompleteTransactionsDTO;
//import org.rtss.mosad_backend.dto.retail_management.PaymentHistoryDTO;
//import org.rtss.mosad_backend.dto.retail_management.PurchaseHistoryDTO;
//import org.rtss.mosad_backend.entity.bill_management.Bill;
//import org.rtss.mosad_backend.entity.bill_management.BillItem;
//import org.rtss.mosad_backend.entity.user_management.Users;
//import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
//import org.rtss.mosad_backend.repository.user_management.UsersRepo;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class RetailServiceTest {
//
//    @InjectMocks
//    private RetailService retailService;
//    @Mock
//    private BillRepository billRepository;
//    @Mock
//    private UsersRepo usersRepo;
//
//    @Test
//    @Disabled
//    void shouldReturnPaymentHistoryForAdmin() {
//        Users admin = new Users();
//        admin.setUsername("admin");
//        when(usersRepo.findByUsername("admin")).thenReturn(Optional.of(admin));
//        Bill mockBill = new Bill();
//        mockBill.setDate();
//        mockBill.setBalance(0.0);
//        mockBill.setTotalAmount(500.0);
//        BillItem mockItem = new BillItem();
//        mockItem.setDescription("Test Item");
//        mockBill.setBillItems();
//        when(billRepository.findAll()).thenReturn(Collections.singletonList(mockBill));
//        List<PaymentHistoryDTO> result = retailService.getPaymentHistory("admin");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Test Item", result.getFirst().getDescription());
//        assertEquals("Completed", result.getFirst().getPaymentStatus());
//        verify(billRepository, times(1)).findAll();
//    }
//    @Test
//    @Disabled
//    void shouldReturnPaymentHistoryForRegularUser() {
//        Users user = new Users();
//        user.setUsername("user1");
//        when(usersRepo.findByUsername("user1")).thenReturn(Optional.of(user));
//        Bill mockBill = new Bill();
//        mockBill.setDate(new Date());
//        mockBill.setBalance(200.0);
//        mockBill.setTotalAmount(500.0);
//        BillItem mockItem = new BillItem();
//        mockItem.setDescription("Test Item");
//        mockBill.setBillItems();
//        when(billRepository.findByUser(user)).thenReturn(Collections.singletonList(mockBill));
//        List<PaymentHistoryDTO> result = retailService.getPaymentHistory("user1");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Test Item", result.getFirst().getDescription());
//        assertEquals("Credit", result.getFirst().getPaymentStatus());
//        verify(billRepository, times(1)).findByUser(user);
//    }
//    @Test
//    void shouldThrowExceptionForUnknownUserInPaymentHistory() {
//        when(usersRepo.findByUsername("unknown")).thenReturn(Optional.empty());
//        Exception exception = assertThrows(IllegalArgumentException.class,
//                () -> retailService.getPaymentHistory("unknown"));
//        assertEquals("User is not a retail user", exception.getMessage());
//    }
//
//    @Test
//    @Disabled
//    void shouldReturnPurchaseHistoryForAdmin() {
//        Users admin = new Users();
//        admin.setUsername("admin");
//        when(usersRepo.findByUsername("admin")).thenReturn(Optional.of(admin));
//        Bill mockBill = new Bill();
//        mockBill.setDate(new Date());
//        BillItem mockItem = new BillItem();
//        mockItem.setDescription("Test Item");
//        mockItem.setQuantity(2);
//        mockItem.setUnitPrice(250.0);
//        mockBill.setBillItems();
//        Items(Collections.singletonList(mockItem));
//        when(billRepository.findAll()).thenReturn(Collections.singletonList(mockBill));
//        List<PurchaseHistoryDTO> result = retailService.getPurchaseHistory("admin");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Test Item", result.getFirst().getProductName());
//        assertEquals(500.0, result.getFirst().getPrice());
//        verify(billRepository, times(1)).findAll();
//    }
//    @Test
//    @Disabled
//    void shouldReturnPurchaseHistoryForUser() {
//        // Arrange
//        Users user = new Users();
//        user.setUsername("user1");
//        when(usersRepo.findByUsername("user1")).thenReturn(Optional.of(user));
//        Bill bill = new Bill();
//        bill.setDate(new Date());
//        BillItem item = new BillItem();
//        item.setDescription("Test Item");
//        item.setQuantity(1);
//        item.setUnitPrice(300.0);
//        bill.setBillItems();
//        ems(Collections.singletonList(item));
//        when(billRepository.findByUser(user)).thenReturn(Collections.singletonList(bill));
//        List<PurchaseHistoryDTO> result = retailService.getPurchaseHistory("user1");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Test Item", result.getFirst().getProductName());
//        assertEquals(300.0, result.getFirst().getPrice());
//        verify(billRepository, times(1)).findByUser(user);
//    }
//    @Test
//    void shouldThrowExceptionForUnknownUserInPurchaseHistory() {
//        when(usersRepo.findByUsername("unknown")).thenReturn(Optional.empty());
//        Exception exception = assertThrows(IllegalArgumentException.class,
//                () -> retailService.getPurchaseHistory("unknown"));
//        assertEquals("User is not a retail user", exception.getMessage());
//    }
//
//    @Test
//    @Disabled
//    void shouldReturnIncompleteTransactionsForAdmin() {
//        Users admin = new Users();
//        admin.setUsername("admin");
//        when(usersRepo.findByUsername("admin")).thenReturn(Optional.of(admin));
//        Bill bill = new Bill();
//        bill.setDate(new Date());
//        bill.setBalance(200.0);
//        BillItem item = new BillItem();
//        item.setDescription("Test Item");
//        bill.setBillItems();
//        when(billRepository.findAll()).thenReturn(Collections.singletonList(bill));
//        List<IncompleteTransactionsDTO> result = retailService.getIncompleteTransactions("admin");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Test Item", result.getFirst().getDescription());
//        assertEquals(200.0, result.getFirst().getBalance());
//        verify(billRepository, times(1)).findAll();
//    }
//    @Test
//    @Disabled
//    void shouldReturnIncompleteTransactionsForUser() {
//        Users user = new Users();
//        user.setUsername("user1");
//        when(usersRepo.findByUsername("user1")).thenReturn(Optional.of(user));
//        Bill mockBill = new Bill();
//        mockBill.setDate(Date);
//        mockBill.setBalance(200.0);
//        BillItem mockItem = new BillItem();
//        mockItem.setDescription("Test Item");
//        mockBill.setBillItems();
//        when(billRepository.findByUser(user)).thenReturn(Collections.singletonList(mockBill));
//        List<IncompleteTransactionsDTO> result = retailService.getIncompleteTransactions("user1");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Test Item", result.getFirst().getDescription());
//        assertEquals(200.0, result.getFirst().getBalance());
//        verify(billRepository, times(1)).findByUser(user);
//    }
//    @Test
//    void shouldThrowExceptionForUnknownUserInIncompleteTransactions() {
//        when(usersRepo.findByUsername("unknown")).thenReturn(Optional.empty());
//        Exception exception = assertThrows(IllegalArgumentException.class,
//                () -> retailService.getIncompleteTransactions("unknown"));
//        assertEquals("User is not a retail user", exception.getMessage());
//    }
//}

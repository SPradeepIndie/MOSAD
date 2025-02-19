package org.rtss.mosad_backend.service.credit_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rtss.mosad_backend.dto.credit_dtos.*;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.CreditDTOMapper;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.credit_repository.CreditRepository;
import org.rtss.mosad_backend.repository.credit_repository.RepaymentRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreditServiceTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private RepaymentRepository repaymentRepository;

    @Mock
    private CreditDTOMapper creditDTOMapper;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreditService creditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCredit_shouldSaveSuccessfully() {
        // Arrange
        CreditDTO creditDTO = new CreditDTO(null, 5000.0, new Date(), 1L);
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("John Doe");

        Credit credit = new Credit();
        credit.setCreditId(1L);
        credit.setBalance(5000.0);
        credit.setDueDate(new Date());
        credit.setCustomer(customer);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(creditDTOMapper.toEntity(any(CreditDTO.class))).thenReturn(credit);
        when(creditRepository.save(any(Credit.class))).thenReturn(credit);
        when(creditDTOMapper.toDTOWithCustomer(any(Credit.class))).thenReturn(new CreditDTO(1L, 5000.0, new Date(), 1L));

        // Act
        ResponseEntity<CreditDTO> response = creditService.saveCredit(creditDTO);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getCustomerId());
        verify(creditRepository, times(1)).save(any(Credit.class));
    }

    @Test
    void saveCredit_shouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        CreditDTO creditDTO = new CreditDTO(null, 5000.0, new Date(), 1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ObjectNotValidException exception = assertThrows(
                ObjectNotValidException.class,
                () -> creditService.saveCredit(creditDTO)
        );

        assertTrue(exception.getErrorMessages().contains("Customer not found"));
    }

    @Test
    void getAllCredits_shouldReturnAllCredits() {
        // Arrange
        Credit credit = new Credit();
        credit.setCreditId(1L);
        credit.setBalance(5000.0);

        List<Credit> credits = List.of(credit);
        when(creditRepository.findAll()).thenReturn(credits);
        when(creditDTOMapper.toDTOList(credits)).thenReturn(List.of(new CreditDTO(1L, 5000.0, new Date(), 1L)));

        // Act
        List<CreditDTO> result = creditService.getAllCredits();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(creditRepository, times(1)).findAll();
    }

    @Test
    void getCreditById_shouldReturnCredit() {
        // Arrange
        Long creditId = 1L;
        Credit credit = new Credit();
        credit.setCreditId(creditId);
        credit.setBalance(5000.0);

        when(creditRepository.findById(creditId)).thenReturn(Optional.of(credit));
        when(creditDTOMapper.toDTOWithCustomer(any(Credit.class))).thenReturn(new CreditDTO(1L, 5000.0, new Date(), 1L));

        // Act
        CreditDTO result = creditService.getCreditById(creditId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getCreditId());
        verify(creditRepository, times(1)).findById(creditId);
    }

    @Test
    void getCreditById_shouldThrowExceptionWhenCreditNotFound() {
        // Arrange
        Long creditId = 1L;

        when(creditRepository.findById(creditId)).thenReturn(Optional.empty());

        // Act & Assert
        ObjectNotValidException exception = assertThrows(
                ObjectNotValidException.class,
                () -> creditService.getCreditById(creditId)
        );

        assertTrue(exception.getErrorMessages().contains("Failed to fetch credit: Credit not found for ID: 1"));
    }
}

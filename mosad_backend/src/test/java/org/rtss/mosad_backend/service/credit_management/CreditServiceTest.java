package org.rtss.mosad_backend.service.credit_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rtss.mosad_backend.dto.credit_dtos.*;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.CreditDTOMapper;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.exceptions.CreditException;
import org.rtss.mosad_backend.exceptions.RepaymentException;
import org.rtss.mosad_backend.repository.credit_repository.CreditRepository;
import org.rtss.mosad_backend.repository.credit_repository.RepaymentRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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
    void testSaveCreditSuccess() {
        CreditDTO creditDTO = new CreditDTO(1L, 500.0, new Date(), 1L);
        Customer customer = new Customer();
        customer.setId(1L);

        Credit credit = new Credit();
        credit.setCreditId(1L);
        credit.setBalance(500.0);
        credit.setDueDate(new Date());
        credit.setCustomer(customer);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(creditDTOMapper.toEntity(creditDTO)).thenReturn(credit);
        when(creditRepository.save(any(Credit.class))).thenReturn(credit);
        when(creditDTOMapper.toDTOWithCustomer(credit)).thenReturn(creditDTO);

        ResponseEntity<CreditDTO> response = creditService.saveCredit(creditDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getCustomerId());
        verify(creditRepository, times(1)).save(any(Credit.class));
    }

    @Test
    void testSaveCreditCustomerNotFound() {
        CreditDTO creditDTO = new CreditDTO(1L, 500.0, new Date(), 1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> creditService.saveCredit(creditDTO));
        assertEquals("Customer not found with ID: 1", exception.getMessage());
        verify(creditRepository, never()).save(any(Credit.class));
    }

    @Test
    void testGetAllCredits() {
        List<Credit> credits = Arrays.asList(new Credit(), new Credit());
        List<CreditDTO> creditDTOs = Arrays.asList(new CreditDTO(), new CreditDTO());

        when(creditRepository.findAll()).thenReturn(credits);
        when(creditDTOMapper.toDTOList(credits)).thenReturn(creditDTOs);

        List<CreditDTO> result = creditService.getAllCredits();

        assertEquals(2, result.size());
        verify(creditRepository, times(1)).findAll();
        verify(creditDTOMapper, times(1)).toDTOList(credits);
    }

    @Test
    void testGetAllCreditsException() {
        when(creditRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(CreditException.class, () -> creditService.getAllCredits());
        assertEquals("Failed to fetch credits: Database error", exception.getMessage());
        verify(creditRepository, times(1)).findAll();
    }

    @Test
    void testAddRepaymentSuccess() {
        RepaymentRequestDTO requestDTO = new RepaymentRequestDTO(1L, new Date(), 100.0);
        Credit credit = new Credit();
        credit.setCreditId(1L);

        Repayment repayment = new Repayment();
        repayment.setRepaymentId(1L);
        repayment.setDate(requestDTO.getDate());
        repayment.setAmount(requestDTO.getAmount());
        repayment.setCredit(credit);

        RepaymentResponseDTO responseDTO = new RepaymentResponseDTO(1L, requestDTO.getDate(), 100.0, 1L);

        when(creditRepository.findById(1L)).thenReturn(Optional.of(credit));
        when(repaymentRepository.save(any(Repayment.class))).thenReturn(repayment);

        ResponseEntity<RepaymentResponseDTO> response = creditService.addRepayment(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getRepaymentId());
        verify(repaymentRepository, times(1)).save(any(Repayment.class));
    }

    @Test
    void testAddRepaymentCreditNotFound() {
        RepaymentRequestDTO requestDTO = new RepaymentRequestDTO(1L, new Date(), 100.0);

        when(creditRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CreditException.class, () -> creditService.addRepayment(requestDTO));
        assertEquals("Credit not found for ID: 1", exception.getMessage());
        verify(repaymentRepository, never()).save(any(Repayment.class));
    }
}

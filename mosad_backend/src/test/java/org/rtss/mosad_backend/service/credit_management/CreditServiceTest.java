package org.rtss.mosad_backend.service.credit_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentRequestDTO;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentResponseDTO;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.CreditDTOMapper;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.repository.credit_repository.CreditRepository;
import org.rtss.mosad_backend.repository.credit_repository.RepaymentRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

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
    void testSaveCredit_Success() {
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setCustomerId(1L);

        Customer customer = new Customer();
        customer.setId(1L);

        Credit credit = new Credit();
        credit.setCreditId(1L);
        credit.setCustomer(customer);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(creditDTOMapper.toEntity(creditDTO)).thenReturn(credit);
        when(creditRepository.save(any(Credit.class))).thenReturn(credit);
        when(creditDTOMapper.toDTOWithCustomer(any(Credit.class))).thenReturn(creditDTO);

        ResponseEntity<CreditDTO> response = creditService.saveCredit(creditDTO);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(creditDTO, response.getBody());
    }

    @Test
    void testSaveCredit_CustomerNotFound() {
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setCustomerId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            creditService.saveCredit(creditDTO);
        });

        assertEquals("Customer not found with ID: 1", exception.getMessage());
    }

    @Test
    void testAddRepayment_Success() {
        RepaymentRequestDTO repaymentRequest = new RepaymentRequestDTO();
        repaymentRequest.setCreditId(1L);
        repaymentRequest.setAmount(100.0);

        Credit credit = new Credit();
        credit.setCreditId(1L);

        Repayment repayment = new Repayment();
        repayment.setRepaymentId(1L);
        repayment.setAmount(100.0);
        repayment.setCredit(credit);

        when(creditRepository.findById(1L)).thenReturn(Optional.of(credit));
        when(repaymentRepository.save(any(Repayment.class))).thenReturn(repayment);

        ResponseEntity<RepaymentResponseDTO> response = creditService.addRepayment(repaymentRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getRepaymentId());
        assertEquals(100.0, response.getBody().getAmount());
    }

    @Test
    void testAddRepayment_CreditNotFound() {
        RepaymentRequestDTO repaymentRequest = new RepaymentRequestDTO();
        repaymentRequest.setCreditId(1L);

        when(creditRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            creditService.addRepayment(repaymentRequest);
        });

        assertEquals("Failed to add repayment: Credit not found for ID: 1", exception.getMessage());
    }
}

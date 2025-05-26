package org.rtss.mosad_backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillDetailsDTO;
import org.rtss.mosad_backend.service.bill_management.BillService;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BillControllerTest {

    @Mock
    private BillService billService;

    @InjectMocks
    private BillController billController;

    private BillDetailsDTO billDetailsDTO;

    @BeforeEach
    public void setUp() {
        billDetailsDTO = new BillDetailsDTO();
        // Initialize billDetailsDTO with necessary data
    }

//    @Test
//    public void testCreateBill() {
//        ResponseDTO expectedResponse = new ResponseDTO();
//        expectedResponse.setMessage("Bill created successfully");
//
//        when(billService.createBill(any(BillDetailsDTO.class), any(), any())).thenReturn(expectedResponse);
//
//        ResponseEntity<ResponseDTO> response = billController.createBill(billDetailsDTO);
//
//        assertEquals(expectedResponse, response.getBody());
//        assertEquals(200, response.getStatusCodeValue());
//    }

    @Test
    public void testGetAllBills() {
        List<BillDetailsDTO> expectedBills = Collections.singletonList(billDetailsDTO);

        when(billService.getAllBills()).thenReturn(expectedBills);

        ResponseEntity<List<BillDetailsDTO>> response = billController.getAllBills();

        assertEquals(expectedBills, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
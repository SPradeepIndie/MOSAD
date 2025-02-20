package org.rtss.mosad_backend.dto_mapper.bill_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BillDTOMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BillDTOMapper billDTOMapper;

    private Bill bill;
    private BillDTO billDTO;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        bill = new Bill();
        bill.setBillId(1L);

        billDTO = new BillDTO();
        billDTO.setBillId(1L);
    }

    @Test
    public void testToDTO() {
        // Mock the behavior of ModelMapper
        when(modelMapper.map(bill, BillDTO.class)).thenReturn(billDTO);

        // Call the method under test
        BillDTO result = billDTOMapper.toDTO(bill);

        // Assert the result
        assertEquals(billDTO.getBillId(), result.getBillId());
    }

    @Test
    public void testToEntity() {
        // Mock the behavior of ModelMapper
        when(modelMapper.map(billDTO, Bill.class)).thenReturn(bill);

        // Call the method under test
        Bill result = billDTOMapper.toEntity(billDTO);

        // Assert the result
        assertEquals(bill.getBillId(), result.getBillId());
    }
}
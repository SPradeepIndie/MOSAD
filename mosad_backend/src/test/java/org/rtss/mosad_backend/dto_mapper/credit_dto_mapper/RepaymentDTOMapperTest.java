package org.rtss.mosad_backend.dto_mapper.credit_dto_mapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentDTO;
import org.rtss.mosad_backend.entity.credit.Repayment;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RepaymentDTOMapperTest {
    private RepaymentDTOMapper repaymentDTOMapper;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        repaymentDTOMapper = new RepaymentDTOMapper(modelMapper);
    }

    @Test
    void shouldMapRepaymentToRepaymentDTO() throws ParseException {
        //Given
        Repayment repayment = new Repayment();
        repayment.setRepaymentId(1L);
        repayment.setAmount(100.0);
        repayment.setDate(new Date());

        //When
        RepaymentDTO repaymentDTO = repaymentDTOMapper.toDTO(repayment);

        //Then
        assertNotNull(repaymentDTO);
        assertEquals(repayment.getRepaymentId(), repaymentDTO.getRepaymentId());
        assertEquals(repayment.getAmount(), repaymentDTO.getAmount());
        assertEquals(repayment.getDate(), repaymentDTO.getDate());
    }

    @Test
    void shouldMapRepaymentDTOToRepayment() {
        //Given
        RepaymentDTO repaymentDTO = new RepaymentDTO();
        repaymentDTO.setRepaymentId(1L);
        repaymentDTO.setAmount(100.0);
        repaymentDTO.setDate(new Date());

        //When
        Repayment repayment = repaymentDTOMapper.toEntity(repaymentDTO);

        //Then
        assertNotNull(repayment);
        assertEquals(repaymentDTO.getRepaymentId(), repayment.getRepaymentId());
        assertEquals(repaymentDTO.getAmount(), repayment.getAmount());
        assertEquals(repaymentDTO.getDate(), repayment.getDate());
    }
}
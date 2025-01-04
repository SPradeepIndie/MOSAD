package org.rtss.mosad_backend.dto_mapper.credit_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.CreditDTOMapper;

class CreditDTOMapperTest {
    private CreditDTOMapper creditDTOMapper;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        creditDTOMapper = new CreditDTOMapper(modelMapper);
    }

    @Test
    void shouldMapCreditToCreditDTO() {
        // Given
        Credit credit = new Credit();
        credit.setCreditId(1L);
        credit.setBalance(1000.0);

        // When
        CreditDTO creditDTO = creditDTOMapper.toDTO(credit);

        // Then
        assertNotNull(creditDTO);
        assertEquals(credit.getCreditId(), creditDTO.getCreditId());
        assertEquals(credit.getBalance(), creditDTO.getBalance());
    }

    @Test
    void shouldMapCreditDTOToCredit() {
        // Given
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setCreditId(1L);
        creditDTO.setBalance(1000.0);

        // When
        Credit credit = creditDTOMapper.toEntity(creditDTO);

        // Then
        assertNotNull(credit);
        assertEquals(creditDTO.getCreditId(), credit.getCreditId());
        assertEquals(creditDTO.getBalance(), credit.getBalance());
    }
}
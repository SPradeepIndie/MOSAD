package org.rtss.mosad_backend.service.credit_management;

import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentDTO;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.CreditDTOMapper;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.RepaymentDTOMapper;
import org.rtss.mosad_backend.repository.credit_repos.CreditRepo;
import org.rtss.mosad_backend.repository.credit_repos.RepaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditService {
    @Autowired
    private CreditRepo creditRepo;

    @Autowired
    private RepaymentRepo repaymentRepo;

    @Autowired
    private CreditDTOMapper creditDTOMapper;

    @Autowired
    private RepaymentDTOMapper repaymentDTOMapper;

    

}

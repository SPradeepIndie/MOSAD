package org.rtss.mosad_backend.dto.bill_dtos;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerDetailsDTO;

import java.util.List;

public class BillDetailsDTO {
    private BillDTO billDTO;
    private CustomerDetailsDTO customerDetailsDTO;
    private List<BillItemDTO> billItemDTO;

    public BillDetailsDTO(BillDTO billDTO, CustomerDetailsDTO customerDetailsDTO, List<BillItemDTO> billItemDTO) {
        this.billDTO = billDTO;
        this.customerDetailsDTO = customerDetailsDTO;
        this.billItemDTO = billItemDTO;
    }

    public BillDetailsDTO() {
    }

    public BillDTO getBillDTO() {
        return billDTO;
    }

    public void setBillDTO(BillDTO billDTO) {
        this.billDTO = billDTO;
    }


    public CustomerDetailsDTO getAddCustomerDTO() {
        return customerDetailsDTO;
    }

    public void setAddCustomerDTO(CustomerDetailsDTO customerDetailsDTO) {
        this.customerDetailsDTO = customerDetailsDTO;
    }

    public List<BillItemDTO> getBillItemDTO() {
        return billItemDTO;
    }

    public void setBillItemDTO(List<BillItemDTO> billItemDTO) {
        this.billItemDTO = billItemDTO;
    }
}

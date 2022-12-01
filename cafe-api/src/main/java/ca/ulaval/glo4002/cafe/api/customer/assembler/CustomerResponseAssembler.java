package ca.ulaval.glo4002.cafe.api.customer.assembler;

import ca.ulaval.glo4002.cafe.api.customer.response.CustomerResponse;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.service.customer.dto.CustomerDTO;

public class CustomerResponseAssembler {
    public CustomerResponse toCustomerResponse(CustomerDTO customerDTO) {
        return new CustomerResponse(customerDTO.name().value(), customerDTO.seatNumber().value(),
            customerDTO.groupName().map(GroupName::value).orElse(null));
    }
}

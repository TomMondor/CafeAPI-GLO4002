package ca.ulaval.glo4002.cafe.api.customer.customerAction.assembler;

import ca.ulaval.glo4002.cafe.api.customer.customerAction.response.CustomerResponse;
import ca.ulaval.glo4002.cafe.application.customer.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;

public class CustomerResponseAssembler {
    public CustomerResponse toCustomerResponse(CustomerDTO customerDTO) {
        return new CustomerResponse(customerDTO.name().value(), customerDTO.seatNumber().value(),
            customerDTO.groupName().map(GroupName::value).orElse(null));
    }
}

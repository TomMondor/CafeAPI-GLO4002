package ca.ulaval.glo4002.cafe.application.customer.parameter;

import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;

public record CheckInCustomerParams(CustomerId customerId, CustomerName customerName, Optional<GroupName> groupName) {
    public CheckInCustomerParams(String customerId, String customerName, String groupName) {
        this(new CustomerId(customerId), new CustomerName(customerName), Optional.ofNullable(groupName != null ? new GroupName(groupName) : null));
    }

    public static CheckInCustomerParams from(String customerId, String customerName, String groupName) {
        return new CheckInCustomerParams(customerId, customerName, groupName);
    }
}

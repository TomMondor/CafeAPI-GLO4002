package ca.ulaval.glo4002.cafe.application.customer.parameter;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;

public record CheckOutCustomerParams(CustomerId customerId) {
    public CheckOutCustomerParams(String customerId) {
        this(new CustomerId(customerId));
    }

    public static CheckOutCustomerParams from(String customerId) {
        return new CheckOutCustomerParams(customerId);
    }
}

package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidCustomerIdException;

public record CustomerId(String value) {
    public CustomerId {
        if (value.isEmpty()) {
            throw new InvalidCustomerIdException();
        }
    }
}

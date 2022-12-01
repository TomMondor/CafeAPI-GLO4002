package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidCustomerNameException;

public record CustomerName(String value) {
    public CustomerName {
        if (value.isEmpty()) {
            throw new InvalidCustomerNameException();
        }
    }
}

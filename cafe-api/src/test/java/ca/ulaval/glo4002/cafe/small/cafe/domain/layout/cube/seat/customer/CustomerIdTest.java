package ca.ulaval.glo4002.cafe.small.cafe.domain.layout.cube.seat.customer;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerIdTest {
    @Test
    public void givenEmptyCustomerId_whenCreatingCustomerId_shouldThrowInvalidCustomerIdException() {
        assertThrows(InvalidCustomerIdException.class, () -> new CustomerId(""));
    }
}

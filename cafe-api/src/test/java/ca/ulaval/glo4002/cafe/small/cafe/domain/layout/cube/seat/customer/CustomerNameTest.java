package ca.ulaval.glo4002.cafe.small.cafe.domain.layout.cube.seat.customer;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidCustomerNameException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerNameTest {
    @Test
    public void givenEmptyCustomerName_whenCreatingCustomerName_shouldThrowInvalidCustomerNameException() {
        assertThrows(InvalidCustomerNameException.class, () -> new CustomerName(""));
    }
}

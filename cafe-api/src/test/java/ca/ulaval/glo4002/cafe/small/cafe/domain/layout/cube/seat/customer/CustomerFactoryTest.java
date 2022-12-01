package ca.ulaval.glo4002.cafe.small.cafe.domain.layout.cube.seat.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerFactoryTest {
    private static final CustomerName CUSTOMER_NAME = new CustomerName("Bob Bisonette");
    private static final CustomerId CUSTOMER_ID = new CustomerId("ABC273031");

    private CustomerFactory customerFactory;

    @BeforeEach
    public void createFactory() {
        customerFactory = new CustomerFactory();
    }

    @Test
    public void whenCreatingCustomer_shouldHaveName() {
        Customer customer = customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME);

        assertEquals(CUSTOMER_NAME, customer.getName());
    }

    @Test
    public void whenCreatingCustomer_shouldHaveID() {
        Customer customer = customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME);

        assertEquals(CUSTOMER_ID, customer.getId());
    }
}

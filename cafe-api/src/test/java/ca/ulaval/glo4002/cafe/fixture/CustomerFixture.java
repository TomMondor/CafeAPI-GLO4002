package ca.ulaval.glo4002.cafe.fixture;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;

public class CustomerFixture {
    private CustomerName customerName = new CustomerName("Bob Bissonette");
    private CustomerId customerId = new CustomerId("defaultID");

    public CustomerFixture withCustomerName(CustomerName customerName) {
        this.customerName = customerName;
        return this;
    }

    public CustomerFixture withCustomerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public Customer build() {
        return new Customer(customerId, customerName);
    }
}

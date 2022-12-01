package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;

public class CustomerFixture {
    private CustomerName customerName = new CustomerName("Bob Bissonette");
    private CustomerId customerId = new CustomerId("defaultID");
    private Order order = new Order(List.of());

    public CustomerFixture withCustomerName(CustomerName customerName) {
        this.customerName = customerName;
        return this;
    }

    public CustomerFixture withCustomerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public CustomerFixture withOrder(Order order) {
        this.order = order;
        return this;
    }

    public Customer build() {
        Customer customer = new Customer(customerId, customerName);
        customer.placeOrder(order);
        return customer;
    }
}

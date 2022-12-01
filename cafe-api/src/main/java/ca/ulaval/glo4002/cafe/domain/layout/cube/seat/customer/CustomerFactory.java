package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer;

public class CustomerFactory {
    public Customer createCustomer(CustomerId customerId, CustomerName customerName) {
        return new Customer(customerId, customerName);
    }
}

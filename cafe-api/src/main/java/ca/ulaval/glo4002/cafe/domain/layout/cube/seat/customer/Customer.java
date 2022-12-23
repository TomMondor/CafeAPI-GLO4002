package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer;

public class Customer {
    private final CustomerId id;
    private final CustomerName name;

    public Customer(CustomerId customerId, CustomerName customerName) {
        this.id = customerId;
        this.name = customerName;
    }

    public CustomerId getId() {
        return id;
    }

    public CustomerName getName() {
        return name;
    }
}

package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.Location;
import ca.ulaval.glo4002.cafe.domain.TipRate;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;

public class Customer {
    private final CustomerId id;
    private final CustomerName name;
    private final BillFactory billFactory;
    private Order order = new Order(List.of());

    public Customer(CustomerId customerId, CustomerName customerName) {
        this.id = customerId;
        this.name = customerName;
        this.billFactory = new BillFactory();
    }

    public CustomerId getId() {
        return id;
    }

    public CustomerName getName() {
        return name;
    }

    public Order getOrder() {
        return order;
    }

    public void placeOrder(Order otherOrder) {
        order = order.addAll(otherOrder);
    }

    public Bill createBill(Location location, TipRate groupTipRate, boolean isInGroup) {
        return billFactory.createBill(order, location, groupTipRate, isInGroup);
    }
}

package ca.ulaval.glo4002.cafe.application.customer.parameter;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.PendingOrder;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;

public record CustomerOrderParams(CustomerId customerId, PendingOrder order) {
    public CustomerOrderParams(String customerId, List<String> orders) {
        this(new CustomerId(customerId), new PendingOrder(orders.stream().map(CoffeeName::new).collect(Collectors.toList())));
    }

    public static CustomerOrderParams from(String customerId, List<String> orders) {
        return new CustomerOrderParams(customerId, orders);
    }
}

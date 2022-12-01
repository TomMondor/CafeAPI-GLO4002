package ca.ulaval.glo4002.cafe.service.customer.parameter;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;

public record CustomerOrderParams(CustomerId customerId, Order order) {
    public CustomerOrderParams(String customerId, List<String> orders) {
        this(new CustomerId(customerId), new Order(orders.stream().map(order -> new Coffee(CoffeeType.fromString(order))).collect(Collectors.toList())));
    }

    public static CustomerOrderParams from(String customerId, List<String> orders) {
        return new CustomerOrderParams(customerId, orders);
    }
}

package ca.ulaval.glo4002.cafe.application.customer.dto;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.order.Order;

public record OrderDTO(List<Coffee> coffees) {
    public static OrderDTO fromOrder(Order order) {
        return new OrderDTO(order.items().stream().toList());
    }
}

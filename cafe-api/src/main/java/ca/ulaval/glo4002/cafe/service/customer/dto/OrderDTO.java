package ca.ulaval.glo4002.cafe.service.customer.dto;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;

public record OrderDTO(List<Coffee> coffees) {
    public static OrderDTO fromOrder(Order order) {
        return new OrderDTO(order.items().stream().toList());
    }
}

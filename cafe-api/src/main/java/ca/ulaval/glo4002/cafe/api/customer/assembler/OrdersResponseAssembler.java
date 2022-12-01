package ca.ulaval.glo4002.cafe.api.customer.assembler;

import ca.ulaval.glo4002.cafe.api.customer.response.OrdersResponse;
import ca.ulaval.glo4002.cafe.service.customer.dto.OrderDTO;

public class OrdersResponseAssembler {
    public OrdersResponse toOrdersResponse(OrderDTO orderDTO) {
        return new OrdersResponse(orderDTO.coffees().stream().map(coffee -> coffee.coffeeType().toString()).toList());
    }
}

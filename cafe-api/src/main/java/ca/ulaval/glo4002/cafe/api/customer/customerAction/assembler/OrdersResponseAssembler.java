package ca.ulaval.glo4002.cafe.api.customer.customerAction.assembler;

import ca.ulaval.glo4002.cafe.api.customer.customerAction.response.OrdersResponse;
import ca.ulaval.glo4002.cafe.application.customer.dto.OrderDTO;

public class OrdersResponseAssembler {
    public OrdersResponse toOrdersResponse(OrderDTO orderDTO) {
        return new OrdersResponse(orderDTO.coffees().stream().map(coffee -> coffee.name().value()).toList());
    }
}

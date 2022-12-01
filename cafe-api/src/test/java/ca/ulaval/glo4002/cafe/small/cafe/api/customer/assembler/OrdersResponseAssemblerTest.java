package ca.ulaval.glo4002.cafe.small.cafe.api.customer.assembler;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.assembler.OrdersResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.response.OrdersResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.CoffeeType;
import ca.ulaval.glo4002.cafe.fixture.OrderFixture;
import ca.ulaval.glo4002.cafe.service.customer.dto.OrderDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdersResponseAssemblerTest {
    private static final Coffee AN_AMERICANO_COFFEE = new Coffee(CoffeeType.Americano);
    private static final Coffee A_DARK_ROAST_COFFEE = new Coffee(CoffeeType.DarkRoast);

    private OrdersResponseAssembler ordersResponseAssembler;

    @BeforeEach
    public void createAssembler() {
        ordersResponseAssembler = new OrdersResponseAssembler();
    }

    @Test
    public void whenAssemblingOrdersResponse_shouldReturnOrdersResponseWithItemsInRightOrder() {
        OrderDTO orderDTO = OrderDTO.fromOrder(new OrderFixture().withItems(List.of(AN_AMERICANO_COFFEE, A_DARK_ROAST_COFFEE)).build());

        OrdersResponse actualOrderResponse = ordersResponseAssembler.toOrdersResponse(orderDTO);

        assertEquals(List.of("Americano", "Dark Roast"), actualOrderResponse.orders());
    }
}

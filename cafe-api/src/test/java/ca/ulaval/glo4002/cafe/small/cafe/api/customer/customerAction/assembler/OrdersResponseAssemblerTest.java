package ca.ulaval.glo4002.cafe.small.cafe.api.customer.customerAction.assembler;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.customerAction.assembler.OrdersResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.customerAction.response.OrdersResponse;
import ca.ulaval.glo4002.cafe.application.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.fixture.CoffeeFixture;
import ca.ulaval.glo4002.cafe.fixture.OrderFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdersResponseAssemblerTest {
    private static final Coffee AN_AMERICANO_COFFEE = new CoffeeFixture().withAmericano().build();
    private static final Coffee A_DARK_ROAST_COFFEE = new CoffeeFixture().withDarkRoast().build();

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

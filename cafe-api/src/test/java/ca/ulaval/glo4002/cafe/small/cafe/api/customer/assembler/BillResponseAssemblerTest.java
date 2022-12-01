package ca.ulaval.glo4002.cafe.small.cafe.api.customer.assembler;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.assembler.BillResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.response.BillResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.fixture.BillFixture;
import ca.ulaval.glo4002.cafe.service.customer.dto.BillDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillResponseAssemblerTest {
    private static final Order A_COFFEE_ORDER = new Order(
        List.of(new Coffee(CoffeeType.Espresso), new Coffee(CoffeeType.Espresso), new Coffee(CoffeeType.Latte), new Coffee(CoffeeType.Americano)));

    private BillResponseAssembler billResponseAssembler;

    @BeforeEach
    public void createAssembler() {
        billResponseAssembler = new BillResponseAssembler();
    }

    @Test
    public void givenBillDTO_whenAssemblingBillResponse_shouldAssembleBillResponseWithCoffeeTypeListInSameOrder() {
        BillDTO billDTO = BillDTO.fromBill(new BillFixture().withCoffeeOrder(A_COFFEE_ORDER).build());

        BillResponse actualBillResponse = billResponseAssembler.toBillResponse(billDTO);

        assertEquals(actualBillResponse.orders(), A_COFFEE_ORDER.items().stream().map(coffee -> coffee.coffeeType().toString()).toList());
    }

    @Test
    public void givenAmountWithMoreThanTwoDecimal_whenAssemblingBillResponse_shouldAssembleBillAmountsRoundedUp() {
        Amount anAmountWithMoreThanTwoDecimal = new Amount(4.91001f);
        BillDTO billDTO = BillDTO.fromBill(new BillFixture().withSubtotal(anAmountWithMoreThanTwoDecimal).build());

        BillResponse actualBillResponse = billResponseAssembler.toBillResponse(billDTO);

        assertEquals(4.92f, actualBillResponse.subtotal());
    }
}

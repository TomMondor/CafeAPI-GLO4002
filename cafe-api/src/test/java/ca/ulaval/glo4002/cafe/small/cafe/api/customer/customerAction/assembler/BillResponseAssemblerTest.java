package ca.ulaval.glo4002.cafe.small.cafe.api.customer.customerAction.assembler;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.customerAction.assembler.BillResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.customerAction.response.BillResponse;
import ca.ulaval.glo4002.cafe.application.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.domain.Amount;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.fixture.BillFixture;
import ca.ulaval.glo4002.cafe.fixture.CoffeeFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillResponseAssemblerTest {
    private static final Order A_COFFEE_ORDER = new Order(List.of(
        new CoffeeFixture().withEspresso().build(),
        new CoffeeFixture().withEspresso().build(),
        new CoffeeFixture().withLatte().build(),
        new CoffeeFixture().withAmericano().build()));

    private BillResponseAssembler billResponseAssembler;

    @BeforeEach
    public void createAssembler() {
        billResponseAssembler = new BillResponseAssembler();
    }

    @Test
    public void givenBillDTO_whenAssemblingBillResponse_shouldAssembleBillResponseWithCoffeeTypeListInSameOrder() {
        BillDTO billDTO = BillDTO.fromBill(new BillFixture().withCoffeeOrder(A_COFFEE_ORDER).build());

        BillResponse actualBillResponse = billResponseAssembler.toBillResponse(billDTO);

        assertEquals(actualBillResponse.orders(), A_COFFEE_ORDER.items().stream().map(coffee -> coffee.name().value()).toList());
    }

    @Test
    public void givenAmountWithMoreThanTwoDecimal_whenAssemblingBillResponse_shouldAssembleBillAmountsRoundedUp() {
        Amount anAmountWithMoreThanTwoDecimal = new Amount(4.91001f);
        BillDTO billDTO = BillDTO.fromBill(new BillFixture().withSubtotal(anAmountWithMoreThanTwoDecimal).build());

        BillResponse actualBillResponse = billResponseAssembler.toBillResponse(billDTO);

        assertEquals(4.92f, actualBillResponse.subtotal());
    }
}

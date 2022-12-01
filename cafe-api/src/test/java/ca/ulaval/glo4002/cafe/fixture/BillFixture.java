package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;

public class BillFixture {
    private Order coffeeOrder =
        new Order(List.of(new Coffee(CoffeeType.Americano), new Coffee(CoffeeType.Espresso), new Coffee(CoffeeType.Latte)));
    private Amount subtotal = new Amount(10.0f);
    private Amount taxes = new Amount(1.0f);
    private Amount tip = new Amount(2.0f);

    public BillFixture withCoffeeOrder(Order coffeeOrder) {
        this.coffeeOrder = coffeeOrder;
        return this;
    }

    public BillFixture withSubtotal(Amount subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public BillFixture withTaxes(Amount taxes) {
        this.taxes = taxes;
        return this;
    }

    public BillFixture withTip(Amount tip) {
        this.tip = tip;
        return this;
    }

    public Bill build() {
        return new Bill(coffeeOrder, subtotal, taxes, tip);
    }
}

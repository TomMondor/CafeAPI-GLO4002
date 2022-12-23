package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.sale.bill.Bill;

public class BillFixture {
    private Order coffeeOrder = new Order(List.of(
        new Coffee(new CoffeeName("Americano"), new Amount(2), new Recipe(List.of())),
        new Coffee(new CoffeeName("Espresso"), new Amount(3), new Recipe(List.of())),
        new Coffee(new CoffeeName("Latte"), new Amount(2.5f), new Recipe(List.of()))));
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

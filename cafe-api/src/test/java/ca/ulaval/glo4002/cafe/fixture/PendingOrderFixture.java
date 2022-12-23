package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.PendingOrder;

public class PendingOrderFixture {
    private List<CoffeeName> items = List.of(new CoffeeName("Americano"), new CoffeeName("Cappuccino"));

    public PendingOrderFixture withItems(List<CoffeeName> items) {
        this.items = items;
        return this;
    }

    public PendingOrderFixture fromOrder(OrderFixture orderFixture) {
        this.items = orderFixture.build().items().stream().map(Coffee::name).toList();
        return this;
    }

    public PendingOrderFixture fromOrder(Order order) {
        this.items = order.items().stream().map(Coffee::name).toList();
        return this;
    }

    public PendingOrder build() {
        return new PendingOrder(items);
    }
}

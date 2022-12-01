package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill;

import ca.ulaval.glo4002.cafe.domain.Location;
import ca.ulaval.glo4002.cafe.domain.TipRate;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Tax;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;

public class BillFactory {
    public Bill createBill(Order order, Location location, TipRate groupTipRate, boolean isInGroup) {
        Amount subtotal = getOrderSubtotal(order);
        Tax taxPercentage = location.getTaxPercentage();
        Amount taxes = new Amount(subtotal.value() * taxPercentage.value());
        Amount tip = isInGroup ? new Amount(subtotal.value() * groupTipRate.value()) : new Amount(0);
        return new Bill(new Order(order.items()), subtotal, taxes, tip);
    }

    private Amount getOrderSubtotal(Order order) {
        return new Amount(order.items().stream()
                .map(coffee -> coffee.price().value())
                .reduce(0f, Float::sum));
    }
}

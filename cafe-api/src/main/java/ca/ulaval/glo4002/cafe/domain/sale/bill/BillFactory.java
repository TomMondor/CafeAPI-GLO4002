package ca.ulaval.glo4002.cafe.domain.sale.bill;

import ca.ulaval.glo4002.cafe.domain.Amount;
import ca.ulaval.glo4002.cafe.domain.Location;
import ca.ulaval.glo4002.cafe.domain.TipRate;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.sale.bill.tax.Tax;
import ca.ulaval.glo4002.cafe.domain.sale.bill.tax.TaxCalculator;

public class BillFactory {
    private final TaxCalculator taxCalculator;

    public BillFactory(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    public Bill createBill(Order order, Location location, TipRate groupTipRate, boolean isInGroup) {
        Amount subtotal = getOrderSubtotal(order);
        Amount taxes = calculateTaxes(subtotal, location);
        Amount tip = calculateTip(subtotal, groupTipRate, isInGroup);

        return new Bill(new Order(order.items()), subtotal, taxes, tip);
    }

    private Amount getOrderSubtotal(Order order) {
        return new Amount(order.items().stream()
            .map(coffee -> coffee.price().value())
            .reduce(0f, Float::sum));
    }

    private Amount calculateTaxes(Amount subtotal, Location location) {
        Tax taxPercentage = taxCalculator.calculateTaxPercentage(location);
        return new Amount(subtotal.value() * taxPercentage.value());
    }

    private Amount calculateTip(Amount subtotal, TipRate groupTipRate, boolean isInGroup) {
        return isInGroup ? new Amount(subtotal.value() * groupTipRate.value()) : new Amount(0);
    }
}

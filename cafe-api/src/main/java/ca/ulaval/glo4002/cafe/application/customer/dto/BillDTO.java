package ca.ulaval.glo4002.cafe.application.customer.dto;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.sale.bill.Bill;

public record BillDTO(List<Coffee> coffees, Amount tip, Amount subtotal, Amount taxes, Amount total) {
    public static BillDTO fromBill(Bill bill) {
        return new BillDTO(List.copyOf(bill.order().items()), bill.tip(), bill.subtotal(), bill.taxes(), bill.total());
    }
}

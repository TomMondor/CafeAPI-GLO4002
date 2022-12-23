package ca.ulaval.glo4002.cafe.application.customer.dto;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;

public record BillDTO(List<Coffee> coffees, Amount tip, Amount subtotal, Amount taxes, Amount total) {
    public static BillDTO fromBill(Bill bill) {
        return new BillDTO(List.copyOf(bill.order().items()), bill.tip(), bill.subtotal(), bill.taxes(), bill.total());
    }
}

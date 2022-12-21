package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;

public record PendingOrder(List<CoffeeName> items) {
}

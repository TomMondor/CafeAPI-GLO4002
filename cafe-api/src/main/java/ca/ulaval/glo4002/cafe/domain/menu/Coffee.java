package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.Amount;
import ca.ulaval.glo4002.cafe.domain.order.Recipe;

public record Coffee(CoffeeName name, Amount price, Recipe recipe) {
}

package ca.ulaval.glo4002.cafe.domain.order;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;

public record Recipe(List<Ingredient> ingredients) {
}

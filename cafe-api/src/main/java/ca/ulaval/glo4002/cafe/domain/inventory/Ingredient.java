package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.domain.exception.IngredientTypeMismatchException;

public record Ingredient(IngredientType type, Quantity quantity) {
    public Ingredient add(Ingredient ingredient) {
        if (ingredient.type() != type) {
            throw new IngredientTypeMismatchException();
        }

        return new Ingredient(type, quantity.add(ingredient.quantity()));
    }

    public Ingredient remove(Ingredient ingredient) {
        if (ingredient.type() != type) {
            throw new IngredientTypeMismatchException();
        }

        return new Ingredient(type, quantity.remove(ingredient.quantity()));
    }
}

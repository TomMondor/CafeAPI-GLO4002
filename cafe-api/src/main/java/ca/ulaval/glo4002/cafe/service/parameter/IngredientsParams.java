package ca.ulaval.glo4002.cafe.service.parameter;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;

public record IngredientsParams(Ingredient chocolate, Ingredient milk, Ingredient water, Ingredient espresso) {
    public IngredientsParams(int chocolate, int milk, int water, int espresso) {
        this(new Ingredient(IngredientType.Chocolate, new Quantity(chocolate)), new Ingredient(IngredientType.Milk, new Quantity(milk)),
            new Ingredient(IngredientType.Water, new Quantity(water)), new Ingredient(IngredientType.Espresso, new Quantity(espresso)));
    }

    public static IngredientsParams from(int chocolate, int milk, int water, int espresso) {
        return new IngredientsParams(chocolate, milk, water, espresso);
    }
}

package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;

public enum CoffeeType {
    Americano("Americano", new Amount(2.25f), new Recipe(List.of(
        new Ingredient(IngredientType.Espresso, new Quantity(50)),
        new Ingredient(IngredientType.Water, new Quantity(50))))),

    DarkRoast("Dark Roast", new Amount(2.10f), new Recipe(List.of(
        new Ingredient(IngredientType.Espresso, new Quantity(40)),
        new Ingredient(IngredientType.Water, new Quantity(40)),
        new Ingredient(IngredientType.Chocolate, new Quantity(10)),
        new Ingredient(IngredientType.Milk, new Quantity(10))))),

    Cappuccino("Cappuccino", new Amount(3.29f), new Recipe(List.of(
        new Ingredient(IngredientType.Espresso, new Quantity(50)),
        new Ingredient(IngredientType.Water, new Quantity(40)),
        new Ingredient(IngredientType.Milk, new Quantity(10))))),

    Espresso("Espresso", new Amount(2.95f), new Recipe(List.of(
        new Ingredient(IngredientType.Espresso, new Quantity(60))))),

    FlatWhite("Flat White", new Amount(3.75f), new Recipe(List.of(
        new Ingredient(IngredientType.Espresso, new Quantity(50)),
        new Ingredient(IngredientType.Milk, new Quantity(50))))),

    Latte("Latte", new Amount(2.95f), new Recipe(List.of(
        new Ingredient(IngredientType.Espresso, new Quantity(50)),
        new Ingredient(IngredientType.Milk, new Quantity(50))))),

    Macchiato("Macchiato", new Amount(4.75f), new Recipe(List.of(
        new Ingredient(IngredientType.Espresso, new Quantity(80)),
        new Ingredient(IngredientType.Milk, new Quantity(20))))),

    Mocha("Mocha", new Amount(4.15f), new Recipe(List.of(
        new Ingredient(IngredientType.Espresso, new Quantity(50)),
        new Ingredient(IngredientType.Milk, new Quantity(40)),
        new Ingredient(IngredientType.Chocolate, new Quantity(10)))));

    private final String type;
    private final Amount price;
    private final Recipe recipe;

    CoffeeType(String type, Amount price, Recipe recipe) {
        this.type = type;
        this.price = price;
        this.recipe = recipe;
    }

    private static boolean contains(String other) {
        for (CoffeeType coffeeType : CoffeeType.values()) {
            if (coffeeType.type.equals(other)) {
                return true;
            }
        }
        return false;
    }

    public static CoffeeType fromString(String type) {
        if (CoffeeType.contains(type)) {
            return CoffeeType.valueOf(type.replace(" ", ""));
        }
        throw new InvalidMenuOrderException();
    }

    @Override
    public String toString() {
        return type;
    }

    public Amount getPrice() {
        return price;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}

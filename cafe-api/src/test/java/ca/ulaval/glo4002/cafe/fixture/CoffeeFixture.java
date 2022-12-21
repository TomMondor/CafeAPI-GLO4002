package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;

public class CoffeeFixture {
    private CoffeeName name = new CoffeeName("Some Coffee");
    private Amount price = new Amount(2.0f);
    private Recipe recipe = new Recipe(List.of());

    public CoffeeFixture withName(CoffeeName name) {
        this.name = name;
        return this;
    }

    public CoffeeFixture withPrice(Amount price) {
        this.price = price;
        return this;
    }

    public CoffeeFixture withRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public CoffeeFixture withAmericano() {
        this.name = new CoffeeName("Americano");
        this.price = new Amount(2.25f);
        this.recipe = new Recipe(List.of(
            new Ingredient(IngredientType.Espresso, new Quantity(50)),
            new Ingredient(IngredientType.Water, new Quantity(50))));
        return this;
    }

    public CoffeeFixture withEspresso() {
        this.name = new CoffeeName("Espresso");
        this.price = new Amount(2.95f);
        this.recipe = new Recipe(List.of(
            new Ingredient(IngredientType.Espresso, new Quantity(60))));
        return this;
    }

    public CoffeeFixture withLatte() {
        this.name = new CoffeeName("Latte");
        this.price = new Amount(2.95f);
        this.recipe = new Recipe(List.of(
            new Ingredient(IngredientType.Espresso, new Quantity(50)),
            new Ingredient(IngredientType.Milk, new Quantity(50))));
        return this;
    }

    public CoffeeFixture withDarkRoast() {
        this.name = new CoffeeName("Dark Roast");
        this.price = new Amount(2.10f);
        this.recipe = new Recipe(List.of(
            new Ingredient(IngredientType.Espresso, new Quantity(40)),
            new Ingredient(IngredientType.Water, new Quantity(40)),
            new Ingredient(IngredientType.Chocolate, new Quantity(10)),
            new Ingredient(IngredientType.Milk, new Quantity(10))));
        return this;
    }

    public Coffee build() {
        return new Coffee(name, price, recipe);
    }
}

package ca.ulaval.glo4002.cafe.service.parameter;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;

public record CoffeeParams(CoffeeName name, Amount cost, Recipe ingredients) {
    public CoffeeParams(String name, float price, IngredientsParams ingredients) {
        this(new CoffeeName(name), new Amount(price),
            new Recipe(List.of(ingredients.chocolate(), ingredients.milk(), ingredients.water(), ingredients.espresso())));
    }

    public static CoffeeParams from(String name, float cost, IngredientsParams ingredients) {
        return new CoffeeParams(name, cost, ingredients);
    }
}

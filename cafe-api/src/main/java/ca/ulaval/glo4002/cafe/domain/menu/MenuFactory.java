package ca.ulaval.glo4002.cafe.domain.menu;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Recipe;

public class MenuFactory {
    public Menu createMenu() {
        Menu menu = new Menu();
        insertDefaultMenuItems(menu);
        return menu;
    }

    private void insertDefaultMenuItems(Menu menu) {
        for (MenuItem item : getDefaultMenuItems()) {
            menu.addMenuItem(item);
        }
    }

    private List<MenuItem> getDefaultMenuItems() {
        return List.of(
            new MenuItem(new MenuItemName("Americano"), new Amount(2.25f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Water, new Quantity(50))))),

            new MenuItem(new MenuItemName("Dark Roast"), new Amount(2.10f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(40)),
                new Ingredient(IngredientType.Water, new Quantity(40)),
                new Ingredient(IngredientType.Chocolate, new Quantity(10)),
                new Ingredient(IngredientType.Milk, new Quantity(10))))),

            new MenuItem(new MenuItemName("Cappuccino"), new Amount(3.29f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Water, new Quantity(40)),
                new Ingredient(IngredientType.Milk, new Quantity(10))))),

            new MenuItem(new MenuItemName("Espresso"), new Amount(2.95f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(60))))),

            new MenuItem(new MenuItemName("Flat White"), new Amount(3.75f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(50))))),

            new MenuItem(new MenuItemName("Latte"), new Amount(2.95f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(50))))),

            new MenuItem(new MenuItemName("Macchiato"), new Amount(4.75f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(80)),
                new Ingredient(IngredientType.Milk, new Quantity(20))))),

            new MenuItem(new MenuItemName("Mocha"), new Amount(4.15f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(40)),
                new Ingredient(IngredientType.Chocolate, new Quantity(10)))))
        );
    }
}

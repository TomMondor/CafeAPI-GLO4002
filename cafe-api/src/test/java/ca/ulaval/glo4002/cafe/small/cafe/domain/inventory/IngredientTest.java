package ca.ulaval.glo4002.cafe.small.cafe.domain.inventory;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTest {
    @Test
    public void whenAddingIngredients_shouldCreateNewInstanceWithSumOfQuantities() {
        Ingredient ingredient1 = new Ingredient(IngredientType.Milk, new Quantity(4));
        Ingredient ingredient2 = new Ingredient(IngredientType.Milk, new Quantity(3));

        Ingredient addedIngredients = ingredient1.add(ingredient2.quantity());

        assertEquals(addedIngredients.type(), ingredient1.type());
        assertEquals(new Quantity(4 + 3), addedIngredients.quantity());
    }

    @Test
    public void whenRemovingIngredients_shouldCreateNewInstanceWithDifferenceOfQuantities() {
        Ingredient ingredient1 = new Ingredient(IngredientType.Milk, new Quantity(4));
        Ingredient ingredient2 = new Ingredient(IngredientType.Milk, new Quantity(3));

        Ingredient addedIngredients = ingredient1.remove(ingredient2.quantity());

        assertEquals(addedIngredients.type(), ingredient1.type());
        assertEquals(new Quantity(4 - 3), addedIngredients.quantity());
    }
}

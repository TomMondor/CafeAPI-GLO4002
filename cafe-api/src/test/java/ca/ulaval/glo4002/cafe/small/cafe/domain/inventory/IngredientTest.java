package ca.ulaval.glo4002.cafe.small.cafe.domain.inventory;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.IngredientTypeMismatchException;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IngredientTest {
    @Test
    public void givenSameIngredientType_whenAddingIngredients_shouldCreateNewInstanceWithSumOfQuantities() {
        Ingredient ingredient1 = new Ingredient(IngredientType.Milk, new Quantity(4));
        Ingredient ingredient2 = new Ingredient(IngredientType.Milk, new Quantity(3));

        Ingredient addedIngredients = ingredient1.add(ingredient2);

        assertEquals(addedIngredients.type(), ingredient1.type());
        assertEquals(new Quantity(4 + 3), addedIngredients.quantity());
    }

    @Test
    public void givenDifferentIngredientTypes_whenAddingIngredients_shouldThrowIngredientTypeMismatchException() {
        Ingredient ingredient1 = new Ingredient(IngredientType.Milk, new Quantity(4));
        Ingredient ingredient2 = new Ingredient(IngredientType.Water, new Quantity(3));

        assertThrows(IngredientTypeMismatchException.class, () -> ingredient1.add(ingredient2));
    }

    @Test
    public void givenSameIngredientType_whenRemovingIngredients_shouldCreateNewInstanceWithDifferenceOfQuantities() {
        Ingredient ingredient1 = new Ingredient(IngredientType.Milk, new Quantity(4));
        Ingredient ingredient2 = new Ingredient(IngredientType.Milk, new Quantity(3));

        Ingredient addedIngredients = ingredient1.remove(ingredient2);

        assertEquals(addedIngredients.type(), ingredient1.type());
        assertEquals(new Quantity(4 - 3), addedIngredients.quantity());
    }

    @Test
    public void givenDifferentIngredientTypes_whenRemoveIngredients_shouldThrowIngredientTypeMismatchException() {
        Ingredient ingredient1 = new Ingredient(IngredientType.Milk, new Quantity(4));
        Ingredient ingredient2 = new Ingredient(IngredientType.Water, new Quantity(3));

        assertThrows(IngredientTypeMismatchException.class, () -> ingredient1.remove(ingredient2));
    }
}

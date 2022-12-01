package ca.ulaval.glo4002.cafe.domain.inventory;

import java.util.HashMap;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.exception.InsufficientIngredientsException;

public class Inventory {
    private HashMap<IngredientType, Ingredient> ingredients = new HashMap<>();

    public HashMap<IngredientType, Ingredient> getIngredients() {
        return ingredients;
    }

    public void clear() {
        ingredients = new HashMap<>();
    }

    public void add(List<Ingredient> newIngredients) {
        newIngredients.forEach(ingredient -> ingredients.put(ingredient.type(),
            ingredients.containsKey(ingredient.type()) ? ingredients.get(ingredient.type()).add(ingredient) : ingredient));
    }

    public void useIngredients(List<Ingredient> ingredients) {
        List<Ingredient> mergedIngredients = mergeIngredients(ingredients);
        validateIfEnoughIngredients(mergedIngredients);
        removeIngredients(mergedIngredients);
    }

    private void validateIfEnoughIngredients(List<Ingredient> ingredientsNeeded) {
        for (Ingredient ingredientNeeded : ingredientsNeeded) {
            if (!ingredients.containsKey(ingredientNeeded.type()) ||
                ingredientNeeded.quantity().isGreaterThan(ingredients.get(ingredientNeeded.type()).quantity())) {
                throw new InsufficientIngredientsException();
            }
        }
    }

    private void removeIngredients(List<Ingredient> ingredientsToRemove) {
        ingredientsToRemove.forEach(ingredient -> ingredients.put(ingredient.type(), ingredients.get(ingredient.type()).remove(ingredient)));
    }

    private List<Ingredient> mergeIngredients(List<Ingredient> ingredientsNeeded) {
        HashMap<IngredientType, Ingredient> ingredients = new HashMap<>();
        for (Ingredient ingredient : ingredientsNeeded) {
            Ingredient newIngredient = ingredients.containsKey(ingredient.type()) ? ingredients.get(ingredient.type()).add(ingredient) : ingredient;
            ingredients.put(ingredient.type(), newIngredient);
        }
        return ingredients.values().stream().toList();
    }
}

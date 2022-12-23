package ca.ulaval.glo4002.cafe.domain.inventory;

public record Ingredient(IngredientType type, Quantity quantity) {
    public Ingredient add(Quantity quantityToAdd) {
        return new Ingredient(type, quantity.add(quantityToAdd));
    }

    public Ingredient remove(Quantity quantityToRemove) {
        return new Ingredient(type, quantity.remove(quantityToRemove));
    }
}

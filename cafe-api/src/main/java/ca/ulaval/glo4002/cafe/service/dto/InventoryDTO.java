package ca.ulaval.glo4002.cafe.service.dto;

import java.util.HashMap;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;

public record InventoryDTO(HashMap<IngredientType, Ingredient> ingredients) {
    public static InventoryDTO fromInventory(Inventory inventory) {
        return new InventoryDTO(inventory.getIngredients());
    }
}

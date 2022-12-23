package ca.ulaval.glo4002.cafe.api.inventory.assembler;

import ca.ulaval.glo4002.cafe.api.inventory.response.InventoryResponse;
import ca.ulaval.glo4002.cafe.application.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;

public class InventoryResponseAssembler {
    public InventoryResponse toInventoryResponse(InventoryDTO inventoryDTO) {
        return new InventoryResponse(
            inventoryDTO.ingredients().containsKey(IngredientType.Chocolate) ?
                inventoryDTO.ingredients().get(IngredientType.Chocolate).quantity().value() : 0,
            inventoryDTO.ingredients().containsKey(IngredientType.Espresso) ?
                inventoryDTO.ingredients().get(IngredientType.Espresso).quantity().value() : 0,
            inventoryDTO.ingredients().containsKey(IngredientType.Milk) ?
                inventoryDTO.ingredients().get(IngredientType.Milk).quantity().value() : 0,
            inventoryDTO.ingredients().containsKey(IngredientType.Water) ?
                inventoryDTO.ingredients().get(IngredientType.Water).quantity().value() : 0
        );
    }
}

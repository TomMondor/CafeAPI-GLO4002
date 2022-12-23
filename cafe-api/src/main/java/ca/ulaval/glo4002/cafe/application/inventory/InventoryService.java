package ca.ulaval.glo4002.cafe.application.inventory;

import java.util.List;

import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.application.parameter.IngredientsParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;

public class InventoryService {
    private final CafeRepository cafeRepository;

    public InventoryService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public void addIngredientsToInventory(IngredientsParams ingredientsParams) {
        Cafe cafe = cafeRepository.get();
        cafe.addIngredientsToInventory(
            List.of(ingredientsParams.chocolate(), ingredientsParams.milk(), ingredientsParams.water(), ingredientsParams.espresso()));
        cafeRepository.saveOrUpdate(cafe);
    }

    public InventoryDTO getInventory() {
        Cafe cafe = cafeRepository.get();
        return InventoryDTO.fromInventory(cafe.getInventory());
    }
}

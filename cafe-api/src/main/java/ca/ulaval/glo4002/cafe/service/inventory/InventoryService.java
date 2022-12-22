package ca.ulaval.glo4002.cafe.service.inventory;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

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

package ca.ulaval.glo4002.cafe.medium;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.service.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryServiceTest {
    private static final IngredientsParams INGREDIENT_PARAMS = new IngredientsParams(25, 20, 15, 10);

    private InventoryService inventoryService;
    private Cafe cafe;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void instantiateAttributes() {
        cafeRepository = new InMemoryCafeRepository();
        inventoryService = new InventoryService(cafeRepository);
        cafe = new CafeFactory().createCafe(List.of());
        cafeRepository.saveOrUpdate(cafe);
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldAddIngredientsToInventory() {
        inventoryService.addIngredientsToInventory(INGREDIENT_PARAMS);

        InventoryDTO inventory = inventoryService.getInventory();
        assertEquals(INGREDIENT_PARAMS.chocolate().quantity(), inventory.ingredients().get(IngredientType.Chocolate).quantity());
        assertEquals(INGREDIENT_PARAMS.milk().quantity(), inventory.ingredients().get(IngredientType.Milk).quantity());
        assertEquals(INGREDIENT_PARAMS.water().quantity(), inventory.ingredients().get(IngredientType.Water).quantity());
        assertEquals(INGREDIENT_PARAMS.espresso().quantity(), inventory.ingredients().get(IngredientType.Espresso).quantity());
    }
}

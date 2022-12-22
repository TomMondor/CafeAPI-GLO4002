package ca.ulaval.glo4002.cafe.small.cafe.service.inventory;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.fixture.CafeFixture;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.service.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {
    private static final Cafe A_CAFE = new CafeFixture().build();
    private static final IngredientsParams AN_INGREDIENTS_PARAMS = new IngredientsParams(1, 2, 3, 4);
    private InventoryService inventoryService;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void createInventoryService() {
        cafeRepository = mock(CafeRepository.class);
        inventoryService = new InventoryService(cafeRepository);
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        inventoryService.addIngredientsToInventory(AN_INGREDIENTS_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldAddIngredientsInInventory() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        inventoryService.addIngredientsToInventory(AN_INGREDIENTS_PARAMS);

        verify(mockCafe).addIngredientsToInventory(
            List.of(AN_INGREDIENTS_PARAMS.chocolate(), AN_INGREDIENTS_PARAMS.milk(), AN_INGREDIENTS_PARAMS.water(), AN_INGREDIENTS_PARAMS.espresso()));
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        inventoryService.addIngredientsToInventory(AN_INGREDIENTS_PARAMS);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenGettingInventory_shouldGetCafe() {
        when(cafeRepository.get()).thenReturn(A_CAFE);

        inventoryService.getInventory();

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingInventory_shouldReturnInventoryDTO() {
        when(cafeRepository.get()).thenReturn(A_CAFE);
        InventoryDTO expectedInventoryDTO = new InventoryDTO(new HashMap<>());

        InventoryDTO actualInventoryDTO = inventoryService.getInventory();

        assertEquals(expectedInventoryDTO, actualInventoryDTO);
    }
}

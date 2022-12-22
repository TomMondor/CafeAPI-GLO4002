package ca.ulaval.glo4002.cafe.small.cafe.api.inventory;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.inventory.InventoryResource;
import ca.ulaval.glo4002.cafe.api.inventory.request.InventoryRequest;
import ca.ulaval.glo4002.cafe.fixture.request.InventoryRequestFixture;
import ca.ulaval.glo4002.cafe.service.CafeService;
import ca.ulaval.glo4002.cafe.service.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InventoryResourceTest {
    private static final InventoryDTO A_INVENTORY_DTO = new InventoryDTO(new HashMap<>());
    private static final int CHOCOLATE = 1;
    private static final int ESPRESSO = 1;
    private static final int MILK = 1;
    private static final int WATER = 1;

    private CafeService cafeService;
    private InventoryResource inventoryResource;

    @BeforeEach
    public void createInventoryResource() {
        cafeService = mock(CafeService.class);
        inventoryResource = new InventoryResource(cafeService);
    }

    @Test
    public void givenValidRequest_whenAddingIngredients_shouldAddIngredientsToInventory() {
        InventoryRequest inventoryRequest = new InventoryRequestFixture()
            .withChocolate(CHOCOLATE)
            .withMilk(MILK)
            .withEspresso(ESPRESSO)
            .withWater(WATER)
            .build();
        IngredientsParams ingredientsParams = new IngredientsParams(CHOCOLATE, MILK, WATER, ESPRESSO);

        inventoryResource.putInventory(inventoryRequest);

        verify(cafeService).addIngredientsToInventory(ingredientsParams);
    }

    @Test
    public void givenValidRequest_whenAddingIngredients_shouldReturn200() {
        InventoryRequest inventoryRequest = new InventoryRequestFixture()
            .withChocolate(CHOCOLATE)
            .withMilk(MILK)
            .withEspresso(ESPRESSO)
            .withWater(WATER)
            .build();

        Response response = inventoryResource.putInventory(inventoryRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenGettingInventory_shouldGetInventory() {
        when(cafeService.getInventory()).thenReturn(A_INVENTORY_DTO);

        inventoryResource.getInventory();

        verify(cafeService).getInventory();
    }

    @Test
    public void whenGettingInventory_shouldReturn200() {
        when(cafeService.getInventory()).thenReturn(A_INVENTORY_DTO);

        Response response = inventoryResource.getInventory();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

package ca.ulaval.glo4002.cafe.medium;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.CafeName;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNoBillException;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationFixture;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeService;
import ca.ulaval.glo4002.cafe.service.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.service.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CafeServiceTest {
    private static final CafeName NEW_CAFE_NAME = new CafeName("Les 4-Ogres");
    private static final Customer A_CUSTOMER = new CustomerFixture().build();
    private static final Reservation A_RESERVATION = new ReservationFixture().build();
    private static final IngredientsParams INGREDIENT_PARAMS = new IngredientsParams(25, 20, 15, 10);
    private static final ConfigurationParams CONFIGURATION_PARAMS = new ConfigurationParams(5, NEW_CAFE_NAME.value(), "Default", "CA",
        "QC", "", 5);

    private CafeService cafeService;
    private Cafe cafe;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void instanciateAttributes() {
        cafeRepository = new InMemoryCafeRepository();
        cafeService = new CafeService(cafeRepository, new CafeFactory());
        cafeService.initializeCafe();
        cafe = cafeRepository.get();
    }

    @Test
    public void whenUpdatingConfiguration_shouldUpdateConfiguration() {
        cafeService.updateConfiguration(CONFIGURATION_PARAMS);

        assertEquals(NEW_CAFE_NAME, cafeService.getLayout().name());
    }

    @Test
    public void givenAReservation_whenClosing_shouldClearReservations() {
        cafe.makeReservation(A_RESERVATION);

        cafeService.closeCafe();

        cafe = cafeRepository.get();
        assertEquals(0, cafe.getReservations().size());
    }

    @Test
    public void givenASavedBill_whenClosing_shouldClearBills() {
        cafe.checkIn(A_CUSTOMER, Optional.empty());
        cafe.checkOut(A_CUSTOMER.getId());

        cafeService.closeCafe();
        cafe.checkIn(A_CUSTOMER, Optional.empty());

        cafe = cafeRepository.get();
        assertThrows(CustomerNoBillException.class, () -> cafe.getCustomerBill(A_CUSTOMER.getId()));
    }

    @Test
    public void givenNonEmptyInventory_whenClosing_shouldClearInventory() {
        cafeService.addIngredientsToInventory(INGREDIENT_PARAMS);

        cafeService.closeCafe();

        assertEquals(0, cafeService.getInventory().ingredients().size());
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldAddIngredientsToInventory() {
        cafeService.addIngredientsToInventory(INGREDIENT_PARAMS);

        InventoryDTO inventory = cafeService.getInventory();
        assertEquals(INGREDIENT_PARAMS.chocolate().quantity(), inventory.ingredients().get(IngredientType.Chocolate).quantity());
        assertEquals(INGREDIENT_PARAMS.milk().quantity(), inventory.ingredients().get(IngredientType.Milk).quantity());
        assertEquals(INGREDIENT_PARAMS.water().quantity(), inventory.ingredients().get(IngredientType.Water).quantity());
        assertEquals(INGREDIENT_PARAMS.espresso().quantity(), inventory.ingredients().get(IngredientType.Espresso).quantity());
    }
}

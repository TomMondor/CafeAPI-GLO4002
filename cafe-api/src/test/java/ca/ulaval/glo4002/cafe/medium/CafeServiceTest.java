package ca.ulaval.glo4002.cafe.medium;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.CafeName;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNoBillException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.PendingOrder;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.fixture.CoffeeFixture;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationFixture;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeService;
import ca.ulaval.glo4002.cafe.service.layout.LayoutService;
import ca.ulaval.glo4002.cafe.service.parameter.CoffeeParams;
import ca.ulaval.glo4002.cafe.service.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import static org.junit.jupiter.api.Assertions.*;

public class CafeServiceTest {
    private static final CafeName NEW_CAFE_NAME = new CafeName("Les 4-Ogres");
    private static final Customer A_CUSTOMER = new CustomerFixture().build();
    private static final Reservation A_RESERVATION = new ReservationFixture().build();
    private static final CoffeeName A_COFFEE_NAME = new CoffeeName("coffee name");
    private static final IngredientsParams INGREDIENT_PARAMS = new IngredientsParams(25, 20, 15, 10);
    private static final CoffeeParams A_COFFEE_PARAMS = new CoffeeParams("coffee name", 3.25f, INGREDIENT_PARAMS);
    private static final ConfigurationParams CONFIGURATION_PARAMS = new ConfigurationParams(5, NEW_CAFE_NAME.value(), "Default", "CA",
        "QC", "", 5);
    private static final List<Ingredient> ENOUGH_INGREDIENTS = List.of(new Ingredient(IngredientType.Chocolate, new Quantity(25)),
        new Ingredient(IngredientType.Milk, new Quantity(25)), new Ingredient(IngredientType.Water, new Quantity(25)),
        new Ingredient(IngredientType.Espresso, new Quantity(25)));

    private CafeService cafeService;
    private LayoutService layoutService;
    private Cafe cafe;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void instantiateAttributes() {
        cafeRepository = new InMemoryCafeRepository();
        layoutService = new LayoutService(cafeRepository);
        cafeService = new CafeService(cafeRepository);
        cafe = new CafeFactory().createCafe(List.of());
        cafeRepository.saveOrUpdate(cafe);
    }

    @Test
    public void whenUpdatingConfiguration_shouldUpdateConfiguration() {
        cafeService.updateConfiguration(CONFIGURATION_PARAMS);

        assertEquals(NEW_CAFE_NAME, layoutService.getLayout().name());
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
    public void givenCustomMenuItems_whenClosing_shouldClearCustomMenuItems() {
        cafe.checkIn(A_CUSTOMER, Optional.empty());
        cafe.addMenuItem(new CoffeeFixture().withName(A_COFFEE_NAME).build());

        cafeService.closeCafe();

        assertThrows(InvalidMenuOrderException.class,
            () -> cafe.placeOrder(A_CUSTOMER.getId(), new PendingOrder(List.of(A_COFFEE_NAME))));
    }

    @Test
    public void givenNonEmptyInventory_whenClosing_shouldClearInventory() {
        cafe.addIngredientsToInventory(ENOUGH_INGREDIENTS);

        cafeService.closeCafe();

        assertEquals(0, cafe.getInventory().getIngredients().size());
    }

    @Test
    public void whenAddingMenuItem_shouldAddMenuItem() {
        cafe.addIngredientsToInventory(ENOUGH_INGREDIENTS);
        PendingOrder pendingOrder = new PendingOrder(List.of(A_COFFEE_PARAMS.name()));
        cafe.checkIn(A_CUSTOMER, Optional.empty());

        cafeService.addMenuItem(A_COFFEE_PARAMS);

        assertDoesNotThrow(() -> cafe.placeOrder(A_CUSTOMER.getId(), pendingOrder));
    }
}

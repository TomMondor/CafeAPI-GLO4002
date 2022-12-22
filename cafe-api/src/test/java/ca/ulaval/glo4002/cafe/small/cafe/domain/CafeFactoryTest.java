package ca.ulaval.glo4002.cafe.small.cafe.domain;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.CafeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.PendingOrder;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CafeFactoryTest {
    private static final CafeName DEFAULT_CAFE_NAME = new CafeName("Les 4-Fées");
    private static final List<Coffee> DEFAULT_MENU_ITEMS = List.of(new Coffee(new CoffeeName("item"), new Amount(1), new Recipe(List.of())));
    private static final Customer A_CUSTOMER = new CustomerFixture().build();
    private CafeFactory cafeFactory;

    @BeforeEach
    public void createCafeFactory() {
        cafeFactory = new CafeFactory();
    }

    @Test
    public void whenCreatingCafe_shouldHaveDefaultName() {
        Cafe cafe = cafeFactory.createCafe(DEFAULT_MENU_ITEMS);

        assertEquals(DEFAULT_CAFE_NAME, cafe.getName());
    }

    @Test
    public void whenCreatingCafe_shouldCreateCubesListWithSortedSpecificCubesNames() {
        List<CubeName> expectedCubeNames = List.of(new CubeName("Bloom"), new CubeName("Merryweather"), new CubeName("Tinker Bell"), new CubeName("Wanda"));

        Cafe cafe = cafeFactory.createCafe(DEFAULT_MENU_ITEMS);

        assertEquals(expectedCubeNames, cafe.getLayout().getCubes().stream().map(Cube::getName).toList());
    }

    @Test
    public void whenCreatingCafe_shouldContainDefaultMenuItems() {
        Coffee defaultItem = new Coffee(new CoffeeName("item"), new Amount(1), new Recipe(List.of()));

        Cafe cafe = cafeFactory.createCafe(List.of(defaultItem));

        cafe.checkIn(A_CUSTOMER, Optional.empty());
        assertDoesNotThrow(() -> cafe.placeOrder(A_CUSTOMER.getId(), new PendingOrder(List.of(defaultItem.name()))));
    }
}

package ca.ulaval.glo4002.cafe.small.cafe.domain;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Amount;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeName;
import ca.ulaval.glo4002.cafe.domain.Country;
import ca.ulaval.glo4002.cafe.domain.Province;
import ca.ulaval.glo4002.cafe.domain.State;
import ca.ulaval.glo4002.cafe.domain.TipRate;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerAlreadyVisitedException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNoBillException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.exception.DuplicateCubeNameException;
import ca.ulaval.glo4002.cafe.domain.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.exception.DuplicateMenuItemNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InsufficientIngredientsException;
import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;
import ca.ulaval.glo4002.cafe.domain.exception.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.exception.NoReservationsFoundException;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;
import ca.ulaval.glo4002.cafe.domain.menu.Menu;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.PendingOrder;
import ca.ulaval.glo4002.cafe.domain.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.sale.bill.Bill;
import ca.ulaval.glo4002.cafe.fixture.CafeConfigurationFixture;
import ca.ulaval.glo4002.cafe.fixture.CoffeeFixture;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.OrderFixture;
import ca.ulaval.glo4002.cafe.fixture.PendingOrderFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationFixture;

import static org.junit.jupiter.api.Assertions.*;

public class CafeTest {
    private static final List<CubeName> SOME_CUBE_NAMES = List.of(new CubeName("Bob"), new CubeName("John"));
    private static final List<CubeName> SOME_UNORDERED_CUBE_NAMES = List.of(new CubeName("B"), new CubeName("A"));
    private static final List<CubeName> DUPLICATE_CUBE_NAMES = List.of(new CubeName("Bob"), new CubeName("Bob"));
    private static final List<CubeName> A_CUBE_NAME = List.of(new CubeName("aName"));
    private static final List<CubeName> TWO_CUBE_NAMES = List.of(new CubeName("Bob"), new CubeName("John"));
    private static final CubeSize TWO_SEATS_PER_CUBE = new CubeSize(2);
    private static final Customer A_CUSTOMER = new CustomerFixture().withCustomerId(new CustomerId("125")).build();
    private static final Customer ANOTHER_CUSTOMER = new CustomerFixture().withCustomerId(new CustomerId("121135")).build();
    private static final Reservation A_RESERVATION_FOR_TWO = new ReservationFixture().withGroupSize(new GroupSize(2)).build();
    private static final Reservation ANOTHER_RESERVATION_FOR_TWO =
        new ReservationFixture().withGroupName(new GroupName("Another")).withGroupSize(new GroupSize(2)).build();
    private static final Order AN_ORDER = new OrderFixture().build();
    private static final PendingOrder THE_MATCHING_PENDING_ORDER = new PendingOrderFixture().fromOrder(AN_ORDER).build();
    private static final PendingOrder A_PENDING_ORDER = THE_MATCHING_PENDING_ORDER;
    private static final Order THE_MATCHING_ORDER = AN_ORDER;
    private static final Order ANOTHER_ORDER = new OrderFixture().withItems(List.of(new CoffeeFixture().withEspresso().build())).build();
    private static final Coffee A_MENU_ITEM = new Coffee(new CoffeeName("menuItem"), new Amount(10), new Recipe(List.of()));
    private static final Coffee A_DEFAULT_COFFEE = A_MENU_ITEM;
    private static final CoffeeName A_COFFEE_NAME = new CoffeeName("CustomCoffee");
    private static final Menu A_MENU = new Menu(List.of(new CoffeeFixture().withAmericano().build(), new CoffeeFixture().withEspresso().build()));
    private static final Menu AN_EMPTY_MENU = new Menu(List.of());
    private static final PendingOrder AN_ORDER_WITH_INEXISTANT_ITEM = new PendingOrderFixture().withItems(List.of(new CoffeeName("UnknownCoffee"))).build();
    private static final List<Ingredient> ENOUGH_INGREDIENTS = AN_ORDER.ingredientsNeeded();
    private static final Amount AN_AMOUNT = new Amount(10);
    private static final Amount ANOTHER_AMOUNT = new Amount(20);
    private static final Recipe SOME_RECIPE = new Recipe(List.of());

    private Cafe cafe;

    @BeforeEach
    public void createDefaultCafe() {
        AN_EMPTY_MENU.clearCustomMenuItems();
        A_MENU.clearCustomMenuItems();
        cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), A_MENU);
    }

    @Test
    public void whenCreatingCafe_shouldHaveNoReservations() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);

        assertTrue(cafe.getReservations().isEmpty());
    }

    @Test
    public void whenCreatingCafe_shouldHaveEmptyInventory() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);

        assertTrue(cafe.getInventory().getIngredients().isEmpty());
    }

    @Test
    public void givenDuplicateCubeNames_whenCreatingCafe_shouldThrowException() {
        assertThrows(DuplicateCubeNameException.class, () -> new Cafe(DUPLICATE_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU));
    }

    @Test
    public void whenGettingLayout_shouldHaveProvidedNumberOfCubesInLayout() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);

        Layout layout = cafe.getLayout();

        assertEquals(SOME_CUBE_NAMES.size(), layout.getCubes().size());
    }

    @Test
    public void whenGettingLayout_shouldHaveCubesWithProvidedNamesInLayout() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);

        Layout layout = cafe.getLayout();

        assertEquals(SOME_CUBE_NAMES.stream().sorted().toList(), layout.getCubes().stream().map(Cube::getName).sorted().toList());
    }

    @Test
    public void whenGettingLayout_shouldHaveCubesInAlphabeticalOrderOfNameInLayout() {
        Cafe cafe = new Cafe(SOME_UNORDERED_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);

        Layout layout = cafe.getLayout();

        assertEquals(SOME_UNORDERED_CUBE_NAMES.stream().sorted().toList(), layout.getCubes().stream().map(Cube::getName).toList());
    }

    @Test
    public void whenGettingLayout_shouldHaveCubesWithProvidedNumberOfSeatsInLayout() {
        CubeSize providedCubeSize = new CubeSize(2);
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withCubeSize(providedCubeSize).build(), AN_EMPTY_MENU);

        Layout layout = cafe.getLayout();

        assertEquals(providedCubeSize.value(), layout.getCubes().get(0).getNumberOfSeats());
    }

    @Test
    public void whenGettingLayout_shouldHaveCubesWithIncrementingSeatNumbersInLayout() {
        Cafe cafe = new Cafe(TWO_CUBE_NAMES, new CafeConfigurationFixture().withCubeSize(TWO_SEATS_PER_CUBE).build(), AN_EMPTY_MENU);

        Layout layout = cafe.getLayout();
        List<Integer> actualSeatNumbers = layout.getCubes().stream().map(Cube::getSeats).flatMap(List::stream).map(seat -> seat.getNumber().value()).toList();

        List<Integer> expectedSeatNumbers = List.of(1, 2, 3, 4);
        assertEquals(expectedSeatNumbers, actualSeatNumbers);
    }

    @Test
    public void givenNoReservationNorCustomer_whenGettingLayout_shouldHaveAllSeatsAvailableInLayout() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withCubeSize(TWO_SEATS_PER_CUBE).build(), AN_EMPTY_MENU);

        Layout layout = cafe.getLayout();
        int numberOfAvailableSeats = (int) layout.getCubes().stream().map(Cube::getSeats).flatMap(List::stream).filter(Seat::isCurrentlyAvailable).count();

        assertEquals(SOME_CUBE_NAMES.size() * TWO_SEATS_PER_CUBE.value(), numberOfAvailableSeats);
    }

    @Test
    public void givenReservation_whenGettingLayout_shouldHaveMatchingSeatsReservedInLayout() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withCubeSize(new CubeSize(2)).build(), AN_EMPTY_MENU);
        cafe.makeReservation(A_RESERVATION_FOR_TWO);

        Layout layout = cafe.getLayout();
        int numberOfReservedSeats = (int) layout.getCubes().stream().map(Cube::getSeats).flatMap(List::stream).filter(Seat::isCurrentlyReserved).count();

        assertEquals(2, numberOfReservedSeats);
    }

    @Test
    public void givenNoAvailableSeats_whenCheckingIn_shouldThrowInsufficientSeatsException() {
        Cafe cafe = new Cafe(A_CUBE_NAME, new CafeConfigurationFixture().withCubeSize(new CubeSize(3)).build(), AN_EMPTY_MENU);
        cafe.checkIn(ANOTHER_CUSTOMER, Optional.empty());
        cafe.makeReservation(A_RESERVATION_FOR_TWO);
        Customer aCustomer = new CustomerFixture().build();

        assertThrows(InsufficientSeatsException.class, () -> cafe.checkIn(aCustomer, Optional.empty()));
    }

    @Test
    public void givenNewCustomerWithoutReservation_whenCheckingIn_shouldOccupyFirstAvailableSeat() {
        Cafe cafe = new Cafe(A_CUBE_NAME, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);
        cafe.checkIn(ANOTHER_CUSTOMER, Optional.empty());
        cafe.makeReservation(A_RESERVATION_FOR_TWO);
        Customer aCustomer = new CustomerFixture().build();

        cafe.checkIn(aCustomer, Optional.empty());

        assertEquals(new SeatNumber(4), cafe.getSeatByCustomerId(aCustomer.getId()).getNumber());
    }

    @Test
    public void givenNewCustomerWithReservation_whenCheckingIn_shouldOccupyFirstReservedSeat() {
        Cafe cafe = new Cafe(A_CUBE_NAME, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);
        cafe.checkIn(ANOTHER_CUSTOMER, Optional.empty());
        cafe.makeReservation(A_RESERVATION_FOR_TWO);
        Customer aCustomer = new CustomerFixture().build();

        cafe.checkIn(aCustomer, Optional.of(A_RESERVATION_FOR_TWO.name()));

        assertEquals(new SeatNumber(2), cafe.getSeatByCustomerId(aCustomer.getId()).getNumber());
    }

    @Test
    public void givenNewCustomerWithReservationButNoMoreReservedSeats_whenCheckingIn_shouldThrowNoGroupSeatsException() {
        Cafe cafe = new Cafe(A_CUBE_NAME, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);
        cafe.makeReservation(A_RESERVATION_FOR_TWO);
        cafe.checkIn(A_CUSTOMER, Optional.of(A_RESERVATION_FOR_TWO.name()));
        cafe.checkIn(ANOTHER_CUSTOMER, Optional.of(A_RESERVATION_FOR_TWO.name()));

        assertThrows(NoGroupSeatsException.class, () -> cafe.checkIn(new CustomerFixture().build(), Optional.of(A_RESERVATION_FOR_TWO.name())));
    }

    @Test
    public void givenNewCustomerWithInvalidReservation_whenCheckingIn_shouldThrowNoReservationFoundException() {
        Cafe cafe = new Cafe(A_CUBE_NAME, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);
        cafe.makeReservation(A_RESERVATION_FOR_TWO);
        Customer aCustomer = new CustomerFixture().build();

        assertThrows(NoReservationsFoundException.class, () -> cafe.checkIn(aCustomer, Optional.of(new GroupName("invalid"))));
    }

    @Test
    public void givenCustomerWhoAlreadyVisitedToday_whenCheckingIn_shouldThrowCustomerAlreadyVisitedException() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        assertThrows(CustomerAlreadyVisitedException.class, () -> cafe.checkIn(aCustomer, Optional.empty()));
    }

    @Test
    public void givenCheckedInCustomer_whenGettingSeatByCustomerId_shouldReturnSeatWithCustomer() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        Seat actualSeat = cafe.getSeatByCustomerId(aCustomer.getId());

        assertEquals(aCustomer, actualSeat.getCustomer().get());
        assertEquals(cafe.getLayout().getCubes().get(0).getSeats().get(0), actualSeat);
    }

    @Test
    public void givenNotCheckedInCustomer_whenGettingSeatByCustomerId_shouldThrowCustomerNotFoundException() {
        Customer aCustomer = new CustomerFixture().build();

        assertThrows(CustomerNotFoundException.class, () -> cafe.getSeatByCustomerId(aCustomer.getId()));
    }

    @Test
    public void givenClosedCafe_whenGettingSeatByCustomerId_shouldThrowCustomerNotFoundException() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.close();

        assertThrows(CustomerNotFoundException.class, () -> cafe.getSeatByCustomerId(aCustomer.getId()));
    }

    @Test
    public void whenMakingReservation_shouldSaveReservation() {
        cafe.makeReservation(A_RESERVATION_FOR_TWO);

        assertTrue(cafe.getReservations().contains(A_RESERVATION_FOR_TWO));
    }

    @Test
    public void whenMakingReservation_shouldUseProvidedReservationStrategy() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withReservationType(ReservationType.FullCubes).build(), AN_EMPTY_MENU);

        cafe.makeReservation(A_RESERVATION_FOR_TWO);

        assertTrue(cafe.getLayout().getCubes().get(0).getSeats().stream().allMatch(Seat::isCurrentlyReserved));
    }

    @Test
    public void givenAlreadyUsedGroupName_whenMakingReservation_shouldThrowDuplicateGroupNameException() {
        cafe.makeReservation(A_RESERVATION_FOR_TWO);

        assertThrows(DuplicateGroupNameException.class, () -> cafe.makeReservation(A_RESERVATION_FOR_TWO));
    }

    @Test
    public void givenNotEnoughSeats_whenMakingReservation_shouldThrowInsufficientSeatsException() {
        Cafe cafe = new Cafe(A_CUBE_NAME, new CafeConfigurationFixture().withCubeSize(TWO_SEATS_PER_CUBE).build(), AN_EMPTY_MENU);
        cafe.makeReservation(A_RESERVATION_FOR_TWO);

        assertThrows(InsufficientSeatsException.class, () -> cafe.makeReservation(ANOTHER_RESERVATION_FOR_TWO));
    }

    @Test
    public void givenNewReservationType_whenUpdatingConfiguration_shouldUseNewReservationStrategy() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withReservationType(ReservationType.Default).build(), AN_EMPTY_MENU);

        cafe.updateConfiguration(new CafeConfigurationFixture().withReservationType(ReservationType.FullCubes).build());

        cafe.makeReservation(A_RESERVATION_FOR_TWO);
        assertTrue(cafe.getLayout().getCubes().get(0).getSeats().stream().allMatch(Seat::isCurrentlyReserved));
    }

    @Test
    public void givenNewName_whenUpdatingConfiguration_shouldUpdateName() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);
        CafeName newName = new CafeName("newName");

        cafe.updateConfiguration(new CafeConfigurationFixture().withName(newName).build());

        assertEquals(newName, cafe.getName());
    }

    @Test
    public void givenNewCubeSize_whenUpdatingConfiguration_shouldUpdateCubeSize() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withCubeSize(TWO_SEATS_PER_CUBE).build(), AN_EMPTY_MENU);
        CubeSize newCubeSize = new CubeSize(3);

        cafe.updateConfiguration(new CafeConfigurationFixture().withCubeSize(newCubeSize).build());
        cafe.close();

        assertEquals(newCubeSize.value(), cafe.getLayout().getCubes().get(0).getNumberOfSeats());
    }

    @Test
    public void givenNewLocation_whenUpdatingConfiguration_shouldUpdateLocation() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withCountry(Country.CL).build(), A_MENU);
        cafe.addIngredientsToInventory(AN_ORDER.ingredientsNeeded());

        cafe.updateConfiguration(new CafeConfigurationFixture().withCountry(Country.None).build());
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), THE_MATCHING_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        assertEquals(new Amount(0), cafe.getCustomerBill(aCustomer.getId()).taxes());
    }

    @Test
    public void givenNewGroupTipRate_whenUpdatingConfiguration_shouldUpdateGroupTipRate() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withGroupTipRate(new TipRate(0.20f)).build(), A_MENU);
        cafe.addIngredientsToInventory(AN_ORDER.ingredientsNeeded());

        cafe.updateConfiguration(new CafeConfigurationFixture().withGroupTipRate(new TipRate(0.15f)).build());
        Customer aCustomer = new CustomerFixture().build();
        cafe.makeReservation(A_RESERVATION_FOR_TWO);
        cafe.checkIn(aCustomer, Optional.of(A_RESERVATION_FOR_TWO.name()));
        cafe.placeOrder(aCustomer.getId(), THE_MATCHING_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        assertNotEquals(new Amount(0), cafe.getCustomerBill(aCustomer.getId()).tip());
    }

    @Test
    public void givenNewMenuItem_whenAddingMenuItem_shouldBePossibleToOrder() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);

        cafe.addMenuItem(A_MENU_ITEM);

        cafe.checkIn(A_CUSTOMER, Optional.empty());
        assertDoesNotThrow(() -> cafe.placeOrder(A_CUSTOMER.getId(), new PendingOrder(List.of(A_MENU_ITEM.name()))));
    }

    @Test
    public void givenDuplicateMenuItemName_whenAddingMenuItem_shouldThrowDuplicateMenuItemNameException() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);
        cafe.addMenuItem(A_MENU_ITEM);

        assertThrows(DuplicateMenuItemNameException.class, () -> cafe.addMenuItem(A_MENU_ITEM));
    }

    @Test
    public void givenDuplicateMenuItemName_whenAddingMenuItem_shouldNotReplacePreviousItem() {
        Coffee menuItem = new Coffee(A_COFFEE_NAME, AN_AMOUNT, SOME_RECIPE);
        Coffee duplicate = new Coffee(A_COFFEE_NAME, ANOTHER_AMOUNT, SOME_RECIPE);
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);
        cafe.addMenuItem(menuItem);
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        try {
            cafe.addMenuItem(duplicate);
        } catch (DuplicateMenuItemNameException ignored) {
        }

        cafe.placeOrder(aCustomer.getId(), new PendingOrder(List.of(A_COFFEE_NAME)));
        cafe.checkOut(aCustomer.getId());
        Bill bill = cafe.getCustomerBill(aCustomer.getId());
        assertEquals(AN_AMOUNT, bill.subtotal());
    }

    @Test
    public void whenPlacingOrder_shouldAssignItToCustomer() {
        Cafe cafe = cafeWithEnoughInventory();
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        cafe.placeOrder(aCustomer.getId(), THE_MATCHING_PENDING_ORDER);

        assertEquals(AN_ORDER, cafe.findOrderByCustomerId(aCustomer.getId()));
    }

    @Test
    public void givenCustomerWithPreviousOrder_whenPlacingOrder_shouldAppendNewOrderToCustomer() {
        Cafe cafe = cafeWithEnoughInventory();
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), new PendingOrderFixture().fromOrder(AN_ORDER).build());

        cafe.placeOrder(aCustomer.getId(), new PendingOrderFixture().fromOrder(ANOTHER_ORDER).build());

        assertEquals(AN_ORDER.addAll(ANOTHER_ORDER), cafe.findOrderByCustomerId(aCustomer.getId()));
    }

    @Test
    public void givenNotCheckedInCustomer_whenPlacingOrder_shouldThrowCustomerNotFoundException() {
        Cafe cafe = cafeWithEnoughInventory();

        assertThrows(CustomerNotFoundException.class, () -> cafe.placeOrder(new CustomerId("Invalid"), A_PENDING_ORDER));
    }

    @Test
    public void givenEnoughIngredients_whenPlacingOrder_shouldConsumeIngredientsFromInventory() {
        cafe.addIngredientsToInventory(AN_ORDER.ingredientsNeeded());
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        cafe.placeOrder(aCustomer.getId(), THE_MATCHING_PENDING_ORDER);

        assertTrue(cafe.getInventory().getIngredients().values().stream().allMatch(ingredient -> ingredient.quantity().value() == 0));
    }

    @Test
    public void givenNotEnoughIngredients_whenPlacingOrder_shouldThrowInsufficientIngredientsException() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        assertThrows(InsufficientIngredientsException.class, () -> cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER));
    }

    @Test
    public void givenNotEnoughIngredients_whenPlacingOrder_shouldNotConsumeAnyIngredient() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.addIngredientsToInventory(AN_ORDER.ingredientsNeeded());

        try {
            cafe.placeOrder(aCustomer.getId(), new PendingOrderFixture().fromOrder(AN_ORDER.addAll(AN_ORDER)).build());
        } catch (InsufficientIngredientsException ignored) {
        }

        assertTrue(cafe.getInventory().getIngredients().values().containsAll(AN_ORDER.ingredientsNeeded()));
    }

    @Test
    public void givenNotEnoughIngredients_whenPlacingOrder_shouldNotBeAddedToCustomersOrders() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.addIngredientsToInventory(AN_ORDER.ingredientsNeeded());

        try {
            cafe.placeOrder(aCustomer.getId(), new PendingOrderFixture().fromOrder(AN_ORDER.addAll(AN_ORDER)).build());
        } catch (InsufficientIngredientsException ignored) {
        }

        assertTrue(cafe.findOrderByCustomerId(aCustomer.getId()).items().isEmpty());
    }

    @Test
    public void givenInexistantMenuItem_whenPlacingOrder_shouldThrowInvalidMenuOrderException() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        assertThrows(InvalidMenuOrderException.class, () -> cafe.placeOrder(aCustomer.getId(), AN_ORDER_WITH_INEXISTANT_ITEM));
    }

    @Test
    public void givenInexistantMenuItem_whenPlacingOrder_shouldNotConsumeAnyIngredient() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.addIngredientsToInventory(AN_ORDER.ingredientsNeeded());

        try {
            cafe.placeOrder(aCustomer.getId(), AN_ORDER_WITH_INEXISTANT_ITEM);
        } catch (InvalidMenuOrderException ignored) {
        }

        assertTrue(cafe.getInventory().getIngredients().values().containsAll(AN_ORDER.ingredientsNeeded()));
    }

    @Test
    public void givenInexistantMenuItem_whenPlacingOrder_shouldNotBeAddedToCustomersOrders() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.addIngredientsToInventory(ENOUGH_INGREDIENTS);

        try {
            cafe.placeOrder(aCustomer.getId(), AN_ORDER_WITH_INEXISTANT_ITEM);
        } catch (InvalidMenuOrderException ignored) {
        }

        assertTrue(cafe.findOrderByCustomerId(aCustomer.getId()).items().isEmpty());
    }

    @Test
    public void givenInvalidCustomerId_whenGettingOrderByCustomerId_shouldThrowCustomerNotFoundException() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);

        assertThrows(CustomerNotFoundException.class, () -> cafe.findOrderByCustomerId(new CustomerId("Invalid")));
    }

    @Test
    public void givenCheckedInCustomer_whenCheckingOut_shouldFreeSeat() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        cafe.checkOut(aCustomer.getId());

        assertTrue(cafe.getLayout().getCubes().get(0).getSeats().get(0).isCurrentlyAvailable());
    }

    @Test
    public void givenLastGroupMemberToLeave_whenCheckingOut_shouldFreeSeatsStillReservedForGroup() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.makeReservation(A_RESERVATION_FOR_TWO);
        cafe.checkIn(aCustomer, Optional.of(A_RESERVATION_FOR_TWO.name()));

        cafe.checkOut(aCustomer.getId());

        assertTrue(cafe.getLayout().getCubes().get(0).getSeats().get(0).isCurrentlyAvailable());
        assertTrue(cafe.getLayout().getCubes().get(0).getSeats().get(1).isCurrentlyAvailable());
    }

    @Test
    public void givenNonexistentCustomer_whenCheckingOut_shouldThrowCustomerNotFoundException() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);

        assertThrows(CustomerNotFoundException.class, () -> cafe.checkOut(new CustomerId("Invalid")));
    }

    @Test
    public void givenNoOrdersMade_whenCheckingOut_shouldStillCheckOut() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        cafe.checkOut(aCustomer.getId());

        assertEquals(new Amount(0), cafe.getCustomerBill(aCustomer.getId()).total());
        assertThrows(CustomerNotFoundException.class, () -> cafe.getSeatByCustomerId(aCustomer.getId()));
    }

    @Test
    public void givenNonexistentCustomer_whenGettingBill_shouldThrowCustomerNotFoundException() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);

        assertThrows(CustomerNotFoundException.class, () -> cafe.getCustomerBill(new CustomerId("Invalid")));
    }

    @Test
    public void givenNotCheckedOutCustomer_whenGettingBill_shouldThrowCustomerNoBillException() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        assertThrows(CustomerNoBillException.class, () -> cafe.getCustomerBill(aCustomer.getId()));
    }

    @Test
    public void givenNoOrdersMade_whenGettingBill_shouldGetEmptyBill() {
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.checkOut(aCustomer.getId());

        Bill bill = cafe.getCustomerBill(aCustomer.getId());

        assertTrue(bill.order().items().isEmpty());
        assertEquals(new Amount(0), bill.subtotal());
        assertEquals(new Amount(0), bill.taxes());
        assertEquals(new Amount(0), bill.total());
    }

    @Test
    public void givenOrdersMade_whenGettingBill_shouldContainMatchingOrders() {
        Cafe cafe = cafeWithEnoughInventory();
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        Bill bill = cafe.getCustomerBill(aCustomer.getId());

        assertEquals(AN_ORDER.items(), bill.order().items());
    }

    @Test
    public void givenOrdersMade_whenGettingBill_shouldHaveSubtotalMatchingOrdersSum() {
        Cafe cafe = cafeWithEnoughInventory();
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        Bill bill = cafe.getCustomerBill(aCustomer.getId());

        double expectedSubtotal = THE_MATCHING_ORDER.items().stream().mapToDouble(coffee -> coffee.price().value()).sum();
        assertEquals(expectedSubtotal, bill.subtotal().value());
    }

    @Test
    public void givenOrdersMade_whenGettingBill_shouldHaveTotalAccordingToSubtotalAndTaxes() {
        Cafe cafe = cafeWithEnoughInventory();
        cafe.updateConfiguration(new CafeConfigurationFixture().withCountry(Country.CL).build());
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        Bill bill = cafe.getCustomerBill(aCustomer.getId());

        float expectedSubtotal = (float) THE_MATCHING_ORDER.items().stream().mapToDouble(coffee -> coffee.price().value()).sum();
        float taxes = expectedSubtotal * 0.19f;
        float expectedTotal = expectedSubtotal + taxes;
        assertEquals(expectedTotal, bill.total().value());
    }

    @Test
    public void givenCountryWithProvince_whenGettingBill_shouldCalculateFederalAndProvincialTaxes() {
        Cafe cafe = cafeWithEnoughInventory();
        cafe.updateConfiguration(new CafeConfigurationFixture().withCountry(Country.CA).withProvince(Province.QC).build());
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        Bill bill = cafe.getCustomerBill(aCustomer.getId());

        float subtotal = (float) THE_MATCHING_ORDER.items().stream().mapToDouble(coffee -> coffee.price().value()).sum();
        assertEquals(new Amount(subtotal * 0.14975f), bill.taxes());
    }

    @Test
    public void givenCountryWithState_whenGettingBill_shouldCalculateFederalAndStateTaxes() {
        Cafe cafe = cafeWithEnoughInventory();
        cafe.updateConfiguration(new CafeConfigurationFixture().withCountry(Country.US).withState(State.FL).build());
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        Bill bill = cafe.getCustomerBill(aCustomer.getId());

        float subtotal = (float) THE_MATCHING_ORDER.items().stream().mapToDouble(coffee -> coffee.price().value()).sum();
        assertEquals(new Amount(subtotal * 0.06f), bill.taxes());
    }

    @Test
    public void givenCustomerWithoutGroup_whenGettingBill_shouldNotHaveMandatoryGroupTip() {
        Cafe cafe = cafeWithEnoughInventory();
        cafe.updateConfiguration(new CafeConfigurationFixture().withGroupTipRate(new TipRate(0.15f)).build());
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        Bill bill = cafe.getCustomerBill(aCustomer.getId());

        assertEquals(new Amount(0), bill.tip());
    }

    @Test
    public void givenCustomerWithGroup_whenGettingBill_shouldHaveMandatoryGroupTip() {
        Cafe cafe = cafeWithEnoughInventory();
        cafe.updateConfiguration(new CafeConfigurationFixture().withGroupTipRate(new TipRate(0.15f)).build());
        Customer aCustomer = new CustomerFixture().build();
        cafe.makeReservation(A_RESERVATION_FOR_TWO);
        cafe.checkIn(aCustomer, Optional.of(A_RESERVATION_FOR_TWO.name()));
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        Bill bill = cafe.getCustomerBill(aCustomer.getId());

        float subtotal = (float) THE_MATCHING_ORDER.items().stream().mapToDouble(coffee -> coffee.price().value()).sum();
        assertEquals(new Amount(subtotal * 0.15f), bill.tip());
    }

    @Test
    public void givenCountryWithOnlyFederalTax_whenGettingBill_shouldCalculateFederalTax() {
        Cafe cafe = cafeWithEnoughInventory();
        cafe.updateConfiguration(new CafeConfigurationFixture().withCountry(Country.CL).build());
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        Bill bill = cafe.getCustomerBill(aCustomer.getId());

        float subtotal = (float) THE_MATCHING_ORDER.items().stream().mapToDouble(coffee -> coffee.price().value()).sum();
        assertEquals(new Amount(subtotal * 0.19f), bill.taxes());
    }

    @Test
    public void whenAddingIngredients_shouldBeAddedToInventory() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);
        Ingredient anIngredient = new Ingredient(IngredientType.Chocolate, new Quantity(10));

        cafe.addIngredientsToInventory(List.of(anIngredient));

        assertTrue(cafe.getInventory().getIngredients().containsValue(anIngredient));
    }

    @Test
    public void whenClosingCafe_shouldClearAllSeats() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withCubeSize(TWO_SEATS_PER_CUBE).build(), AN_EMPTY_MENU);
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.makeReservation(A_RESERVATION_FOR_TWO);

        cafe.close();

        Layout layout = cafe.getLayout();
        int numberOfAvailableSeats = (int) layout.getCubes().stream().map(Cube::getSeats).flatMap(List::stream).filter(Seat::isCurrentlyAvailable).count();
        assertEquals(SOME_CUBE_NAMES.size() * TWO_SEATS_PER_CUBE.value(), numberOfAvailableSeats);
    }

    @Test
    public void whenClosingCafe_shouldClearAllCustomers() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withCubeSize(TWO_SEATS_PER_CUBE).build(), AN_EMPTY_MENU);
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());

        cafe.close();

        assertThrows(CustomerNotFoundException.class, () -> cafe.getSeatByCustomerId(aCustomer.getId()));
    }

    @Test
    public void whenClosingCafe_shouldClearAllReservations() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().withCubeSize(TWO_SEATS_PER_CUBE).build(), AN_EMPTY_MENU);
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.makeReservation(A_RESERVATION_FOR_TWO);

        cafe.close();

        assertTrue(cafe.getReservations().isEmpty());
    }

    @Test
    public void whenClosingCafe_shouldClearAllOrders() {
        Cafe cafe = cafeWithEnoughInventory();
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);

        cafe.close();
        cafe.checkIn(new CustomerFixture().withCustomerId(aCustomer.getId()).build(), Optional.empty());

        assertTrue(cafe.findOrderByCustomerId(aCustomer.getId()).items().isEmpty());
    }

    @Test
    public void whenClosingCafe_shouldClearAllCustomMenuItems() {
        cafe.addMenuItem(new CoffeeFixture().withName(A_COFFEE_NAME).build());
        cafe.checkIn(A_CUSTOMER, Optional.empty());

        cafe.close();

        assertThrows(InvalidMenuOrderException.class, () -> cafe.placeOrder(A_CUSTOMER.getId(), new PendingOrder(List.of(A_COFFEE_NAME))));
    }

    @Test
    public void whenClosingCafe_shouldNotClearDefaultMenuItems() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), new Menu(List.of(A_DEFAULT_COFFEE)));
        cafe.addMenuItem(new CoffeeFixture().withName(A_COFFEE_NAME).build());

        cafe.close();

        cafe.checkIn(A_CUSTOMER, Optional.empty());
        assertDoesNotThrow(() -> cafe.placeOrder(A_CUSTOMER.getId(), new PendingOrder(List.of(A_DEFAULT_COFFEE.name()))));
    }

    @Test
    public void whenClosingCafe_shouldClearInventory() {
        Cafe cafe = cafeWithEnoughInventory();

        cafe.close();

        assertTrue(cafe.getInventory().getIngredients().isEmpty());
    }

    @Test
    public void whenClosingCafe_shouldClearAllBills() {
        Cafe cafe = cafeWithEnoughInventory();
        Customer aCustomer = new CustomerFixture().build();
        cafe.checkIn(aCustomer, Optional.empty());
        cafe.placeOrder(aCustomer.getId(), A_PENDING_ORDER);
        cafe.checkOut(aCustomer.getId());

        cafe.close();
        cafe.checkIn(aCustomer, Optional.empty());

        assertThrows(CustomerNoBillException.class, () -> cafe.getCustomerBill(aCustomer.getId()));
    }

    private Cafe cafeWithEnoughInventory() {
        Cafe cafe = new Cafe(SOME_CUBE_NAMES, new CafeConfigurationFixture().build(), AN_EMPTY_MENU);
        cafe.addMenuItem(new CoffeeFixture().withAmericano().build());
        cafe.addMenuItem(new CoffeeFixture().withEspresso().build());
        cafe.addIngredientsToInventory(
            List.of(new Ingredient(IngredientType.Milk, new Quantity(1000)), new Ingredient(IngredientType.Chocolate, new Quantity(1000)),
                new Ingredient(IngredientType.Water, new Quantity(1000)), new Ingredient(IngredientType.Espresso, new Quantity(1000))));
        return cafe;
    }
}

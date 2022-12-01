package ca.ulaval.glo4002.cafe.small.cafe.domain.reservation.strategies;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.NoLonersStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.ReservationStrategy;
import ca.ulaval.glo4002.cafe.fixture.CubeFixture;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationFixture;
import ca.ulaval.glo4002.cafe.fixture.SeatFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoLonersStrategyTest {
    public static final GroupName ANOTHER_GROUP_NAME = new GroupName("ANOTHER_GROUP_NAME");
    private static final Reservation A_RESERVATION_FOR_TWO = new ReservationFixture().build();
    private static final Reservation A_RESERVATION_FOR_THREE = new ReservationFixture().withGroupSize(new GroupSize(3)).build();
    private static final Reservation A_RESERVATION_FOR_FOUR = new ReservationFixture().withGroupSize(new GroupSize(4)).build();
    private static final Customer A_CUSTOMER = new CustomerFixture().build();
    private static ReservationStrategy reservationStrategy;

    @BeforeEach
    public void assignStrategy() {
        reservationStrategy = new NoLonersStrategy();
    }

    @Test
    public void givenNotEnoughAvailableSeats_whenMakingReservation_shouldThrowInsufficientSeatsException() {
        List<Cube> cubes = listOfTwoCubesWithOneOccupiedAndOneAvailableSeatEach();

        assertThrows(InsufficientSeatsException.class, () -> reservationStrategy.makeReservation(A_RESERVATION_FOR_THREE, cubes));
    }

    @Test
    public void givenOnlyLonerAvailableSeats_whenMakingReservation_shouldThrowInsufficientSeatsException() {
        List<Cube> cubes = listOfTwoCubesWithOneOccupiedAndOneAvailableSeatEach();

        assertThrows(InsufficientSeatsException.class, () -> reservationStrategy.makeReservation(A_RESERVATION_FOR_TWO, cubes));
    }

    @Test
    public void whenMakingReservation_shouldNotOverwriteUnavailableSeats() {
        List<Cube> cubes = listOfOneCubeWithOneReservedSeatOneOccupiedSeatAndTwoAvailableSeats();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_TWO, cubes);

        assertEquals(ANOTHER_GROUP_NAME, cubes.get(0).getSeats().get(0).getGroupName().get());
        assertTrue(cubes.get(0).getSeats().get(1).isCurrentlyOccupied());
    }

    @Test
    public void givenAvailableLonerSeat_whenMakingReservation_shouldSkipLoner() {
        List<Cube> cubes = listOfACubeWithLonerSeatAndACubeWithEnoughSeats();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_THREE, cubes);

        assertTrue(cubes.get(0).getSeats().get(1).isCurrentlyAvailable());
    }

    @Test
    public void givenTwoAvailableSeatsInFirstCube_whenMakingReservationForThree_shouldOnlyReserveThreeInNextCube() {
        List<Cube> cubes = listOfACubeWithTwoAvailableSeatsAndACubeWithEnoughSeats();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_THREE, cubes);

        assertTrue(cubes.get(0).getSeats().get(0).isCurrentlyAvailable());
        assertTrue(cubes.get(0).getSeats().get(1).isCurrentlyAvailable());

        assertTrue(cubes.get(1).getSeats().get(0).isCurrentlyReserved());
        assertTrue(cubes.get(1).getSeats().get(1).isCurrentlyReserved());
        assertTrue(cubes.get(1).getSeats().get(2).isCurrentlyReserved());
    }

    @Test
    public void givenThreeAvailableSeatsInFirstCubeAndEnoughInSecond_whenMakingReservationForFour_shouldReserveTwoInEachCube() {
        List<Cube> cubes = givenACubeWithThreeAvailableSeatsAndACubeWithEnoughSeats();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_FOUR, cubes);

        assertTrue(cubes.get(0).getSeats().get(0).isCurrentlyReserved());
        assertTrue(cubes.get(0).getSeats().get(1).isCurrentlyReserved());
        assertTrue(cubes.get(0).getSeats().get(2).isCurrentlyAvailable());

        assertTrue(cubes.get(1).getSeats().get(0).isCurrentlyReserved());
        assertTrue(cubes.get(1).getSeats().get(1).isCurrentlyReserved());
        assertTrue(cubes.get(1).getSeats().get(2).isCurrentlyAvailable());
    }

    private List<Cube> listOfTwoCubesWithOneOccupiedAndOneAvailableSeatEach() {
        return List.of(new CubeFixture().withSeatList(List.of(
            new SeatFixture().withCustomer(new CustomerFixture().withCustomerId(new CustomerId("some_id")).build()).withSeatNumber(new SeatNumber(1)).build(),
            new SeatFixture().withSeatNumber(new SeatNumber(2)).build())).build(), new CubeFixture().withSeatList(List.of(
                new SeatFixture().withCustomer(new CustomerFixture().withCustomerId(new CustomerId("some_id2")).build()).withSeatNumber(
                    new SeatNumber(3)).build(), new SeatFixture().withSeatNumber(new SeatNumber(4)).build())).build());
    }

    private List<Cube> listOfOneCubeWithOneReservedSeatOneOccupiedSeatAndTwoAvailableSeats() {
        List<Cube> cubes = List.of(new CubeFixture().build());
        cubes.get(0).getSeats().get(0).reserveForGroup(ANOTHER_GROUP_NAME);
        cubes.get(0).getSeats().get(1).sitCustomer(new CustomerFixture().withCustomerId(new CustomerId("some_id")).build());
        return cubes;
    }

    private List<Cube> listOfACubeWithLonerSeatAndACubeWithEnoughSeats() {
        return List.of(new CubeFixture().withSeatList(List.of(
            new SeatFixture().withCustomer(new CustomerFixture().withCustomerId(new CustomerId("some_id")).build()).withSeatNumber(new SeatNumber(1)).build(),
            new SeatFixture().withSeatNumber(new SeatNumber(2)).build())).build(), new CubeFixture().withSeatList(
            List.of(new SeatFixture().withSeatNumber(new SeatNumber(3)).build(), new SeatFixture().withSeatNumber(new SeatNumber(4)).build(),
                new SeatFixture().withSeatNumber(new SeatNumber(5)).build())).build());
    }

    private List<Cube> listOfACubeWithTwoAvailableSeatsAndACubeWithEnoughSeats() {
        List<Cube> cubes = List.of(new CubeFixture().build(), new CubeFixture().build());
        cubes.get(0).getSeats().get(2).reserveForGroup(ANOTHER_GROUP_NAME);
        cubes.get(0).getSeats().get(3).reserveForGroup(ANOTHER_GROUP_NAME);

        return cubes;
    }

    private List<Cube> givenACubeWithThreeAvailableSeatsAndACubeWithEnoughSeats() {
        List<Cube> cubes = List.of(new CubeFixture().build(), new CubeFixture().build());
        cubes.get(0).getSeats().get(3).reserveForGroup(ANOTHER_GROUP_NAME);

        return cubes;
    }
}

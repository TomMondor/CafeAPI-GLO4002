package ca.ulaval.glo4002.cafe.small.cafe.domain.reservation.strategies;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.ReservationStrategy;
import ca.ulaval.glo4002.cafe.fixture.CubeFixture;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationFixture;
import ca.ulaval.glo4002.cafe.fixture.SeatFixture;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultStrategyTest {

    private static final Reservation A_RESERVATION_FOR_TWO = new ReservationFixture().build();
    private static final Reservation A_RESERVATION_FOR_THREE = new ReservationFixture().withGroupSize(new GroupSize(3)).build();
    private static final Customer A_CUSTOMER = new CustomerFixture().build();

    private static ReservationStrategy reservationStrategy;

    @BeforeAll
    public static void assignStrategy() {
        reservationStrategy = new DefaultStrategy();
    }

    @Test
    public void givenNotEnoughSeatsForGroup_whenMakingReservation_shouldThrowInsufficientSeatsException() {
        List<Cube> cubes = new ArrayList<>();

        assertThrows(InsufficientSeatsException.class, () -> reservationStrategy.makeReservation(A_RESERVATION_FOR_TWO, cubes));
    }

    @Test
    public void givenOneEmptyCube_whenMakingReservation_shouldReserveSeatsInOrder() {
        List<Cube> cubes = listOfOneCubeWithFourAvailableSeats();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_TWO, cubes);

        assertTrue(cubes.get(0).getSeats().get(0).isCurrentlyReserved());
        assertTrue(cubes.get(0).getSeats().get(1).isCurrentlyReserved());
    }

    @Test
    public void givenOneEmptyCube_whenMakingReservation_shouldReserveRightNumberOfSeat() {
        List<Cube> cubes = listOfOneCubeWithFourAvailableSeats();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_TWO, cubes);

        assertTrue(cubes.get(0).getSeats().get(2).isCurrentlyAvailable());
        assertTrue(cubes.get(0).getSeats().get(3).isCurrentlyAvailable());
    }

    @Test
    public void givenTwoCubesAndAGroupSizeBiggerThanCubeSize_whenMakingReservation_shouldReserveOnTwoCubes() {
        List<Cube> cubes = listOfTwoCubesWithTwoAvailableSeats();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_THREE, cubes);

        assertTrue(cubes.get(0).getSeats().get(0).isCurrentlyReserved());
        assertTrue(cubes.get(0).getSeats().get(1).isCurrentlyReserved());
        assertTrue(cubes.get(1).getSeats().get(0).isCurrentlyReserved());
    }

    @Test
    public void givenOneOccupiedSeatInCube_whenMakingReservation_shouldNotReserveOccupiedSeat() {
        List<Cube> cubes = listOfOneCubeWithFirstSeatOccupied();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_TWO, cubes);

        assertTrue(cubes.get(0).getSeats().get(0).isCurrentlyOccupied());
    }

    private List<Cube> listOfOneCubeWithFirstSeatOccupied() {
        return List.of(new CubeFixture().withSeatList(List.of(
            new SeatFixture().withSeatNumber(new SeatNumber(1)).withCustomer(A_CUSTOMER).build(),
            new SeatFixture().withSeatNumber(new SeatNumber(2)).build(),
            new SeatFixture().withSeatNumber(new SeatNumber(3)).build(),
            new SeatFixture().withSeatNumber(new SeatNumber(4)).build()
        )).build());
    }

    private List<Cube> listOfOneCubeWithFourAvailableSeats() {
        return List.of(new CubeFixture().build());
    }

    private List<Cube> listOfTwoCubesWithTwoAvailableSeats() {
        return List.of(new CubeFixture().withSeatList(List.of(
                new SeatFixture().withSeatNumber(new SeatNumber(1)).build(),
                new SeatFixture().withSeatNumber(new SeatNumber(2)).build()
            )).build(),
            new CubeFixture().withSeatList(List.of(
                new SeatFixture().withSeatNumber(new SeatNumber(3)).build(),
                new SeatFixture().withSeatNumber(new SeatNumber(4)).build()
            )).build());
    }
}

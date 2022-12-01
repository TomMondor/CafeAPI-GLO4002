package ca.ulaval.glo4002.cafe.small.cafe.domain.reservation.strategies;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.FullCubesStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.ReservationStrategy;
import ca.ulaval.glo4002.cafe.fixture.CubeFixture;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationFixture;
import ca.ulaval.glo4002.cafe.fixture.SeatFixture;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FullCubesStrategyTest {

    private static final Reservation A_RESERVATION_FOR_TWO = new ReservationFixture().build();
    private static final Reservation A_RESERVATION_FOR_THREE = new ReservationFixture().withGroupSize(new GroupSize(3)).build();
    private static final Seat FIRST_OCCUPIED_SEAT = new SeatFixture().withSeatNumber(new SeatNumber(1))
        .withCustomer(new CustomerFixture().build()).build();

    private static ReservationStrategy reservationStrategy;

    @BeforeEach
    public void assignStrategy() {
        reservationStrategy = new FullCubesStrategy();
    }

    @Test
    public void givenNotEnoughAvailableSeats_whenMakeReservation_shouldThrowInsufficientSeatsException() {
        List<Cube> cubes = new ArrayList<>();

        assertThrows(InsufficientSeatsException.class, () -> reservationStrategy.makeReservation(A_RESERVATION_FOR_TWO, cubes));
    }

    @Test
    public void givenFirstCubeNotEmptyAndSecondCubeEmpty_whenMakeReservation_shouldReserveSecondCube() {
        List<Cube> cubes = listOfTwoCubesWithFirstSeatOfFirstCubeOccupied();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_TWO, cubes);

        assertTrue(cubes.get(1).getSeats().get(0).isCurrentlyReserved());
        assertTrue(cubes.get(1).getSeats().get(1).isCurrentlyReserved());
    }

    @Test
    public void givenFirstCubeNotEmptyAndSecondCubeEmpty_whenMakeReservation_shouldNotReserveFirstCube() {
        List<Cube> cubes = listOfTwoCubesWithFirstSeatOfFirstCubeOccupied();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_TWO, cubes);

        assertTrue(cubes.get(0).getSeats().get(1).isCurrentlyAvailable());
    }

    @Test
    public void givenGroupSizeLargerThanCubeSize_whenMakeReservation_shouldReserveMultipleCubes() {
        List<Cube> cubes = listOfTwoCubesWithAllAvailableSeats();

        reservationStrategy.makeReservation(A_RESERVATION_FOR_THREE, cubes);

        assertTrue(cubes.get(0).getSeats().get(0).isCurrentlyReserved());
        assertTrue(cubes.get(0).getSeats().get(1).isCurrentlyReserved());
        assertTrue(cubes.get(1).getSeats().get(0).isCurrentlyReserved());
        assertTrue(cubes.get(1).getSeats().get(1).isCurrentlyReserved());
    }

    private List<Cube> listOfTwoCubesWithFirstSeatOfFirstCubeOccupied() {
        return List.of(
            new CubeFixture().withSeatList(
                List.of(FIRST_OCCUPIED_SEAT, new SeatFixture().withSeatNumber(new SeatNumber(2)).build())).build(),
            new CubeFixture().withSeatList(
                List.of(new SeatFixture().withSeatNumber(new SeatNumber(3)).build(),
                    new SeatFixture().withSeatNumber(new SeatNumber(4)).build())).build()
        );
    }

    private List<Cube> listOfTwoCubesWithAllAvailableSeats() {
        return List.of(
            new CubeFixture().withSeatList(
                List.of(new SeatFixture().withSeatNumber(new SeatNumber(1)).build(),
                    new SeatFixture().withSeatNumber(new SeatNumber(2)).build())).build(),
            new CubeFixture().withSeatList(
                List.of(new SeatFixture().withSeatNumber(new SeatNumber(3)).build(),
                    new SeatFixture().withSeatNumber(new SeatNumber(4)).build())).build()
        );
    }
}

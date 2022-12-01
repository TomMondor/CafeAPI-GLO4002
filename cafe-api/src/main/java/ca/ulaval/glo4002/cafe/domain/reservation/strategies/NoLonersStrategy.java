package ca.ulaval.glo4002.cafe.domain.reservation.strategies;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

public class NoLonersStrategy implements ReservationStrategy {
    public static final int MIN_SEATS_PER_CUBE = 2;

    @Override
    public void makeReservation(Reservation reservation, List<Cube> cubes) {
        List<Seat> selectedSeats = selectSeats(cubes, reservation.size());
        reserveSeats(selectedSeats, reservation.name());
    }

    private void reserveSeats(List<Seat> seats, GroupName groupName) {
        for (Seat seat : seats) {
            seat.reserveForGroup(groupName);
        }
    }

    private List<Seat> selectSeats(List<Cube> cubes, GroupSize groupSize) {
        List<Seat> selectedSeats = new ArrayList<>();
        for (Cube cube : cubes) {
            List<Seat> seatsToReserve = selectSeatsInCube(groupSize, selectedSeats.size(), cube.getAllAvailableSeats());
            selectedSeats.addAll(seatsToReserve);

            if (isSelectionCompleted(selectedSeats, groupSize)) {
                return selectedSeats;
            }
        }
        throw new InsufficientSeatsException();
    }

    private List<Seat> selectSeatsInCube(GroupSize groupSize, int alreadySelectedSeatsCount, List<Seat> availableSeatsInCube) {
        int numberLeftToReserve = groupSize.value() - alreadySelectedSeatsCount;
        int numberToReserveInCube = findHowManyToReserveInCube(numberLeftToReserve, availableSeatsInCube.size());
        return availableSeatsInCube.subList(0, numberToReserveInCube);
    }

    private int findHowManyToReserveInCube(int numberLeftToReserve, int availableInCube) {
        if (isLonerSeat(availableInCube)) {
            return 0;
        }
        if (reservingAllWouldLeaveALoner(numberLeftToReserve, availableInCube)) {
            return (availableInCube == MIN_SEATS_PER_CUBE) ? 0 : availableInCube - 1;
        }
        return Math.min(availableInCube, numberLeftToReserve);
    }

    private boolean reservingAllWouldLeaveALoner(int numberLeftToReserve, int availableInCube) {
        int numberLeftForNextCubes = numberLeftToReserve - availableInCube;
        return numberLeftForNextCubes > 0 && numberLeftForNextCubes < MIN_SEATS_PER_CUBE;
    }

    private boolean isLonerSeat(int seatsNumber) {
        return seatsNumber < MIN_SEATS_PER_CUBE;
    }

    private boolean isSelectionCompleted(List<Seat> selectedSeats, GroupSize groupSize) {
        return selectedSeats.size() == groupSize.value();
    }
}

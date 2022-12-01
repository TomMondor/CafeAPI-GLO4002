package ca.ulaval.glo4002.cafe.domain.reservation.strategies;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

public class DefaultStrategy implements ReservationStrategy {
    @Override
    public void makeReservation(Reservation reservation, List<Cube> cubes) {
        List<Seat> availableSeats = findAllAvailableSeats(cubes);

        validateEnoughSeats(reservation.size(), availableSeats);

        for (int i = 0; i < reservation.size().value(); i++) {
            availableSeats.get(i).reserveForGroup(reservation.name());
        }
    }

    private void validateEnoughSeats(GroupSize groupSize, List<Seat> availableSeats) {
        if (availableSeats.size() < groupSize.value()) {
            throw new InsufficientSeatsException();
        }
    }

    private List<Seat> findAllAvailableSeats(List<Cube> cubes) {
        List<Seat> availableSeats = new ArrayList<>();
        cubes.forEach(cube -> availableSeats.addAll(cube.getAllAvailableSeats()));
        return availableSeats;
    }
}

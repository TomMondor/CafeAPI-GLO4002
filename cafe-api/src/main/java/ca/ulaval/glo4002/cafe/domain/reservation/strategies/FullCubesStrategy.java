package ca.ulaval.glo4002.cafe.domain.reservation.strategies;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

public class FullCubesStrategy implements ReservationStrategy {
    @Override
    public void makeReservation(Reservation reservation, List<Cube> cubes) {
        List<Cube> availableCubes = findCubesWithAllSeatsAvailable(cubes);
        validateEnoughSeats(availableCubes, reservation.size());
        reserveSeats(availableCubes, reservation);
    }

    private List<Cube> findCubesWithAllSeatsAvailable(List<Cube> cubes) {
        return cubes.stream().filter(cube -> cube.getAllAvailableSeats().size() == cube.getSeats().size()).toList();
    }

    private void validateEnoughSeats(List<Cube> availableCubes, GroupSize groupSize) {
        int availableSeatCount = availableCubes.stream()
            .map(cube -> cube.getSeats().size())
            .toList().stream()
            .mapToInt(Integer::intValue).sum();

        if (availableSeatCount < groupSize.value()) {
            throw new InsufficientSeatsException();
        }
    }

    private void reserveSeats(List<Cube> availableCubes, Reservation reservation) {
        int cubesNeeded = getNumberOfCubesNeeded(availableCubes, reservation.size());
        for (int i = 0; i < cubesNeeded; i++) {
            availableCubes.get(i).reserveAllSeats(reservation.name());
        }
    }

    private int getNumberOfCubesNeeded(List<Cube> availableCubes, GroupSize groupSize) {
        return Math.ceilDiv(groupSize.value(), availableCubes.get(0).getNumberOfSeats());
    }
}

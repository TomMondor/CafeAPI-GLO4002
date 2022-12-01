package ca.ulaval.glo4002.cafe.domain.reservation.strategies;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

public interface ReservationStrategy {
    void makeReservation(Reservation reservation, List<Cube> cubes);
}

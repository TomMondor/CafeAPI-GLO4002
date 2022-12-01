package ca.ulaval.glo4002.cafe.fixture;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.service.reservation.dto.ReservationDTO;

public class ReservationDTOFixture {
    List<Reservation> reservations = new ArrayList<>();

    public ReservationDTOFixture withReservation(List<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public ReservationDTO build() {
        return new ReservationDTO(reservations);
    }
}

package ca.ulaval.glo4002.cafe.fixture;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.cafe.application.registration.dto.ReservationDTO;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

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

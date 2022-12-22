package ca.ulaval.glo4002.cafe.service.registration.dto;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

public record ReservationDTO(List<Reservation> reservations) {
    public ReservationDTO(List<Reservation> reservations) {
        this.reservations = List.copyOf(reservations);
    }
}

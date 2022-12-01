package ca.ulaval.glo4002.cafe.api.reservation.assembler;

import java.util.List;

import ca.ulaval.glo4002.cafe.api.reservation.response.ReservationResponse;
import ca.ulaval.glo4002.cafe.service.reservation.dto.ReservationDTO;

public class ReservationResponseAssembler {
    public List<ReservationResponse> toReservationsResponse(ReservationDTO reservationDTO) {
        return reservationDTO.reservations().stream().map(reservation -> new ReservationResponse(reservation.name().value(), reservation.size().value()))
            .toList();
    }
}

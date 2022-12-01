package ca.ulaval.glo4002.cafe.service.reservation;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.reservation.dto.ReservationDTO;
import ca.ulaval.glo4002.cafe.service.reservation.parameter.ReservationRequestParams;

public class ReservationService {
    private final CafeRepository cafeRepository;
    private final ReservationFactory reservationFactory;

    public ReservationService(CafeRepository cafeRepository, ReservationFactory reservationFactory) {
        this.cafeRepository = cafeRepository;
        this.reservationFactory = reservationFactory;
    }

    public ReservationDTO getReservations() {
        Cafe cafe = cafeRepository.get();
        return new ReservationDTO(cafe.getReservations());
    }

    public void makeReservation(ReservationRequestParams reservationRequestParams) {
        Cafe cafe = cafeRepository.get();
        Reservation reservation = reservationFactory.createReservation(reservationRequestParams.groupName(), reservationRequestParams.groupSize());
        cafe.makeReservation(reservation);
        cafeRepository.saveOrUpdate(cafe);
    }
}

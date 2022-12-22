package ca.ulaval.glo4002.cafe.service.registration;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.service.registration.dto.ReservationDTO;
import ca.ulaval.glo4002.cafe.service.registration.parameter.ReservationRequestParams;

public class RegistrationService {
    private final CafeRepository cafeRepository;
    private final ReservationFactory reservationFactory;
    private final CustomerFactory customerFactory;

    public RegistrationService(CafeRepository cafeRepository, ReservationFactory reservationFactory, CustomerFactory customerFactory) {
        this.cafeRepository = cafeRepository;
        this.reservationFactory = reservationFactory;
        this.customerFactory = customerFactory;
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

    public void checkIn(CheckInCustomerParams checkInCustomerParams) {
        Cafe cafe = cafeRepository.get();

        Customer customer = customerFactory.createCustomer(checkInCustomerParams.customerId(), checkInCustomerParams.customerName());
        cafe.checkIn(customer, checkInCustomerParams.groupName());

        cafeRepository.saveOrUpdate(cafe);
    }

    public void checkOut(CheckOutCustomerParams checkOutCustomerParams) {
        Cafe cafe = cafeRepository.get();
        cafe.checkOut(checkOutCustomerParams.customerId());
        cafeRepository.saveOrUpdate(cafe);
    }
}

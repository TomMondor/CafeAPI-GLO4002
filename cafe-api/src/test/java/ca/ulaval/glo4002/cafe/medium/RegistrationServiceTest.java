package ca.ulaval.glo4002.cafe.medium;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.application.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.application.registration.RegistrationService;
import ca.ulaval.glo4002.cafe.application.registration.dto.ReservationDTO;
import ca.ulaval.glo4002.cafe.application.registration.parameter.ReservationRequestParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest {
    private static final GroupSize A_GROUP_SIZE = new GroupSize(4);
    private static final GroupName A_GROUP_NAME = new GroupName("My group");
    private static final ReservationRequestParams A_RESERVATION_REQUEST_PARAMS = new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value());
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("123");
    private static final String A_CUSTOMER_NAME = "Joe";
    private static final CheckInCustomerParams CHECK_IN_CUSTOMER_PARAMS = new CheckInCustomerParams(A_CUSTOMER_ID.value(), A_CUSTOMER_NAME, null);
    private static final CheckOutCustomerParams CHECK_OUT_CUSTOMER_PARAMS = new CheckOutCustomerParams(A_CUSTOMER_ID.value());

    private RegistrationService registrationService;
    private CustomerService customerService;

    @BeforeEach
    public void instanciateAttributes() {
        CafeRepository cafeRepository = new InMemoryCafeRepository();
        registrationService = new RegistrationService(cafeRepository, new ReservationFactory(), new CustomerFactory());
        customerService = new CustomerService(cafeRepository);
        Cafe cafe = new CafeFactory().createCafe(List.of());
        cafeRepository.saveOrUpdate(cafe);
    }

    @Test
    public void whenMakingReservation_shouldSaveReservation() {
        registrationService.makeReservation(A_RESERVATION_REQUEST_PARAMS);
        ReservationDTO actualReservationDTO = registrationService.getReservations();

        assertEquals(A_GROUP_SIZE, actualReservationDTO.reservations().get(0).size());
        assertEquals(A_GROUP_NAME, actualReservationDTO.reservations().get(0).name());
    }

    @Test
    public void whenCheckingIn_shouldSaveCustomer() {
        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS);

        assertDoesNotThrow(() -> customerService.getCustomer(A_CUSTOMER_ID));
    }

    @Test
    public void givenSavedCustomer_whenCheckingOut_shouldSaveCustomerBill() {
        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS);

        registrationService.checkOut(CHECK_OUT_CUSTOMER_PARAMS);

        assertNotNull(customerService.getCustomerBill(A_CUSTOMER_ID));
    }

    @Test
    public void givenSavedCustomer_whenCheckingOut_shouldRemoveCustomerFromSeat() {
        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS);

        registrationService.checkOut(CHECK_OUT_CUSTOMER_PARAMS);

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(A_CUSTOMER_ID));
    }
}

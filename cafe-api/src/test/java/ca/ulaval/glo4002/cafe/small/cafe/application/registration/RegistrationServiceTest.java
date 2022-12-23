package ca.ulaval.glo4002.cafe.small.cafe.application.registration;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.application.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.application.registration.RegistrationService;
import ca.ulaval.glo4002.cafe.application.registration.dto.ReservationDTO;
import ca.ulaval.glo4002.cafe.application.registration.parameter.ReservationRequestParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.fixture.CafeFixture;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationDTOFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {
    private static final GroupName A_GROUP_NAME = new GroupName("My Hero Academia");
    private static final GroupSize A_GROUP_SIZE = new GroupSize(5);
    private static final GroupName ANOTHER_GROUP_NAME = new GroupName("Batman VS the Chipmunks");
    private static final GroupSize ANOTHER_GROUP_SIZE = new GroupSize(3);
    private static final Reservation A_RESERVATION = new ReservationFixture().withGroupName(A_GROUP_NAME).withGroupSize(A_GROUP_SIZE).build();
    private static final Reservation ANOTHER_RESERVATION = new ReservationFixture().withGroupName(ANOTHER_GROUP_NAME).withGroupSize(ANOTHER_GROUP_SIZE).build();
    private static final List<Reservation> SOME_RESERVATIONS = List.of(A_RESERVATION, ANOTHER_RESERVATION);
    private static final Cafe A_CAFE = new CafeFixture().build();
    private static final CustomerName CUSTOMER_NAME = new CustomerName("Bob Bisonette");
    private static final CustomerId CUSTOMER_ID = new CustomerId("ABC273031");
    private static final CheckInCustomerParams CHECK_IN_CUSTOMER_PARAMS_NO_GROUP = new CheckInCustomerParams(CUSTOMER_ID.value(), CUSTOMER_NAME.value(), null);
    private static final CheckOutCustomerParams CHECKOUT_CUSTOMER_PARAMS = new CheckOutCustomerParams(CUSTOMER_ID.value());
    private static final Customer CUSTOMER = new CustomerFixture().withCustomerId(CUSTOMER_ID).withCustomerName(CUSTOMER_NAME).build();

    private RegistrationService registrationService;
    private ReservationFactory reservationFactory;
    private CustomerFactory customerFactory;
    private CafeRepository cafeRepository;
    private Cafe mockCafe;

    @BeforeEach
    public void createReservationService() {
        reservationFactory = mock(ReservationFactory.class);
        customerFactory = mock(CustomerFactory.class);
        cafeRepository = mock(CafeRepository.class);
        registrationService = new RegistrationService(cafeRepository, reservationFactory, customerFactory);

        mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);
    }

    @Test
    public void whenMakingReservation_shouldRetrieveCafe() {
        Cafe aCafe = new CafeFixture().build();
        when(cafeRepository.get()).thenReturn(aCafe);
        when(reservationFactory.createReservation(any(), any())).thenReturn(A_RESERVATION);

        registrationService.makeReservation(new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value()));

        verify(cafeRepository).get();
    }

    @Test
    public void whenMakingReservation_shouldCreateNewGroup() {
        Cafe aCafe = new CafeFixture().build();
        when(cafeRepository.get()).thenReturn(aCafe);
        when(reservationFactory.createReservation(A_GROUP_NAME, A_GROUP_SIZE)).thenReturn(A_RESERVATION);

        registrationService.makeReservation(new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value()));

        verify(reservationFactory).createReservation(A_GROUP_NAME, A_GROUP_SIZE);
    }

    @Test
    public void whenMakingReservation_shouldTellCafeToMakeReservation() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);
        when(reservationFactory.createReservation(any(), any())).thenReturn(A_RESERVATION);

        registrationService.makeReservation(new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value()));

        verify(mockCafe).makeReservation(A_RESERVATION);
    }

    @Test
    public void whenMakingReservation_shouldUpdateCafe() {
        Cafe aCafe = new CafeFixture().build();
        when(cafeRepository.get()).thenReturn(aCafe);
        when(reservationFactory.createReservation(any(), any())).thenReturn(A_RESERVATION);

        registrationService.makeReservation(new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value()));

        verify(cafeRepository).saveOrUpdate(aCafe);
    }

    @Test
    public void whenGettingReservations_shouldRetrieveCafe() {
        when(cafeRepository.get()).thenReturn(A_CAFE);

        registrationService.getReservations();

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingReservations_shouldRetrieveCafeReservations() {
        Cafe cafe = cafeWithReservations(SOME_RESERVATIONS);
        when(cafeRepository.get()).thenReturn(cafe);

        registrationService.getReservations();

        verify(cafe).getReservations();
    }

    @Test
    public void whenGettingReservations_shouldReturnMatchingReservationDTO() {
        Cafe cafe = cafeWithReservations(SOME_RESERVATIONS);
        when(cafeRepository.get()).thenReturn(cafe);
        ReservationDTO expectedDTO = new ReservationDTOFixture().withReservation(SOME_RESERVATIONS).build();

        ReservationDTO reservationDTO = registrationService.getReservations();

        assertEquals(expectedDTO, reservationDTO);
    }

    @Test
    public void whenCheckingInCustomer_shouldGetCafe() {
        when(customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME)).thenReturn(CUSTOMER);

        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS_NO_GROUP);

        verify(cafeRepository).get();
    }

    @Test
    public void whenCheckingInCustomer_shouldCreateNewCustomer() {
        when(customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME)).thenReturn(CUSTOMER);

        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS_NO_GROUP);

        verify(customerFactory).createCustomer(CUSTOMER_ID, CUSTOMER_NAME);
    }

    @Test
    public void whenCheckingInCustomer_shouldCheckInInCafe() {
        when(customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME)).thenReturn(CUSTOMER);

        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS_NO_GROUP);

        verify(mockCafe).checkIn(CUSTOMER, CHECK_IN_CUSTOMER_PARAMS_NO_GROUP.groupName());
    }

    @Test
    public void whenCheckingInCustomer_shouldUpdateCafe() {
        when(customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME)).thenReturn(CUSTOMER);

        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS_NO_GROUP);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenCheckingOut_shouldGetCafe() {
        registrationService.checkOut(CHECKOUT_CUSTOMER_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenCheckingOut_shouldCheckOutWithCustomerId() {
        registrationService.checkOut(CHECKOUT_CUSTOMER_PARAMS);

        verify(mockCafe).checkOut(CUSTOMER_ID);
    }

    @Test
    public void whenCheckingOut_shouldUpdateCafe() {
        registrationService.checkOut(CHECKOUT_CUSTOMER_PARAMS);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    private Cafe cafeWithReservations(List<Reservation> reservations) {
        Cafe cafe = mock(Cafe.class);
        when(cafe.getReservations()).thenReturn(reservations);
        return cafe;
    }
}

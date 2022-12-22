package ca.ulaval.glo4002.cafe.small.cafe.api.customer.registration;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.registration.RegistrationResource;
import ca.ulaval.glo4002.cafe.api.customer.registration.request.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.customer.registration.request.CheckOutRequest;
import ca.ulaval.glo4002.cafe.api.customer.registration.request.ReservationRequest;
import ca.ulaval.glo4002.cafe.fixture.request.CheckInRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.CheckOutRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.ReservationRequestFixture;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.service.reservation.ReservationService;
import ca.ulaval.glo4002.cafe.service.reservation.dto.ReservationDTO;
import ca.ulaval.glo4002.cafe.service.reservation.parameter.ReservationRequestParams;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RegistrationResourceTest {
    private static final String CUSTOMER_ID = "customerId";
    private static final String CUSTOMER_NAME = "Bob";
    private static final String GROUP_NAME = "team";
    private static final int GROUP_SIZE = 4;
    private static final ReservationDTO A_RESERVATION_DTO = new ReservationDTO(List.of());

    private RegistrationResource registrationResource;
    private ReservationService reservationService;
    private CustomerService customerService;

    @BeforeEach
    public void createReservationResource() {
        reservationService = mock(ReservationService.class);
        customerService = mock(CustomerService.class);
        registrationResource = new RegistrationResource(reservationService, customerService);
    }

    @Test
    public void whenPostingReservation_shouldMakeReservation() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
            .withGroupName(GROUP_NAME)
            .withGroupSize(GROUP_SIZE)
            .build();
        ReservationRequestParams reservationRequestParams = new ReservationRequestParams(GROUP_NAME, GROUP_SIZE);

        registrationResource.postReservation(reservationRequest);

        verify(reservationService).makeReservation(reservationRequestParams);
    }

    @Test
    public void givenValidRequest_whenPostingReservation_shouldReturn200() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
            .withGroupName(GROUP_NAME)
            .withGroupSize(GROUP_SIZE)
            .build();

        Response response = registrationResource.postReservation(reservationRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenGettingReservation_shouldGetReservation() {
        when(reservationService.getReservations()).thenReturn(A_RESERVATION_DTO);

        registrationResource.getReservations();

        verify(reservationService).getReservations();
    }

    @Test
    public void whenGettingReservation_shouldReturn200() {
        when(reservationService.getReservations()).thenReturn(A_RESERVATION_DTO);

        Response response = registrationResource.getReservations();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenCheckingIn_shouldCheckInCustomer() {
        CheckInRequest checkInRequest = new CheckInRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .withCustomerName(CUSTOMER_NAME)
            .withGroupName(GROUP_NAME)
            .build();
        CheckInCustomerParams checkInCustomerParams = new CheckInCustomerParams(CUSTOMER_ID, CUSTOMER_NAME, GROUP_NAME);

        registrationResource.checkIn(checkInRequest);

        verify(customerService).checkIn(checkInCustomerParams);
    }

    @Test
    public void givenValidRequest_whenCheckingIn_shouldReturn201() {
        CheckInRequest checkInRequest = new CheckInRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .withCustomerName(CUSTOMER_NAME)
            .withGroupName(GROUP_NAME)
            .build();

        Response response = registrationResource.checkIn(checkInRequest);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenCheckingIn_shouldReturnPathToCustomerInLocation() {
        CheckInRequest checkInRequest = new CheckInRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .withCustomerName(CUSTOMER_NAME)
            .withGroupName(GROUP_NAME)
            .build();

        Response response = registrationResource.checkIn(checkInRequest);

        assertTrue(response.getLocation().toString().contains("/customers/" + CUSTOMER_ID));
    }

    @Test
    public void whenCheckingOut_shouldCheckOutCustomer() {
        CheckOutRequest checkOutRequest = new CheckOutRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .build();
        CheckOutCustomerParams checkOutCustomerParams = new CheckOutCustomerParams(CUSTOMER_ID);

        registrationResource.checkOut(checkOutRequest);

        verify(customerService).checkOut(checkOutCustomerParams);
    }

    @Test
    public void givenValidRequest_whenCheckingOut_shouldReturn201() {
        CheckOutRequest checkOutRequest = new CheckOutRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .build();

        Response response = registrationResource.checkOut(checkOutRequest);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenCheckingOut_shouldReturnPathToBillInLocation() {
        CheckOutRequest checkOutRequest = new CheckOutRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .build();

        Response response = registrationResource.checkOut(checkOutRequest);

        assertTrue(response.getLocation().toString().contains("/customers/" + CUSTOMER_ID + "/bill"));
    }
}

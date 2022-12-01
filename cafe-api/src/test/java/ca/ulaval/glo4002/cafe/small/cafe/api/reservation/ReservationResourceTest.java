package ca.ulaval.glo4002.cafe.small.cafe.api.reservation;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.reservation.ReservationResource;
import ca.ulaval.glo4002.cafe.api.reservation.request.ReservationRequest;
import ca.ulaval.glo4002.cafe.fixture.request.ReservationRequestFixture;
import ca.ulaval.glo4002.cafe.service.reservation.ReservationService;
import ca.ulaval.glo4002.cafe.service.reservation.dto.ReservationDTO;
import ca.ulaval.glo4002.cafe.service.reservation.parameter.ReservationRequestParams;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservationResourceTest {
    private static final String GROUP_NAME = "Les 4-Fées";
    private static final int GROUP_SIZE = 4;
    private static final ReservationDTO A_RESERVATION_DTO = new ReservationDTO(List.of());

    private ReservationResource reservationResource;
    private ReservationService reservationService;

    @BeforeEach
    public void createReservationResource() {
        reservationService = mock(ReservationService.class);
        reservationResource = new ReservationResource(reservationService);
    }

    @Test
    public void whenPostingReservation_shouldMakeReservation() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
            .withGroupName(GROUP_NAME)
            .withGroupSize(GROUP_SIZE)
            .build();
        ReservationRequestParams reservationRequestParams = new ReservationRequestParams(GROUP_NAME, GROUP_SIZE);

        reservationResource.postReservation(reservationRequest);

        verify(reservationService).makeReservation(reservationRequestParams);
    }

    @Test
    public void givenValidRequest_whenPostingReservation_shouldReturn200() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
            .withGroupName(GROUP_NAME)
            .withGroupSize(GROUP_SIZE)
            .build();

        Response response = reservationResource.postReservation(reservationRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenGettingReservation_shouldGetReservation() {
        when(reservationService.getReservations()).thenReturn(A_RESERVATION_DTO);

        reservationResource.getReservations();

        verify(reservationService).getReservations();
    }

    @Test
    public void whenGettingReservation_shouldReturn200() {
        when(reservationService.getReservations()).thenReturn(A_RESERVATION_DTO);

        Response response = reservationResource.getReservations();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

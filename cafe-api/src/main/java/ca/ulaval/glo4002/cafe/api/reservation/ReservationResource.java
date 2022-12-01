package ca.ulaval.glo4002.cafe.api.reservation;

import java.util.List;

import ca.ulaval.glo4002.cafe.api.reservation.assembler.ReservationResponseAssembler;
import ca.ulaval.glo4002.cafe.api.reservation.request.ReservationRequest;
import ca.ulaval.glo4002.cafe.api.reservation.response.ReservationResponse;
import ca.ulaval.glo4002.cafe.service.reservation.ReservationService;
import ca.ulaval.glo4002.cafe.service.reservation.parameter.ReservationRequestParams;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {
    private final ReservationService reservationService;
    private final ReservationResponseAssembler reservationResponseAssembler = new ReservationResponseAssembler();

    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @POST
    public Response postReservation(@Valid ReservationRequest reservationRequest) {
        ReservationRequestParams requestParams = ReservationRequestParams.from(reservationRequest.group_name, reservationRequest.group_size);
        reservationService.makeReservation(requestParams);
        return Response.ok().build();
    }

    @GET
    public Response getReservations() {
        List<ReservationResponse> reservations = reservationResponseAssembler.toReservationsResponse(reservationService.getReservations());
        return Response.ok(reservations).build();
    }
}

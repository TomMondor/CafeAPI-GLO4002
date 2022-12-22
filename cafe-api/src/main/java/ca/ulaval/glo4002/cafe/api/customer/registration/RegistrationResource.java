package ca.ulaval.glo4002.cafe.api.customer.registration;

import java.net.URI;
import java.util.List;

import ca.ulaval.glo4002.cafe.api.customer.registration.assembler.ReservationResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.registration.request.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.customer.registration.request.CheckOutRequest;
import ca.ulaval.glo4002.cafe.api.customer.registration.request.ReservationRequest;
import ca.ulaval.glo4002.cafe.api.customer.registration.response.ReservationResponse;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.service.reservation.ReservationService;
import ca.ulaval.glo4002.cafe.service.reservation.parameter.ReservationRequestParams;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {
    private final ReservationService reservationService;
    private final ReservationResponseAssembler reservationResponseAssembler = new ReservationResponseAssembler();
    private final CustomerService customersService;

    public RegistrationResource(ReservationService reservationService, CustomerService customersService) {
        this.reservationService = reservationService;
        this.customersService = customersService;
    }

    @POST
    @Path("/reservations")
    public Response postReservation(@Valid ReservationRequest reservationRequest) {
        ReservationRequestParams requestParams = ReservationRequestParams.from(reservationRequest.group_name, reservationRequest.group_size);
        reservationService.makeReservation(requestParams);
        return Response.ok().build();
    }

    @GET
    @Path("/reservations")
    public Response getReservations() {
        List<ReservationResponse> reservations = reservationResponseAssembler.toReservationsResponse(reservationService.getReservations());
        return Response.ok(reservations).build();
    }

    @POST
    @Path("/check-in")
    public Response checkIn(@Valid CheckInRequest checkInRequest) {
        CheckInCustomerParams checkInCustomerParams =
            CheckInCustomerParams.from(checkInRequest.customer_id, checkInRequest.customer_name, checkInRequest.group_name);
        customersService.checkIn(checkInCustomerParams);
        return Response.created(URI.create("/customers/" + checkInCustomerParams.customerId().value())).build();
    }

    @POST
    @Path("/checkout")
    public Response checkOut(@Valid CheckOutRequest checkOutRequest) {
        CheckOutCustomerParams checkOutCustomerParams = CheckOutCustomerParams.from(checkOutRequest.customer_id);
        customersService.checkOut(checkOutCustomerParams);
        return Response.created(URI.create("/customers/" + checkOutCustomerParams.customerId().value() + "/bill")).build();
    }
}

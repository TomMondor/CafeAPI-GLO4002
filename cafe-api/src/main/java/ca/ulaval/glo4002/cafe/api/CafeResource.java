package ca.ulaval.glo4002.cafe.api;

import java.net.URI;

import ca.ulaval.glo4002.cafe.api.assembler.InventoryResponseAssembler;
import ca.ulaval.glo4002.cafe.api.layout.assembler.LayoutResponseAssembler;
import ca.ulaval.glo4002.cafe.api.layout.response.LayoutResponse;
import ca.ulaval.glo4002.cafe.api.request.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.request.CheckOutRequest;
import ca.ulaval.glo4002.cafe.api.request.ConfigurationRequest;
import ca.ulaval.glo4002.cafe.api.request.InventoryRequest;
import ca.ulaval.glo4002.cafe.api.response.InventoryResponse;
import ca.ulaval.glo4002.cafe.service.CafeService;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.service.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class CafeResource {
    private final CafeService cafeService;
    private final CustomerService customersService;
    private final InventoryResponseAssembler inventoryResponseAssembler = new InventoryResponseAssembler();
    private final LayoutResponseAssembler layoutResponseAssembler = new LayoutResponseAssembler();

    public CafeResource(CafeService cafeService, CustomerService customersService) {
        this.cafeService = cafeService;
        this.customersService = customersService;
    }

    @GET
    @Path("/layout")
    public Response layout() {
        LayoutResponse response = layoutResponseAssembler.toLayoutResponse(cafeService.getLayout());
        return Response.ok(response).build();
    }

    @POST
    @Path("/close")
    public Response close() {
        cafeService.closeCafe();
        return Response.ok().build();
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

    @POST
    @Path("/config")
    public Response updateConfiguration(@Valid ConfigurationRequest configurationRequest) {
        ConfigurationParams configurationParams =
            ConfigurationParams.from(configurationRequest.cube_size, configurationRequest.organization_name, configurationRequest.group_reservation_method,
                configurationRequest.country, configurationRequest.province, configurationRequest.state, configurationRequest.group_tip_rate);
        cafeService.updateConfiguration(configurationParams);
        return Response.ok().build();
    }

    @GET
    @Path("/inventory")
    public Response getInventory() {
        InventoryResponse response = inventoryResponseAssembler.toInventoryResponse(cafeService.getInventory());
        return Response.ok(response).build();
    }

    @PUT
    @Path("/inventory")
    public Response putInventory(@Valid InventoryRequest inventoryRequest) {
        IngredientsParams ingredientsParams =
            IngredientsParams.from(inventoryRequest.Chocolate, inventoryRequest.Milk, inventoryRequest.Water, inventoryRequest.Espresso);
        cafeService.addIngredientsToInventory(ingredientsParams);
        return Response.status(200).build();
    }
}

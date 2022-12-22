package ca.ulaval.glo4002.cafe.api.operation;

import ca.ulaval.glo4002.cafe.api.operation.request.CoffeeRequest;
import ca.ulaval.glo4002.cafe.api.operation.request.ConfigurationRequest;
import ca.ulaval.glo4002.cafe.service.operation.OperationService;
import ca.ulaval.glo4002.cafe.service.parameter.CoffeeParams;
import ca.ulaval.glo4002.cafe.service.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class OperationResource {
    private final OperationService cafeService;

    public OperationResource(OperationService cafeService) {
        this.cafeService = cafeService;
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

    @POST
    @Path("/menu")
    public Response addMenuItem(@Valid CoffeeRequest coffeeRequest) {
        IngredientsParams ingredientsParams = IngredientsParams.from(
            coffeeRequest.ingredients.Chocolate,
            coffeeRequest.ingredients.Milk,
            coffeeRequest.ingredients.Water,
            coffeeRequest.ingredients.Espresso);
        CoffeeParams coffeeParams = CoffeeParams.from(coffeeRequest.name, coffeeRequest.cost, ingredientsParams);

        cafeService.addMenuItem(coffeeParams);
        return Response.status(200).build();
    }

    @POST
    @Path("/close")
    public Response close() {
        cafeService.closeCafe();
        return Response.ok().build();
    }
}

package ca.ulaval.glo4002.cafe.small.cafe.api.operation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.inventory.request.InventoryRequest;
import ca.ulaval.glo4002.cafe.api.operation.OperationResource;
import ca.ulaval.glo4002.cafe.api.operation.request.CoffeeRequest;
import ca.ulaval.glo4002.cafe.api.operation.request.ConfigurationRequest;
import ca.ulaval.glo4002.cafe.fixture.request.ConfigurationRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.InventoryRequestFixture;
import ca.ulaval.glo4002.cafe.service.operation.OperationService;
import ca.ulaval.glo4002.cafe.service.parameter.CoffeeParams;
import ca.ulaval.glo4002.cafe.service.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OperationResourceTest {
    private static final int CUBE_SIZE = 1;
    private static final String ORGANISATION_NAME = "Bob";
    private static final String GROUP_RESERVATION_METHOD = "Default";
    private static final String COUNTRY = "CA";
    private static final String PROVINCE = "QC";
    private static final String STATE = "";
    private static final int GROUP_TIP_RATE = 0;
    private static final float A_COST = 1.0f;
    private static final String A_NAME = "coffee";
    private static final InventoryRequest SOME_INGREDIENTS = new InventoryRequestFixture().build();

    private OperationService cafeService;
    private OperationResource operationResource;

    @BeforeEach
    public void createCafeResource() {
        cafeService = mock(OperationService.class);
        operationResource = new OperationResource(cafeService);
    }

    @Test
    public void whenClosing_shouldCloseCafe() {
        operationResource.close();

        verify(cafeService).closeCafe();
    }

    @Test
    public void whenClosing_shouldReturn200() {
        Response response = operationResource.close();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenUpdatingConfiguration_shouldUpdateConfiguration() {
        ConfigurationRequest configurationRequest = new ConfigurationRequestFixture()
            .withCubeSize(CUBE_SIZE)
            .withOrganizationName(ORGANISATION_NAME)
            .withGroupReservationMethod(GROUP_RESERVATION_METHOD)
            .withCountry(COUNTRY)
            .withProvince(PROVINCE)
            .withState(STATE)
            .withTipRate(GROUP_TIP_RATE)
            .build();
        ConfigurationParams configurationParams =
            new ConfigurationParams(CUBE_SIZE, ORGANISATION_NAME, GROUP_RESERVATION_METHOD, COUNTRY, PROVINCE, STATE, GROUP_TIP_RATE);

        operationResource.updateConfiguration(configurationRequest);

        verify(cafeService).updateConfiguration(configurationParams);
    }

    @Test
    public void givenValidRequest_whenUpdatingConfiguration_shouldReturn200() {
        ConfigurationRequest configurationRequest = new ConfigurationRequestFixture()
            .withCubeSize(CUBE_SIZE)
            .withOrganizationName(ORGANISATION_NAME)
            .withGroupReservationMethod(GROUP_RESERVATION_METHOD)
            .withCountry(COUNTRY)
            .withProvince(PROVINCE)
            .withState(STATE)
            .withTipRate(GROUP_TIP_RATE)
            .build();

        Response response = operationResource.updateConfiguration(configurationRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenAddingMenuItem_shouldAddMenuItem() {
        CoffeeRequest coffeeRequest = new CoffeeRequest();
        coffeeRequest.cost = A_COST;
        coffeeRequest.name = A_NAME;
        coffeeRequest.ingredients = SOME_INGREDIENTS;
        CoffeeParams expectedParams = new CoffeeParams(A_NAME, A_COST,
            new IngredientsParams(SOME_INGREDIENTS.Chocolate, SOME_INGREDIENTS.Milk, SOME_INGREDIENTS.Water, SOME_INGREDIENTS.Espresso));

        operationResource.addMenuItem(coffeeRequest);

        verify(cafeService).addMenuItem(expectedParams);
    }

    @Test
    public void givenValidRequest_whenAddingMenuItem_shouldReturn200() {
        CoffeeRequest coffeeRequest = new CoffeeRequest();
        coffeeRequest.cost = A_COST;
        coffeeRequest.name = A_NAME;
        coffeeRequest.ingredients = SOME_INGREDIENTS;

        Response response = operationResource.addMenuItem(coffeeRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

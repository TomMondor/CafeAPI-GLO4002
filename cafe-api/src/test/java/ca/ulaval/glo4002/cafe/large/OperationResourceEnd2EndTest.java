package ca.ulaval.glo4002.cafe.large;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.response.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.customerAction.request.OrderRequest;
import ca.ulaval.glo4002.cafe.api.customer.registration.request.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.customer.registration.request.ReservationRequest;
import ca.ulaval.glo4002.cafe.api.inventory.request.InventoryRequest;
import ca.ulaval.glo4002.cafe.api.layout.SeatStatus;
import ca.ulaval.glo4002.cafe.api.layout.response.LayoutResponse;
import ca.ulaval.glo4002.cafe.api.operation.request.CoffeeRequest;
import ca.ulaval.glo4002.cafe.api.operation.request.ConfigurationRequest;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.fixture.request.CheckInRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.ConfigurationRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.InventoryRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.OrderRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.ReservationRequestFixture;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationResourceEnd2EndTest {
    private static final String BASE_URL = "http://localhost:8181";
    private static final String AN_ORGANISATION_NAME = "MyLittleCafe";
    private static final int A_CUBE_SIZE = 5;
    private static final String A_VALID_ID = "test_ID";
    private static final String A_VALID_GROUP_NAME = "test_group";
    private static final int A_VALID_STOCK = 100;
    private static final float A_COST = 1.0f;
    private static final String A_COFFEE_NAME = "coffee";
    private static final InventoryRequest SOME_INGREDIENTS = new InventoryRequestFixture().build();

    private TestServer server;

    @BeforeEach
    public void startServer() throws Exception {
        server = new TestServer();
        server.start();
    }

    @AfterEach
    public void closeServer() throws Exception {
        server.stop();
    }

    @Test
    public void whenClosing_shouldReturn200() {
        Response response = when().post(BASE_URL + "/close");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void givenValidConfigRequest_whenUpdatingConfig_shouldReturn200() {
        ConfigurationRequest configRequest = new ConfigurationRequestFixture().build();

        Response response = given().contentType("application/json").body(configRequest).when().post(BASE_URL + "/config");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void givenConfigRequestWithAdditionalFields_whenUpdatingConfig_shouldReturn200() {
        ConfigurationRequest configRequest = new ConfigurationRequestFixture().build();
        Map<String, Object> body = new HashMap<>();
        body.put("group_reservation_method", configRequest.group_reservation_method);
        body.put("organization_name", configRequest.organization_name);
        body.put("cube_size", configRequest.cube_size);
        body.put("country", configRequest.country);
        body.put("province", configRequest.province);
        body.put("state", configRequest.state);
        body.put("additional_field", "additional_value");

        Response response = given().contentType("application/json").body(body).when().post(BASE_URL + "/config");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void givenValidConfigRequest_whenUpdatingConfig_shouldSetNewOrganisationName() {
        ConfigurationRequest configRequest = new ConfigurationRequestFixture().withOrganizationName(AN_ORGANISATION_NAME).withCubeSize(A_CUBE_SIZE)
            .withGroupReservationMethod(ReservationType.FullCubes.toString()).build();

        given().contentType("application/json").body(configRequest).when().post(BASE_URL + "/config");

        Response response = when().get(BASE_URL + "/layout");
        LayoutResponse actualBody = response.getBody().as(LayoutResponse.class);
        assertEquals(AN_ORGANISATION_NAME, actualBody.name());
    }

    @Test
    public void givenValidConfigRequest_whenUpdatingConfig_shouldSetNewCubeSize() {
        ConfigurationRequest configRequest = new ConfigurationRequestFixture().withOrganizationName(AN_ORGANISATION_NAME).withCubeSize(A_CUBE_SIZE)
            .withGroupReservationMethod(ReservationType.FullCubes.toString()).build();

        given().contentType("application/json").body(configRequest).when().post(BASE_URL + "/config");

        Response response = when().get(BASE_URL + "/layout");
        LayoutResponse actualBody = response.getBody().as(LayoutResponse.class);
        assertEquals(A_CUBE_SIZE, actualBody.cubes().get(0).seats().size());
    }

    @Test
    public void givenValidConfigRequest_whenUpdatingConfig_shouldResetCafeWithNewStrategy() {
        ConfigurationRequest configRequest = new ConfigurationRequestFixture().withOrganizationName(AN_ORGANISATION_NAME).withCubeSize(A_CUBE_SIZE)
            .withGroupReservationMethod(ReservationType.FullCubes.toString()).build();

        given().contentType("application/json").body(configRequest).when().post(BASE_URL + "/config");

        postReservationWithGroupName(A_VALID_GROUP_NAME);
        Response response = when().get(BASE_URL + "/layout");
        LayoutResponse actualBody = response.getBody().as(LayoutResponse.class);
        assertEquals(SeatStatus.Reserved, actualBody.cubes().get(0).seats().get(0).status());
        assertEquals(SeatStatus.Reserved, actualBody.cubes().get(0).seats().get(1).status());
        assertEquals(SeatStatus.Reserved, actualBody.cubes().get(0).seats().get(2).status());
        assertEquals(SeatStatus.Reserved, actualBody.cubes().get(0).seats().get(3).status());
        assertEquals(SeatStatus.Reserved, actualBody.cubes().get(0).seats().get(4).status());
    }

    @Test
    public void whenAddingMenuItem_shouldReturn200() {
        CoffeeRequest coffeeRequest = new CoffeeRequest();
        coffeeRequest.cost = A_COST;
        coffeeRequest.name = A_COFFEE_NAME;
        coffeeRequest.ingredients = SOME_INGREDIENTS;

        Response response = given().contentType("application/json").body(coffeeRequest).when().post(BASE_URL + "/menu");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenAddingMenuItem_shouldBeAvailableInMenu() {
        fullInventory();
        CoffeeRequest coffeeRequest = new CoffeeRequest();
        coffeeRequest.cost = A_COST;
        coffeeRequest.name = A_COFFEE_NAME;
        coffeeRequest.ingredients = SOME_INGREDIENTS;

        given().contentType("application/json").body(coffeeRequest).when().post(BASE_URL + "/menu");

        postCheckInWithCustomerId(A_VALID_ID);
        OrderRequest orderRequest = new OrderRequestFixture().withOrders(List.of(A_COFFEE_NAME)).build();
        Response response = given().contentType("application/json").body(orderRequest).put(BASE_URL + "/customers/" + A_VALID_ID + "/orders");
        assertEquals(200, response.getStatusCode());
    }

    private void postCheckInWithCustomerId(String customerId) {
        CheckInRequest checkInRequest = new CheckInRequestFixture().withCustomerId(customerId).build();
        given().contentType("application/json").body(checkInRequest).when().post(BASE_URL + "/check-in");
    }

    private void postReservationWithGroupName(String groupName) {
        ReservationRequest reservationRequest = (new ReservationRequestFixture()).withGroupName(groupName).withGroupSize(2).build();
        given().contentType("application/json").body(reservationRequest).when().post(BASE_URL + "/reservations");
    }

    private void fullInventory() {
        InventoryRequest inventoryRequest =
            new InventoryRequestFixture().withChocolate(A_VALID_STOCK).withEspresso(A_VALID_STOCK).withMilk(A_VALID_STOCK).withWater(A_VALID_STOCK).build();
        given().contentType("application/json").body(inventoryRequest).put(BASE_URL + "/inventory");
    }
}

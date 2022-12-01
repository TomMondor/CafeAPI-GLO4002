package ca.ulaval.glo4002.cafe.large;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.response.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.layout.SeatStatus;
import ca.ulaval.glo4002.cafe.api.layout.response.CubeResponse;
import ca.ulaval.glo4002.cafe.api.layout.response.LayoutResponse;
import ca.ulaval.glo4002.cafe.api.request.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.request.CheckOutRequest;
import ca.ulaval.glo4002.cafe.api.request.ConfigurationRequest;
import ca.ulaval.glo4002.cafe.api.request.InventoryRequest;
import ca.ulaval.glo4002.cafe.api.reservation.request.ReservationRequest;
import ca.ulaval.glo4002.cafe.api.response.InventoryResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.fixture.CubeResponseFixture;
import ca.ulaval.glo4002.cafe.fixture.SeatFixture;
import ca.ulaval.glo4002.cafe.fixture.request.CheckInRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.ConfigurationRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.InventoryRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.ReservationRequestFixture;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CafeResourceEnd2EndTest {
    private static final String BASE_URL = "http://localhost:8181";
    private static final String AN_ORGANISATION_NAME = "MyLittleCafe";
    private static final int A_CUBE_SIZE = 5;
    private static final String A_VALID_ID = "test_ID";
    private static final String A_VALID_GROUP_NAME = "test_group";
    private static final CheckInRequest A_VALID_CHECK_IN_REQUEST = new CheckInRequestFixture().withCustomerId(A_VALID_ID).build();
    private static final int A_VALID_STOCK = 100;

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
    public void whenGettingLayout_shouldReturn200() {
        Response response = when().get(BASE_URL + "/layout");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenGettingLayout_shouldReturnCafeLayoutBody() {
        List<CubeResponse> expectedCubes = createListOfExpectedValidCube();

        Response response = when().get(BASE_URL + "/layout");
        LayoutResponse actualBody = response.getBody().as(LayoutResponse.class);

        assertEquals("Les 4-Fées", actualBody.name());
        assertEquals(expectedCubes, actualBody.cubes());
    }

    @Test
    public void whenClosing_shouldReturn200() {
        Response response = when().post(BASE_URL + "/close");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void givenAGroup_whenCheckingIn_shouldReturn201() {
        String groupName = "a_group";
        postReservationWithGroupName(groupName);
        CheckInRequest checkInRequest = new CheckInRequestFixture().withGroupName(groupName).build();

        Response response = given().contentType("application/json").body(checkInRequest).when().post(BASE_URL + "/check-in");

        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void givenNoGroup_whenCheckingIn_shouldReturn201() {
        CheckInRequest checkInRequest = new CheckInRequestFixture().build();

        Response response = given().contentType("application/json").body(checkInRequest).when().post(BASE_URL + "/check-in");

        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void whenCheckingIn_shouldReturnWithLocationHeader() {
        CheckInRequest checkInRequest = new CheckInRequestFixture().withCustomerId(A_VALID_ID).build();

        Response response = given().contentType("application/json").body(checkInRequest).when().post(BASE_URL + "/check-in");

        assertEquals(BASE_URL + "/customers/" + A_VALID_ID, response.header("Location"));
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
    public void whenCheckingOut_shouldReturn201() {
        postCheckInWithCheckInRequest(A_VALID_CHECK_IN_REQUEST);
        CheckOutRequest checkOutRequest = new CheckOutRequest();
        checkOutRequest.customer_id = A_VALID_ID;

        Response response = given().contentType("application/json").body(checkOutRequest).when().post(BASE_URL + "/checkout");

        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void whenCheckingOut_shouldReturnBillLocationHeader() {
        postCheckInWithCheckInRequest(A_VALID_CHECK_IN_REQUEST);
        CheckOutRequest checkOutRequest = new CheckOutRequest();
        checkOutRequest.customer_id = A_VALID_ID;

        Response response = given().contentType("application/json").body(checkOutRequest).when().post(BASE_URL + "/checkout");

        assertEquals(BASE_URL + "/customers/" + A_VALID_ID + "/bill", response.getHeader("Location"));
    }

    @Test
    public void whenGettingInventory_shouldReturn200() {
        Response response = when().get(BASE_URL + "/inventory");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenGettingInventory_shouldReturnCafeInventoryBody() {
        Response response = when().get(BASE_URL + "/inventory");
        InventoryResponse actualBody = response.getBody().as(InventoryResponse.class);

        assertEquals(0, actualBody.Chocolate());
        assertEquals(0, actualBody.Espresso());
        assertEquals(0, actualBody.Milk());
        assertEquals(0, actualBody.Water());
    }

    @Test
    public void whenPuttingInventory_shouldReturn200() {
        InventoryRequest inventoryRequest =
            new InventoryRequestFixture().withChocolate(A_VALID_STOCK).withEspresso(A_VALID_STOCK).withMilk(A_VALID_STOCK).withWater(A_VALID_STOCK).build();

        Response response = given().contentType("application/json").body(inventoryRequest).when().put(BASE_URL + "/inventory");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenPuttingInventory_shouldAddToInventory() {
        InventoryRequest inventoryRequest =
            new InventoryRequestFixture().withChocolate(A_VALID_STOCK).withEspresso(A_VALID_STOCK).withMilk(A_VALID_STOCK).withWater(A_VALID_STOCK).build();

        given().contentType("application/json").body(inventoryRequest).when().put(BASE_URL + "/inventory");

        Response inventoryResponse = given().contentType("application/json").body(inventoryRequest).when().get(BASE_URL + "/inventory");
        InventoryResponse body = inventoryResponse.getBody().as(InventoryResponse.class);
        assertEquals(A_VALID_STOCK, body.Chocolate());
        assertEquals(A_VALID_STOCK, body.Espresso());
        assertEquals(A_VALID_STOCK, body.Milk());
        assertEquals(A_VALID_STOCK, body.Water());
    }

    private void postCheckInWithCheckInRequest(CheckInRequest checkInRequest) {
        given().contentType("application/json").body(checkInRequest).when().post(BASE_URL + "/check-in");
    }

    private void postReservationWithGroupName(String groupName) {
        ReservationRequest reservationRequest = (new ReservationRequestFixture()).withGroupName(groupName).withGroupSize(2).build();
        given().contentType("application/json").body(reservationRequest).when().post(BASE_URL + "/reservations");
    }

    private List<CubeResponse> createListOfExpectedValidCube() {
        CubeResponse firstCubeResponse = new CubeResponseFixture().withCubeName("Bloom").build();

        List<Seat> seatsStartingAtFive = new SeatFixture().withSeatNumber(new SeatNumber(5)).buildMultipleSeats(4);
        CubeResponse secondCubeResponse = new CubeResponseFixture().withCubeName("Merryweather").withSeatList(seatsStartingAtFive).build();

        List<Seat> seatsStartingAtNine = new SeatFixture().withSeatNumber(new SeatNumber(9)).buildMultipleSeats(4);
        CubeResponse thirdCubeResponse = new CubeResponseFixture().withCubeName("Tinker Bell").withSeatList(seatsStartingAtNine).build();

        List<Seat> seatsStartingAtThirteen = new SeatFixture().withSeatNumber(new SeatNumber(13)).buildMultipleSeats(4);
        CubeResponse forthCubeResponse = new CubeResponseFixture().withCubeName("Wanda").withSeatList(seatsStartingAtThirteen).build();

        return List.of(firstCubeResponse, secondCubeResponse, thirdCubeResponse, forthCubeResponse);
    }
}

package ca.ulaval.glo4002.cafe.large;

import java.util.List;

import io.restassured.response.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.request.OrderRequest;
import ca.ulaval.glo4002.cafe.api.customer.response.BillResponse;
import ca.ulaval.glo4002.cafe.api.customer.response.CustomerResponse;
import ca.ulaval.glo4002.cafe.api.customer.response.OrdersResponse;
import ca.ulaval.glo4002.cafe.api.request.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.request.CheckOutRequest;
import ca.ulaval.glo4002.cafe.api.request.InventoryRequest;
import ca.ulaval.glo4002.cafe.api.reservation.request.ReservationRequest;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.fixture.request.CheckInRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.InventoryRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.ReservationRequestFixture;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerResourceEnd2EndTest {
    private static final String BASE_URL = "http://localhost:8181";
    private static final String CUSTOMER_ID = "abd4bced-4fce-44a0-aa41-c41c5777e679";
    private static final String CUSTOMER_NAME = "Keanu Reeves";
    private static final String GROUP_NAME = "Rise Against the Machine";
    private static final int FIRST_SEAT_NUMBER = 1;
    private static final Order ORDERS = new Order(
        List.of(new Coffee(CoffeeType.Espresso), new Coffee(CoffeeType.Espresso), new Coffee(CoffeeType.Latte), new Coffee(CoffeeType.Americano)));
    private static final String A_VALID_COFFEE = "Latte";
    private static final String ANOTHER_VALID_COFFEE = "Americano";
    private static final Amount SUBTOTAL = new Amount(11.1f);
    private static final Amount TAXES = new Amount(0f);
    private static final Amount TIP_RATE = new Amount(0.0f);
    private static final Amount TOTAL = new Amount(11.1f);
    private static final int ENOUGH_STOCK = 10000;

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
    public void whenGettingExistingCustomer_shouldReturn200() {
        CheckInRequest checkInRequest = new CheckInRequestFixture().withCustomerId(CUSTOMER_ID).build();
        given().contentType("application/json").body(checkInRequest).post(BASE_URL + "/check-in");

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void givenCustomer_whenGettingExistingCustomer_shouldReturnValidCustomerResponse() {
        CheckInRequest aCheckInRequestWithoutGroupName = new CheckInRequestFixture().withCustomerId(CUSTOMER_ID).withCustomerName(CUSTOMER_NAME).build();
        given().contentType("application/json").body(aCheckInRequestWithoutGroupName).post(BASE_URL + "/check-in");

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID);
        CustomerResponse actualBody = response.getBody().as(CustomerResponse.class);

        assertEquals(CUSTOMER_NAME, actualBody.name());
        assertEquals(FIRST_SEAT_NUMBER, actualBody.seat_number());
    }

    @Test
    public void givenCustomerWithGroupName_whenGettingExistingCustomer_shouldReturnValidCustomerResponseWithGroupName() {
        postReservationWithGroupNameAndSize(GROUP_NAME, 2);
        CheckInRequest checkInRequestWithGroupName =
            new CheckInRequestFixture().withCustomerId(CUSTOMER_ID).withGroupName(GROUP_NAME).withCustomerName(CUSTOMER_NAME).build();
        postCheckInCustomer(checkInRequestWithGroupName);

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID);
        CustomerResponse actualBody = response.getBody().as(CustomerResponse.class);

        assertEquals(CUSTOMER_NAME, actualBody.name());
        assertEquals(FIRST_SEAT_NUMBER, actualBody.seat_number());
        assertEquals(GROUP_NAME, actualBody.group_name());
    }

    @Test
    public void whenPuttingOrder_shouldReturn200() {
        fullInventory();
        givenCheckInCustomer();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.orders = List.of(A_VALID_COFFEE);

        Response response = given().contentType("application/json").body(orderRequest).put(BASE_URL + "/customers/" + CUSTOMER_ID + "/orders");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenGettingOrders_shouldReturn200() {
        givenCheckInCustomer();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.orders = List.of(A_VALID_COFFEE);
        given().contentType("application/json").body(orderRequest).put(BASE_URL + "/customers/" + CUSTOMER_ID + "/orders");

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID + "/orders");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void givenCustomer_whenGettingOrders_shouldReturnValidOrdersResponse() {
        fullInventory();
        givenCheckInCustomer();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.orders = List.of(A_VALID_COFFEE, ANOTHER_VALID_COFFEE);
        given().contentType("application/json").body(orderRequest).put(BASE_URL + "/customers/" + CUSTOMER_ID + "/orders");

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID + "/orders");
        OrdersResponse actualBody = response.getBody().as(OrdersResponse.class);

        assertEquals(List.of(A_VALID_COFFEE, ANOTHER_VALID_COFFEE), actualBody.orders());
    }

    @Test
    public void givenCustomer_whenGettingCustomerBill_shouldReturn200() {
        givenCheckedOutCustomer();

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID + "/bill");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void givenCheckedOutCustomer_whenGettingCustomerBill_shouldReturnValidBillResponse() {
        fullInventory();
        givenCheckedOutCustomer();

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID + "/bill");
        BillResponse actualBody = response.getBody().as(BillResponse.class);

        assertEquals(ORDERS.items().stream().map(coffee -> coffee.coffeeType().toString()).toList(), actualBody.orders());
        assertEquals(SUBTOTAL.getRoundedValue(), actualBody.subtotal());
        assertEquals(TAXES.getRoundedValue(), actualBody.taxes());
        assertEquals(TIP_RATE.getRoundedValue(), actualBody.tip());
        assertEquals(TOTAL.getRoundedValue(), actualBody.total());
    }

    @Test
    public void givenExistingCustomer_whenGettingOrders_shouldReturn200() {
        postCheckInCustomer(CUSTOMER_ID);

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID + "/orders");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void givenExistingCustomerWithoutOrders_whenGettingOrders_shouldReturnEmptyOrdersResponse() {
        postCheckInCustomer(CUSTOMER_ID);

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID + "/orders");
        OrdersResponse actualBody = response.getBody().as(OrdersResponse.class);

        assertTrue(actualBody.orders().isEmpty());
    }

    @Test
    public void givenExistingCustomerWithOrders_whenGettingOrders_shouldReturnValidOrdersResponse() {
        fullInventory();
        postCheckInCustomer(CUSTOMER_ID);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.orders = List.of("Latte", "Macchiato", "Flat White");
        putOrders(CUSTOMER_ID, orderRequest);

        Response response = when().get(BASE_URL + "/customers/" + CUSTOMER_ID + "/orders");
        OrdersResponse actualBody = response.getBody().as(OrdersResponse.class);

        assertEquals("Latte", actualBody.orders().get(0));
        assertEquals("Macchiato", actualBody.orders().get(1));
        assertEquals("Flat White", actualBody.orders().get(2));
    }

    private void givenCheckInCustomer() {
        CheckInRequest checkInRequest = new CheckInRequestFixture().withCustomerId(CUSTOMER_ID).build();
        given().contentType("application/json").body(checkInRequest).post(BASE_URL + "/check-in");
    }

    private void givenCheckedOutCustomer() {
        givenCheckInCustomer();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.orders = ORDERS.items().stream().map(coffee -> coffee.coffeeType().toString()).toList();
        given().contentType("application/json").body(orderRequest).put(BASE_URL + "/customers/" + CUSTOMER_ID + "/orders");
        CheckOutRequest checkOutRequest = new CheckOutRequest();
        checkOutRequest.customer_id = CUSTOMER_ID;
        given().contentType("application/json").body(checkOutRequest).post(BASE_URL + "/checkout");
    }

    private void postCheckInCustomer(CheckInRequest checkInRequest) {
        given().contentType("application/json").body(checkInRequest).post(BASE_URL + "/check-in");
    }

    private void postCheckInCustomer(String customerId) {
        CheckInRequest checkInRequest = new CheckInRequestFixture().withCustomerId(customerId).build();
        given().contentType("application/json").body(checkInRequest).post(BASE_URL + "/check-in");
    }

    private void postReservationWithGroupNameAndSize(String groupName, int groupSize) {
        ReservationRequest reservationRequest = new ReservationRequestFixture().withGroupName(groupName).withGroupSize(groupSize).build();

        given().contentType("application/json").body(reservationRequest).post(BASE_URL + "/reservations");
    }

    private void putOrders(String customerId, OrderRequest orderRequest) {
        given().contentType("application/json").body(orderRequest).put(BASE_URL + "/customers/" + customerId + "/orders");
    }

    private void fullInventory() {
        InventoryRequest inventoryRequest =
            new InventoryRequestFixture().withChocolate(ENOUGH_STOCK).withEspresso(ENOUGH_STOCK).withMilk(ENOUGH_STOCK).withWater(ENOUGH_STOCK).build();
        given().contentType("application/json").body(inventoryRequest).put(BASE_URL + "/inventory");
    }
}

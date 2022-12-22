package ca.ulaval.glo4002.cafe.large;

import io.restassured.response.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.inventory.request.InventoryRequest;
import ca.ulaval.glo4002.cafe.api.inventory.response.InventoryResponse;
import ca.ulaval.glo4002.cafe.fixture.request.InventoryRequestFixture;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryResourceEnd2EndTest {
    private static final String BASE_URL = "http://localhost:8181";
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
}

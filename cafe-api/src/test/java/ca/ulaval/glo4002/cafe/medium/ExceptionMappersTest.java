package ca.ulaval.glo4002.cafe.medium;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.restassured.response.Response;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.exception.response.ErrorResponse;
import ca.ulaval.glo4002.cafe.fixture.FakeExceptionThrowerContextFixture;
import ca.ulaval.glo4002.config.ApplicationContext;
import ca.ulaval.glo4002.config.CafeServer;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionMappersTest {
    private static final Logger ORG_GLASSFISH_JERSEY_LOGGER = Logger.getLogger("org.glassfish.jersey");
    private static final Logger ORG_HIBERNATE_LOGGER = Logger.getLogger("org.hibernate");
    private static String baseUrl;

    @BeforeAll
    public static void startServer() {
        ORG_GLASSFISH_JERSEY_LOGGER.setLevel(Level.SEVERE);
        ORG_HIBERNATE_LOGGER.setLevel(Level.SEVERE);

        ApplicationContext context = new FakeExceptionThrowerContextFixture();
        CafeServer cafeServer = new CafeServer(context);
        baseUrl = String.format("http://localhost:%s", context.getPort());
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.submit(cafeServer);
    }

    @Test
    public void whenThrowingCustomerAlreadyVisitedException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("DUPLICATE_CUSTOMER_ID",
            "The customer cannot visit the café multiple times in the same day.");

        Response response = when().get(baseUrl + "/CustomerAlreadyVisitedException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingInsufficientSeatsException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("INSUFFICIENT_SEATS",
            "There are currently no available seats. Please come back later.");

        Response response = when().get(baseUrl + "/InsufficientSeatsException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingCustomerNotFoundException_shouldGiveNotFound() {
        ErrorResponse expectedResponse = new ErrorResponse("INVALID_CUSTOMER_ID",
            "The customer does not exist.");

        Response response = when().get(baseUrl + "/CustomerNotFoundException");

        assertEquals(404, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingInvalidGroupTipRateException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("INVALID_GROUP_TIP_RATE",
            "The group tip rate must be set to a value between 0 to 100.");

        Response response = when().get(baseUrl + "/InvalidGroupTipRateException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingInsufficientIngredientException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("INSUFFICIENT_INGREDIENTS",
            "We lack the necessary number of ingredients to fulfill your order.");

        Response response = when().get(baseUrl + "/InsufficientIngredientException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingCustomerNoBillException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("NO_BILL",
            "The customer needs to do a checkout before receiving his bill.");

        Response response = when().get(baseUrl + "/CustomerNoBillException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingInvalidConfigurationCountryException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("INVALID_COUNTRY",
            "The specified country is invalid.");

        Response response = when().get(baseUrl + "/InvalidConfigurationCountryException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingInvalidGroupSizeException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("INVALID_GROUP_SIZE",
            "Groups must reserve at least two seats.");

        Response response = when().get(baseUrl + "/InvalidGroupSizeException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingDuplicateGroupNameException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("DUPLICATE_GROUP_NAME",
            "The specified group already made a reservation today.");

        Response response = when().get(baseUrl + "/DuplicateGroupNameException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingNoReservationsFoundException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("NO_RESERVATIONS_FOUND",
            "No reservations were made today for that group.");

        Response response = when().get(baseUrl + "/NoReservationsFoundException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingNoGroupSeatsException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("NO_GROUP_SEATS",
            "There are no more seats reserved for that group.");

        Response response = when().get(baseUrl + "/NoGroupSeatsException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingInvalidGroupReservationMethodException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("INVALID_GROUP_RESERVATION_METHOD",
            "The group reservation method is not supported.");

        Response response = when().get(baseUrl + "/InvalidGroupReservationMethodException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingInvalidMenuOrderException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("INVALID_MENU_ORDER",
            "An item ordered is not on the menu.");

        Response response = when().get(baseUrl + "/InvalidMenuOrderException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingConstraintViolationException_shouldGiveBadRequest() {
        ErrorResponse expectedResponse = new ErrorResponse("INVALID_REQUEST",
            "The customer_id may not be null.");

        Response response = when().get(baseUrl + "/ConstraintViolationException");

        assertEquals(400, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody().as(ErrorResponse.class));
    }

    @Test
    public void whenThrowingUnexpectedException_shouldGiveBadRequest() {
        Response response = when().get(baseUrl + "/UnexpectedException");

        assertEquals(400, response.getStatusCode());
    }
}

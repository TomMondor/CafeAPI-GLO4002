package ca.ulaval.glo4002.cafe.large;

import java.util.Arrays;
import java.util.List;

import io.restassured.response.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.reservation.request.ReservationRequest;
import ca.ulaval.glo4002.cafe.api.reservation.response.ReservationResponse;
import ca.ulaval.glo4002.cafe.fixture.request.ReservationRequestFixture;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationResourceEnd2EndTest {
    private static final String A_GROUP_NAME = "My group";
    private static final String ANOTHER_GROUP_NAME = "My other group";
    private static final int A_GROUP_SIZE = 3;
    private static final int ANOTHER_GROUP_SIZE = 2;
    private static final String BASE_URL = "http://localhost:8181";

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
    public void givenGroupNameAndSize_whenMakingReservation_shouldReturn200() {
        ReservationRequest reservationRequest = new ReservationRequestFixture().build();

        Response response = given().contentType("application/json").body(reservationRequest).when().post(BASE_URL + "/reservations");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenGettingReservations_shouldReturn200() {
        Response response = given().when().get(BASE_URL + "/reservations");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void givenNoReservations_whenGettingReservations_shouldReturnEmptyReservations() {
        Response response = given().when().get(BASE_URL + "/reservations");
        List<ReservationResponse> expectedResponse = List.of();

        List<ReservationResponse> groupsResponse = Arrays.asList(response.getBody().as(ReservationResponse[].class));

        assertEquals(expectedResponse, groupsResponse);
    }

    @Test
    public void givenReservations_whenGettingReservations_shouldReturnReservations() {
        postReservationWithGroupNameAndSize(A_GROUP_NAME, A_GROUP_SIZE);
        postReservationWithGroupNameAndSize(ANOTHER_GROUP_NAME, ANOTHER_GROUP_SIZE);
        List<ReservationResponse> expectedResponse = List.of(
            new ReservationResponse(A_GROUP_NAME, A_GROUP_SIZE),
            new ReservationResponse(ANOTHER_GROUP_NAME, ANOTHER_GROUP_SIZE)
        );

        Response response = get(BASE_URL + "/reservations");
        List<ReservationResponse> groupsResponse = Arrays.asList(response.getBody().as(ReservationResponse[].class));

        assertEquals(expectedResponse, groupsResponse);
    }

    private void postReservationWithGroupNameAndSize(String groupName, int groupSize) {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
            .withGroupName(groupName)
            .withGroupSize(groupSize)
            .build();

        given().contentType("application/json")
            .body(reservationRequest)
            .post(BASE_URL + "/reservations");
    }
}

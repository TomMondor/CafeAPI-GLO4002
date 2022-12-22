package ca.ulaval.glo4002.cafe.large;

import java.util.List;

import io.restassured.response.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.layout.response.CubeResponse;
import ca.ulaval.glo4002.cafe.api.layout.response.LayoutResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.fixture.CubeResponseFixture;
import ca.ulaval.glo4002.cafe.fixture.SeatFixture;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LayoutResourceEnd2EndTest {
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

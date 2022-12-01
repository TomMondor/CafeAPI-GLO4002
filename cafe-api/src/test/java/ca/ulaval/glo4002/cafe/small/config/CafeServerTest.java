package ca.ulaval.glo4002.cafe.small.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.fixture.FakeHeartBeatContextFixture;
import ca.ulaval.glo4002.config.CafeServer;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CafeServerTest {
    private final FakeHeartBeatContextFixture fakeHeartBeatContextFixture = new FakeHeartBeatContextFixture();
    private final String baseUrl = String.format("http://localhost:%s", fakeHeartBeatContextFixture.getPort());
    private final CafeServer cafeServer = new CafeServer(fakeHeartBeatContextFixture);

    @Test
    public void givenApplicationConfig_whenRunningCafeServer_shouldRun() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.submit(cafeServer);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // The thread has been interrupted
        }
        Response response = when().get(baseUrl);
        assertEquals(200, response.getStatusCode());
    }
}

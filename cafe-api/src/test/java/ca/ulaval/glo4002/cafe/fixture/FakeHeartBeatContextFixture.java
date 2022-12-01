package ca.ulaval.glo4002.cafe.fixture;

import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.config.ApplicationContext;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

public class FakeHeartBeatContextFixture implements ApplicationContext {

    public int getPort() {
        return 8182;
    }

    public ResourceConfig initializeResourceConfig() {
        return new ResourceConfig().register(new FakeHeartBeatResource());
    }

    @Path("/")
    public static class FakeHeartBeatResource {

        @GET
        public Response getHeartbeat() {
            return Response.ok().build();
        }
    }
}

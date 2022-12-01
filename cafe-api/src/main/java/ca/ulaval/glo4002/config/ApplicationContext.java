package ca.ulaval.glo4002.config;

import org.glassfish.jersey.server.ResourceConfig;

public interface ApplicationContext {
    ResourceConfig initializeResourceConfig();

    int getPort();
}

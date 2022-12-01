package ca.ulaval.glo4002.cafe.large;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.config.ApplicationContext;
import ca.ulaval.glo4002.config.ProductionApplicationContext;

public class TestServer {
    private static final Logger ORG_GLASSFISH_JERSEY_LOGGER = Logger.getLogger("org.glassfish.jersey");
    private static final Logger ORG_HIBERNATE_LOGGER = Logger.getLogger("org.hibernate");
    private final Server server;

    public TestServer() {
        ORG_GLASSFISH_JERSEY_LOGGER.setLevel(Level.SEVERE);
        ORG_HIBERNATE_LOGGER.setLevel(Level.SEVERE);

        ApplicationContext applicationContext = new ProductionApplicationContext();

        server = new Server(applicationContext.getPort());
        ServletContextHandler contextHandler = new ServletContextHandler(server, "");
        ResourceConfig resourceConfig = applicationContext.initializeResourceConfig();
        ServletContainer container = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}

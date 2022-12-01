package ca.ulaval.glo4002.config;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class CafeServer implements Runnable {
    private final ApplicationContext applicationContext;

    public CafeServer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void run() {
        Server server = initializeServer();
        start(server);
    }

    private void start(Server server) {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (server.isRunning()) {
                server.destroy();
            }
        }
    }

    private Server initializeServer() {
        Server server = new Server(this.applicationContext.getPort());

        ServletContextHandler contextHandler = new ServletContextHandler(server, "");
        ResourceConfig resourceConfig = applicationContext.initializeResourceConfig();
        ServletContainer container = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");
        return server;
    }
}

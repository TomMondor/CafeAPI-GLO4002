package ca.ulaval.glo4002;

import ca.ulaval.glo4002.config.ApplicationContext;
import ca.ulaval.glo4002.config.CafeServer;
import ca.ulaval.glo4002.config.ProductionApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ProductionApplicationContext();

        Runnable server = new CafeServer(applicationContext);
        server.run();
    }
}

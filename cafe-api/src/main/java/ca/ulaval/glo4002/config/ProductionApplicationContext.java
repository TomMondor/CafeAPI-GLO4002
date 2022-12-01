package ca.ulaval.glo4002.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import ca.ulaval.glo4002.cafe.api.CafeResource;
import ca.ulaval.glo4002.cafe.api.customer.CustomerResource;
import ca.ulaval.glo4002.cafe.api.exception.mapper.CafeExceptionMapper;
import ca.ulaval.glo4002.cafe.api.exception.mapper.CatchallExceptionMapper;
import ca.ulaval.glo4002.cafe.api.exception.mapper.ConstraintViolationExceptionMapper;
import ca.ulaval.glo4002.cafe.api.reservation.ReservationResource;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeService;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.reservation.ReservationService;

public class ProductionApplicationContext implements ApplicationContext {
    private static final int PORT = 8181;

    public ResourceConfig initializeResourceConfig() {
        CafeRepository cafeRepository = new InMemoryCafeRepository();

        ReservationService groupService = new ReservationService(cafeRepository, new ReservationFactory());
        CustomerService customersService = new CustomerService(cafeRepository, new CustomerFactory());
        CafeService cafeService = new CafeService(cafeRepository, new CafeFactory());

        cafeService.initializeCafe();

        return new ResourceConfig().packages("ca.ulaval.glo4002.cafe").property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
            .register(new CafeResource(cafeService, customersService)).register(new CustomerResource(customersService))
            .register(new ReservationResource(groupService)).register(new CafeExceptionMapper()).register(new CatchallExceptionMapper())
            .register(new ConstraintViolationExceptionMapper());
    }

    public int getPort() {
        return PORT;
    }
}

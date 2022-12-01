package ca.ulaval.glo4002.cafe.fixture;

import java.util.Set;

import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;

import ca.ulaval.glo4002.cafe.api.exception.mapper.CafeExceptionMapper;
import ca.ulaval.glo4002.cafe.api.exception.mapper.CatchallExceptionMapper;
import ca.ulaval.glo4002.cafe.api.exception.mapper.ConstraintViolationExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerAlreadyVisitedException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNoBillException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InsufficientIngredientsException;
import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupSizeException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupTipRateException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;
import ca.ulaval.glo4002.cafe.domain.exception.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.exception.NoReservationsFoundException;
import ca.ulaval.glo4002.config.ApplicationContext;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class FakeExceptionThrowerContextFixture implements ApplicationContext {

    public int getPort() {
        return 8183;
    }

    public ResourceConfig initializeResourceConfig() {
        return new ResourceConfig()
            .register(new FakeExceptionThrowerResource())
            .register(new CafeExceptionMapper())
            .register(new CatchallExceptionMapper())
            .register(new ConstraintViolationExceptionMapper());
    }

    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public static class FakeExceptionThrowerResource {

        @GET
        @Path("/CustomerAlreadyVisitedException")
        public Response throwCustomerAlreadyVisitedException() {
            throw new CustomerAlreadyVisitedException();
        }

        @GET
        @Path("/InsufficientSeatsException")
        public Response throwInsufficientSeatsException() {
            throw new InsufficientSeatsException();
        }

        @GET
        @Path("/CustomerNotFoundException")
        public Response throwCustomerNotFoundException() {
            throw new CustomerNotFoundException();
        }

        @GET
        @Path("/InvalidGroupTipRateException")
        public Response throwInvalidGroupTipRateException() {
            throw new InvalidGroupTipRateException();
        }

        @GET
        @Path("/InsufficientIngredientException")
        public Response throwInsufficientIngredientException() {
            throw new InsufficientIngredientsException();
        }

        @GET
        @Path("/CustomerNoBillException")
        public Response throwCustomerNoBillException() {
            throw new CustomerNoBillException();
        }

        @GET
        @Path("/InvalidConfigurationCountryException")
        public Response throwInvalidConfigurationCountryException() {
            throw new InvalidConfigurationCountryException();
        }

        @GET
        @Path("/InvalidGroupSizeException")
        public Response throwInvalidGroupSizeException() {
            throw new InvalidGroupSizeException();
        }

        @GET
        @Path("/DuplicateGroupNameException")
        public Response throwDuplicateGroupNameException() {
            throw new DuplicateGroupNameException();
        }

        @GET
        @Path("/NoReservationsFoundException")
        public Response throwNoReservationsFoundException() {
            throw new NoReservationsFoundException();
        }

        @GET
        @Path("/NoGroupSeatsException")
        public Response throwNoGroupSeatsException() {
            throw new NoGroupSeatsException();
        }

        @GET
        @Path("/InvalidGroupReservationMethodException")
        public Response throwInvalidGroupReservationMethodException() {
            throw new InvalidGroupReservationMethodException();
        }

        @GET
        @Path("/InvalidMenuOrderException")
        public Response throwInvalidMenuOrderException() {
            throw new InvalidMenuOrderException();
        }

        @GET
        @Path("/ConstraintViolationException")
        public Response throwConstraintViolationException() {
            throw new ConstraintViolationException("", Set.of(
                ConstraintViolationImpl.forParameterValidation(null, null, null, "The customer_id may not be null.",
                    null, null, null, null, null, null, null, null)
            ));
        }

        @GET
        @Path("/UnexpectedException")
        public Response throwUnexpectedException() {
            throw new RuntimeException();
        }
    }
}

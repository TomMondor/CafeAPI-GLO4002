package ca.ulaval.glo4002.cafe.api.exception.mapper;

import java.util.WeakHashMap;

import ca.ulaval.glo4002.cafe.domain.exception.CafeException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerAlreadyVisitedException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNoBillException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.exception.DuplicateCubeNameException;
import ca.ulaval.glo4002.cafe.domain.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.exception.IngredientTypeMismatchException;
import ca.ulaval.glo4002.cafe.domain.exception.InsufficientIngredientsException;
import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidCubeNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidCubeSizeException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidCustomerNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupSizeException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupTipRateException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidQuantityException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidSeatNumberException;
import ca.ulaval.glo4002.cafe.domain.exception.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.exception.NoReservationsFoundException;
import ca.ulaval.glo4002.cafe.domain.exception.SeatAlreadyOccupiedException;
import ca.ulaval.glo4002.cafe.domain.exception.SeatAlreadyReservedException;
import ca.ulaval.glo4002.cafe.service.exception.CafeNotFoundException;

import jakarta.ws.rs.core.Response;

public class ExceptionStatusMapper {
    private static WeakHashMap<Class<? extends CafeException>, Response.Status> exceptionMapper;

    public static Response.Status getResponseStatus(CafeException cafeException) {
        if (exceptionMapper == null) {
            createExceptionMapper();
        }
        return exceptionMapper.get(cafeException.getClass());
    }

    private static void createExceptionMapper() {
        exceptionMapper = new WeakHashMap<>();

        exceptionMapper.put(CafeNotFoundException.class, Response.Status.NOT_FOUND);
        exceptionMapper.put(CustomerNotFoundException.class, Response.Status.NOT_FOUND);

        exceptionMapper.put(InsufficientIngredientsException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidCubeSizeException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidGroupReservationMethodException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(CustomerAlreadyVisitedException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidCustomerIdException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidCustomerNameException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(DuplicateGroupNameException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidGroupNameException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidGroupSizeException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(NoGroupSeatsException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(NoReservationsFoundException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InsufficientSeatsException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidCubeNameException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidSeatNumberException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidMenuOrderException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidConfigurationCountryException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(CustomerNoBillException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidQuantityException.class, Response.Status.BAD_REQUEST);
        exceptionMapper.put(InvalidGroupTipRateException.class, Response.Status.BAD_REQUEST);

        exceptionMapper.put(DuplicateCubeNameException.class, Response.Status.INTERNAL_SERVER_ERROR);
        exceptionMapper.put(SeatAlreadyOccupiedException.class, Response.Status.INTERNAL_SERVER_ERROR);
        exceptionMapper.put(SeatAlreadyReservedException.class, Response.Status.INTERNAL_SERVER_ERROR);
        exceptionMapper.put(IngredientTypeMismatchException.class, Response.Status.INTERNAL_SERVER_ERROR);
    }
}

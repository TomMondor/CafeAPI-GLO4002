package ca.ulaval.glo4002.cafe.small.cafe.domain.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.FullCubesStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.NoLonersStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.ReservationStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationStrategyFactoryTest {

    private ReservationStrategyFactory reservationStrategyFactory;

    @BeforeEach
    public void createFactory() {
        reservationStrategyFactory = new ReservationStrategyFactory();
    }

    @Test
    public void givenDefaultType_whenCreateReservationStrategy_shouldReturnDefaultStrategy() {
        ReservationStrategy reservationStrategy = reservationStrategyFactory.createReservationStrategy(ReservationType.Default);

        assertEquals(DefaultStrategy.class, reservationStrategy.getClass());
    }

    @Test
    public void givenFullCubesType_whenCreateReservationStrategy_shouldReturnFullCubesStrategy() {
        ReservationStrategy reservationStrategy = reservationStrategyFactory.createReservationStrategy(ReservationType.FullCubes);

        assertEquals(FullCubesStrategy.class, reservationStrategy.getClass());
    }

    @Test
    public void givenNoLonersType_whenCreateReservationStrategy_shouldReturnNoLonersStrategy() {
        ReservationStrategy reservationStrategy = reservationStrategyFactory.createReservationStrategy(ReservationType.NoLoners);

        assertEquals(NoLonersStrategy.class, reservationStrategy.getClass());
    }
}

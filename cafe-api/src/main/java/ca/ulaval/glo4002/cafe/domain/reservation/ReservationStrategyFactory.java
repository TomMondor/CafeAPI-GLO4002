package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.reservation.strategies.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.FullCubesStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.NoLonersStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.ReservationStrategy;

public class ReservationStrategyFactory {
    public ReservationStrategy createReservationStrategy(ReservationType reservationType) {
        return switch (reservationType) {
            case Default -> new DefaultStrategy();
            case FullCubes -> new FullCubesStrategy();
            case NoLoners -> new NoLonersStrategy();
        };
    }
}

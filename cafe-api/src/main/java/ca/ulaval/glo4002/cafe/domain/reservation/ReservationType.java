package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupReservationMethodException;

public enum ReservationType {
    Default("Default"), FullCubes("Full Cubes"), NoLoners("No Loners");

    private final String reservationStrategy;

    ReservationType(String reservationType) {
        this.reservationStrategy = reservationType;
    }

    public static ReservationType fromString(String type) {
        if (ReservationType.contains(type)) {
            return ReservationType.valueOf(type.replace(" ", ""));
        }
        throw new InvalidGroupReservationMethodException();
    }

    private static boolean contains(String type) {
        for (ReservationType reservationType : ReservationType.values()) {
            if (reservationType.reservationStrategy.equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.reservationStrategy;
    }
}

package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidGroupReservationMethodException extends CafeException {

    public InvalidGroupReservationMethodException() {
        super("INVALID_GROUP_RESERVATION_METHOD", "The group reservation method is not supported.");
    }
}

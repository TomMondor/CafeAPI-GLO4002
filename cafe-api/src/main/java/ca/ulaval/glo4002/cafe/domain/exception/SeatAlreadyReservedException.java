package ca.ulaval.glo4002.cafe.domain.exception;

public class SeatAlreadyReservedException extends CafeException {
    public SeatAlreadyReservedException() {
        super("SEAT_ALREADY_RESERVED", "This seat is already reserved.");
    }
}

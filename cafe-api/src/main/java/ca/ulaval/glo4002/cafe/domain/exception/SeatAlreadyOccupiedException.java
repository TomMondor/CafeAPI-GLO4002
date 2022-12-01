package ca.ulaval.glo4002.cafe.domain.exception;

public class SeatAlreadyOccupiedException extends CafeException {
    public SeatAlreadyOccupiedException() {
        super("SEAT_ALREADY_OCCUPIED", "This seat is already occupied.");
    }
}

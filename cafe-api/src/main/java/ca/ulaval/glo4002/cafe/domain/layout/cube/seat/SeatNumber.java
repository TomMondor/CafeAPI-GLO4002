package ca.ulaval.glo4002.cafe.domain.layout.cube.seat;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidSeatNumberException;

public record SeatNumber(int value) {
    public SeatNumber {
        if (value < 1) {
            throw new InvalidSeatNumberException();
        }
    }
}

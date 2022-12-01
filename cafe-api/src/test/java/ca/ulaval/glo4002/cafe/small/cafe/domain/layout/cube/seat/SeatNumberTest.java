package ca.ulaval.glo4002.cafe.small.cafe.domain.layout.cube.seat;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidSeatNumberException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SeatNumberTest {
    private static final int INTEGER_LESS_THAN_ONE = 0;

    @Test
    public void givenIntegerLessThanOne_whenCreatingSeatNumber_shouldThrowInvalidSeatNumberException() {
        assertThrows(InvalidSeatNumberException.class, () -> new SeatNumber(INTEGER_LESS_THAN_ONE));
    }
}

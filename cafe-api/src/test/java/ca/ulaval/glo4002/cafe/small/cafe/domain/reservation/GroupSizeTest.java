package ca.ulaval.glo4002.cafe.small.cafe.domain.reservation;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupSizeException;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroupSizeTest {
    private static final int INTEGER_INFERIOR_TO_MIN_GROUP_SIZE = 1;

    @Test
    public void givenIntegerInferiorToMinimumGroupSize_whenCreateGroupSize_shouldThrowInvalidGroupSizeException() {
        assertThrows(InvalidGroupSizeException.class, () -> new GroupSize(INTEGER_INFERIOR_TO_MIN_GROUP_SIZE));
    }
}

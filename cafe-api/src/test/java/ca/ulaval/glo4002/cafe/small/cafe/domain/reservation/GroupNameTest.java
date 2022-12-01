package ca.ulaval.glo4002.cafe.small.cafe.domain.reservation;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupNameException;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroupNameTest {
    private static final String EMPTY_VALUE = "";

    @Test
    public void givenEmptyValue_whenCreatingGroupName_shouldThrowInvalidGroupNameException() {
        assertThrows(InvalidGroupNameException.class, () -> new GroupName(EMPTY_VALUE));
    }
}

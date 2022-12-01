package ca.ulaval.glo4002.cafe.small.cafe.domain.reservation;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationTypeTest {
    private static final String A_STRING_MATCHING_RESERVATION_TYPE = "Full Cubes";
    private static final String A_STRING_NOT_MATCHING_RESERVATION_TYPE = "Delete reservation";

    @Test
    public void givenStringMatchingReservationType_whenCreatingFromString_shouldCreateInstance() {
        ReservationType createdFullCubesType = ReservationType.fromString(A_STRING_MATCHING_RESERVATION_TYPE);

        assertEquals(ReservationType.FullCubes, createdFullCubesType);
    }

    @Test
    public void givenStringNotMatchingReservationType_whenCreatingFromString_shouldThrowInvalidGroupReservationMethodException() {
        assertThrows(InvalidGroupReservationMethodException.class,
            () -> ReservationType.fromString(A_STRING_NOT_MATCHING_RESERVATION_TYPE));
    }
}

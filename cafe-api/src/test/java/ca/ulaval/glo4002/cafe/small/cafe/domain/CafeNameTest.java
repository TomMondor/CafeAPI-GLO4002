package ca.ulaval.glo4002.cafe.small.cafe.domain;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.CafeName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CafeNameTest {
    private static final String CAFE_NAME = "Les 4-Fées";
    private static final String EMPTY_CAFE_NAME = "";

    @Test
    public void givenNonEmptyString_whenCreatingCafeName_shouldCreateCafeNameWithValue() {
        CafeName cafeName = new CafeName(CAFE_NAME);

        assertEquals(CAFE_NAME, cafeName.value());
    }

    @Test
    public void givenEmptyString_whenCreatingCafeName_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CafeName(EMPTY_CAFE_NAME));
    }
}

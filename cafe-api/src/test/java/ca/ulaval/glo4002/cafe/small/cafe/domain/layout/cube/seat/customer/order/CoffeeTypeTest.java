package ca.ulaval.glo4002.cafe.small.cafe.domain.layout.cube.seat.customer.order;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.CoffeeType;

import static org.junit.jupiter.api.Assertions.*;

public class CoffeeTypeTest {
    private static final String VALID_COFFEE = "Dark Roast";
    private static final String INVALID_COFFEE = "Creamy";

    @Test
    public void givenValidString_whenGettingCoffeeTypeFromString_shouldReturnCoffeeType() {
        CoffeeType coffeeType = CoffeeType.fromString(VALID_COFFEE);

        assertEquals(coffeeType, CoffeeType.DarkRoast);
    }

    @Test
    public void givenInvalidString_whenGettingCoffeeTypeFromString_shouldThrowInvalidMenuOrderException() {
        assertThrows(InvalidMenuOrderException.class, () -> CoffeeType.fromString(INVALID_COFFEE));
    }
}

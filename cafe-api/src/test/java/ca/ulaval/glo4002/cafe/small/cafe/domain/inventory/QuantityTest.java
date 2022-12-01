package ca.ulaval.glo4002.cafe.small.cafe.domain.inventory;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidQuantityException;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityTest {
    @Test
    public void givenNegativeQuantity_whenCreatingQuantity_shouldThrowInvalidQuantityException() {
        assertThrows(InvalidQuantityException.class, () -> new Quantity(-1));
    }

    @Test
    public void given2ValidQuantity_whenAddingQuantity_shouldCreateNewQuantityWithSumOf2Values() {
        Quantity quantity = new Quantity(1);
        Quantity quantityToAdd = new Quantity(2);

        assertEquals(quantity.value() + quantityToAdd.value(), quantity.add(quantityToAdd).value());
    }

    @Test
    public void given2ValidQuantity_whenSubtractingQuantity_shouldCreateNewQuantityWithDifferenceOf2Values() {
        Quantity quantity = new Quantity(2);
        Quantity quantityToSubtract = new Quantity(1);

        assertEquals(quantity.value() - quantityToSubtract.value(), quantity.remove(quantityToSubtract).value());
    }

    @Test
    public void givenSmallerQuantity_whenCheckingIfGreaterThan_shouldReturnTrue() {
        Quantity quantity = new Quantity(2);
        Quantity smallerQuantity = new Quantity(1);

        assertTrue(quantity.isGreaterThan(smallerQuantity));
    }

    @Test
    public void givenSameQuantity_whenCheckingIfGreaterThan_shouldReturnFalse() {
        Quantity quantity = new Quantity(2);
        Quantity sameQuantity = new Quantity(2);

        assertFalse(quantity.isGreaterThan(sameQuantity));
    }

    @Test
    public void givenBiggerQuantity_whenCheckingIfGreaterThan_shouldReturnFalse() {
        Quantity quantity = new Quantity(2);
        Quantity biggerQuantity = new Quantity(3);

        assertFalse(quantity.isGreaterThan(biggerQuantity));
    }
}

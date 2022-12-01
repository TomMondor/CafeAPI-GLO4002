package ca.ulaval.glo4002.cafe.small.cafe.domain.layout.cube.seat.customer;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Tax;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxTest {
    private static final Tax A_TAX = new Tax(5.5f);
    private static final Tax ANOTHER_TAX = new Tax(6);

    @Test
    public void givenTax_whenAddOtherTax_shouldReturnNewTaxWithBothTaxValues() {
        float expectedValue = A_TAX.value() + ANOTHER_TAX.value();

        Tax newTax = A_TAX.add(ANOTHER_TAX);

        assertEquals(expectedValue, newTax.value());
    }
}

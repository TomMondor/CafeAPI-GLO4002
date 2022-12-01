package ca.ulaval.glo4002.cafe.small.cafe.domain.layout.cube.seat.customer;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountTest {

    @Test
    public void givenAmountWithoutDecimal_whenGettingRoundedValue_shouldReturnAmountRoundedWithTwoDecimal() {
        Amount anAmountWithNoDecimal = new Amount(4f);

        assertEquals(4.00f, anAmountWithNoDecimal.getRoundedValue());
    }

    @Test
    public void givenAmountWithOneDecimal_whenGettingRoundedValue_shouldReturnAmountRoundedWithTwoDecimal() {
        Amount anAmountWithNoDecimal = new Amount(11.1f);

        assertEquals(11.10f, anAmountWithNoDecimal.getRoundedValue());
    }

    @Test
    public void givenAmountWithTwoDecimal_whenGettingRoundedValue_shouldReturnAmountRoundedWithTwoDecimal() {
        Amount anAmountWithTwoDecimal = new Amount(4.91f);

        assertEquals(4.91f, anAmountWithTwoDecimal.getRoundedValue());
    }

    @Test
    public void givenAmountWithMoreThanTwoDecimal_whenGettingRoundedValue_shouldReturnAmountRoundedUpWithTwoDecimal() {
        Amount anAmountWithMoreThanTwoDecimal = new Amount(4.91001f);

        assertEquals(4.92f, anAmountWithMoreThanTwoDecimal.getRoundedValue());
    }
}

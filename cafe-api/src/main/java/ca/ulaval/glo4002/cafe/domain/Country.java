package ca.ulaval.glo4002.cafe.domain;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Tax;

public enum Country {
    CA(new Tax(0.05f)),
    US(new Tax(0)),
    CL(new Tax(0.19f)),
    None(new Tax(0));

    private final Tax tax;

    Country(Tax tax) {
        this.tax = tax;
    }

    public static Country fromString(String country) {
        if (Country.contains(country)) {
            return Country.valueOf(country);
        }
        throw new InvalidConfigurationCountryException();
    }

    private static boolean contains(String other) {
        for (Country country : Country.values()) {
            if (country.name().equals(other)) {
                return true;
            }
        }
        return false;
    }

    public Tax getTax() {
        return tax;
    }
}

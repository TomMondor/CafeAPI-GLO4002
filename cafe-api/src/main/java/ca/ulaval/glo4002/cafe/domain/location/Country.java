package ca.ulaval.glo4002.cafe.domain.location;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;

public enum Country {
    CA,
    US,
    CL,
    None;

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
}

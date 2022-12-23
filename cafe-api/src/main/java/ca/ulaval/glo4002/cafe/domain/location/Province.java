package ca.ulaval.glo4002.cafe.domain.location;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;

public enum Province {
    AB,
    BC,
    MB,
    NB,
    NL,
    NT,
    NS,
    NU,
    ON,
    PE,
    QC,
    SK,
    YT;

    public static Province fromString(String province) {
        if (Province.contains(province)) {
            return Province.valueOf(province);
        }
        throw new InvalidConfigurationCountryException();
    }

    private static boolean contains(String other) {
        for (Province province : Province.values()) {
            if (province.name().equals(other)) {
                return true;
            }
        }
        return false;
    }
}

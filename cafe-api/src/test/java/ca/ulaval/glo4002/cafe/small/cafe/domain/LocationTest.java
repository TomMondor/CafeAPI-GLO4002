package ca.ulaval.glo4002.cafe.small.cafe.domain;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.location.Country;
import ca.ulaval.glo4002.cafe.domain.location.Location;
import ca.ulaval.glo4002.cafe.domain.location.Province;
import ca.ulaval.glo4002.cafe.domain.location.State;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    private static final String INVALID_COUNTRY = "WWW";
    private static final String INVALID_PROVINCE = "BOB";
    private static final String INVALID_STATE = "EOE";
    private static final String A_VALID_PROVINCE = "QC";
    private static final String A_VALID_STATE = "AL";

    @Test
    public void givenCountryWithProvinceButNoProvince_whenCreatingLocation_thenThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class,
            () -> new Location(Country.CA, Optional.empty(), Optional.empty()));
    }

    @Test
    public void givenCountryWithStateButNoState_whenCreatingLocation_thenThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class,
            () -> new Location(Country.US, Optional.empty(), Optional.empty()));
    }

    @Test
    public void givenProvinceLessCountryAndProvince_whenCreatingLocation_thenProvinceIsIgnored() {
        Location location = new Location(Country.US, Optional.of(Province.QC), Optional.of(State.AL));

        assertEquals(Optional.empty(), location.province());
    }

    @Test
    public void givenStateLessCountryAndState_whenCreatingLocation_thenStateIsIgnored() {
        Location location = new Location(Country.CA, Optional.of(Province.QC), Optional.of(State.AL));

        assertEquals(Optional.empty(), location.state());
    }

    @Test
    public void givenProvinceLessAndStateLessCountryButProvinceAndState_whenCreatingLocation_thenProvinceAndStateAreIgnored() {
        Location location = new Location(Country.CL, Optional.of(Province.QC), Optional.of(State.AL));

        assertEquals(Optional.empty(), location.province());
        assertEquals(Optional.empty(), location.state());
    }

    @Test
    public void givenAnInvalidCountry_whenCreatingLocationFromDetails_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class,
            () -> Location.fromDetails(INVALID_COUNTRY, A_VALID_PROVINCE, A_VALID_STATE));
    }

    @Test
    public void givenCACountryAndInvalidProvince_whenCreatingLocationFromDetails_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class,
            () -> Location.fromDetails("CA", INVALID_PROVINCE, A_VALID_STATE));
    }

    @Test
    public void givenUSCountryAndInvalidState_whenCreatingLocationFromDetails_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class,
            () -> Location.fromDetails("US", A_VALID_PROVINCE, INVALID_STATE));
    }

    @Test
    public void givenCACountryAndInvalidState_whenCreatingLocationFromDetails_shouldNotThrowInvalidConfigurationRequestException() {
        assertDoesNotThrow(
            () -> Location.fromDetails("CA", A_VALID_PROVINCE, INVALID_STATE));
    }

    @Test
    public void givenUSCountryAndInvalidProvince_whenCreatingLocationFromDetails_shouldNotThrowInvalidConfigurationRequestException() {
        assertDoesNotThrow(
            () -> Location.fromDetails("US", INVALID_PROVINCE, A_VALID_STATE));
    }

    @Test
    public void givenCountryWithProvinceOnly_whenCreatingLocationFromDetails_shouldIgnoreState() {
        Location location = Location.fromDetails("CA", A_VALID_PROVINCE, A_VALID_STATE);

        assertTrue(location.state().isEmpty());
    }

    @Test
    public void givenCountryWithStateOnly_whenCreatingLocationFromDetails_shouldIgnoreProvince() {
        Location location = Location.fromDetails("US", A_VALID_PROVINCE, A_VALID_STATE);

        assertTrue(location.province().isEmpty());
    }

    @Test
    public void givenCountryWithNoProvinceAndState_whenCreatingLocationFromDetails_shouldIgnoreProvinceAndState() {
        Location location = Location.fromDetails("None", A_VALID_PROVINCE, A_VALID_STATE);

        assertTrue(location.province().isEmpty());
        assertTrue(location.state().isEmpty());
    }
}

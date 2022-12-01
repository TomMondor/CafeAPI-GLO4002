package ca.ulaval.glo4002.cafe.small.cafe.domain.layout.cube;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidCubeNameException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CubeNameTest {
    private static final String EMPTY_STRING = "";

    @Test
    public void givenEmptyString_whenCreatingCubeName_shouldThrowInvalidCubeNameException() {
        assertThrows(InvalidCubeNameException.class, () -> new CubeName(EMPTY_STRING));
    }

    @Test
    public void givenSameCubeNames_whenComparingCubeName_shouldReturn0() {
        CubeName cubeName = new CubeName("Wanda");
        CubeName sameCubeName = new CubeName(cubeName.value());

        assertEquals(0, cubeName.compareTo(sameCubeName));
    }

    @Test
    public void givenCubeNameAfterAlphabetically_whenComparingCubeName_shouldReturnNegativeValue() {
        CubeName cubeName = new CubeName("TinkerBell");
        CubeName cubeNameAfterAlphabetically = new CubeName("Wanda");

        assertTrue(cubeName.compareTo(cubeNameAfterAlphabetically) < 0);
    }

    @Test
    public void givenCubeNameBeforeAlphabetically_whenComparingCubeName_shouldReturnPositiveValue() {
        CubeName cubeName = new CubeName("Wanda");
        CubeName cubeNameBeforeAlphabetically = new CubeName("TinkerBell");

        assertTrue(cubeName.compareTo(cubeNameBeforeAlphabetically) > 0);
    }
}

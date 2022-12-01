package ca.ulaval.glo4002.cafe.small.cafe.domain.layout.cube;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidCubeSizeException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CubeSizeTest {
    private static final int CUBE_SIZE = 6;
    private static final int INVALID_CUBE_SIZE = 0;

    @Test
    public void givenValidCubeSize_whenCreatingCubeSize_shouldCreateCubeSizeWithValue() {
        CubeSize cubeSize = new CubeSize(CUBE_SIZE);

        assertEquals(CUBE_SIZE, cubeSize.value());
    }

    @Test
    public void givenInvalidCubeSize_whenCreatingCubeSize_shouldThrowIllegalArgumentException() {
        assertThrows(InvalidCubeSizeException.class, () -> new CubeSize(INVALID_CUBE_SIZE));
    }
}

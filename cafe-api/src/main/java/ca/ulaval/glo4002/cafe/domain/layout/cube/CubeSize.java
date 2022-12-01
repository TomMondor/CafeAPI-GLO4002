package ca.ulaval.glo4002.cafe.domain.layout.cube;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidCubeSizeException;

public record CubeSize(int value) {
    public CubeSize {
        if (value <= 0) {
            throw new InvalidCubeSizeException();
        }
    }
}

package ca.ulaval.glo4002.cafe.domain.layout.cube;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidCubeNameException;

public record CubeName(String value) implements Comparable<CubeName> {
    public CubeName {
        if (value.isEmpty()) {
            throw new InvalidCubeNameException();
        }
    }

    @Override
    public int compareTo(CubeName o) {
        return value.compareTo(o.value);
    }
}

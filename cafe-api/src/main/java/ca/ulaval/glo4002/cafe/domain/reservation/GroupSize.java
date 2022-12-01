package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupSizeException;

public record GroupSize(int value) {
    private static final int MIN_GROUP_SIZE = 2;

    public GroupSize {
        if (value < MIN_GROUP_SIZE) {
            throw new InvalidGroupSizeException();
        }
    }
}

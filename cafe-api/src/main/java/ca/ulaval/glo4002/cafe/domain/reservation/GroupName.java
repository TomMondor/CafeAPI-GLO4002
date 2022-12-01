package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupNameException;

public record GroupName(String value) {
    public GroupName {
        if (value.isEmpty()) {
            throw new InvalidGroupNameException();
        }
    }
}

package ca.ulaval.glo4002.cafe.service.exception;

import ca.ulaval.glo4002.cafe.domain.exception.CafeException;

public class CafeNotFoundException extends CafeException {
    public CafeNotFoundException() {
        super("CAFE_NOT_FOUND", "There is currently no initialized cafe.");
    }
}

package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.Cafe;

public interface CafeRepository {
    void saveOrUpdate(Cafe cafe);

    Cafe get();
}

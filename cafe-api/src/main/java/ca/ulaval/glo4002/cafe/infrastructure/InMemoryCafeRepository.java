package ca.ulaval.glo4002.cafe.infrastructure;

import java.util.Optional;

import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.exception.CafeNotFoundException;
import ca.ulaval.glo4002.cafe.domain.Cafe;

public class InMemoryCafeRepository implements CafeRepository {
    private Optional<Cafe> cafe = Optional.empty();

    @Override
    public void saveOrUpdate(Cafe cafe) {
        this.cafe = Optional.of(cafe);
    }

    @Override
    public Cafe get() {
        return cafe.orElseThrow(CafeNotFoundException::new);
    }
}

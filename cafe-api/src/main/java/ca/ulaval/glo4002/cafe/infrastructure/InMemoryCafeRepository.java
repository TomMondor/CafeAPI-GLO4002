package ca.ulaval.glo4002.cafe.infrastructure;

import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.exception.CafeNotFoundException;

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

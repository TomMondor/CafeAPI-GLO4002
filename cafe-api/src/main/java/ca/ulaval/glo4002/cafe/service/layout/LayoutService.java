package ca.ulaval.glo4002.cafe.service.layout;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.layout.dto.LayoutDTO;

public class LayoutService {
    private final CafeRepository cafeRepository;

    public LayoutService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public LayoutDTO getLayout() {
        Cafe cafe = cafeRepository.get();
        return LayoutDTO.fromCafe(cafe);
    }
}

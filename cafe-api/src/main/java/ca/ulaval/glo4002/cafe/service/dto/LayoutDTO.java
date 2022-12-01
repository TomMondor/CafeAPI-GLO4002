package ca.ulaval.glo4002.cafe.service.dto;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;

public record LayoutDTO(CafeName name, List<Cube> cubes) {
    public static LayoutDTO fromCafe(Cafe cafe) {
        return new LayoutDTO(cafe.getName(), cafe.getLayout().getCubes());
    }
}

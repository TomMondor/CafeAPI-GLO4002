package ca.ulaval.glo4002.cafe.small.cafe.domain;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.CafeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CafeFactoryTest {

    private static final CafeName DEFAULT_CAFE_NAME = new CafeName("Les 4-Fées");

    private CafeFactory cafeFactory;

    @BeforeEach
    public void createCafeFactory() {
        cafeFactory = new CafeFactory();
    }

    @Test
    public void whenCreatingCafe_shouldHaveDefaultName() {
        Cafe cafe = cafeFactory.createCafe();

        assertEquals(DEFAULT_CAFE_NAME, cafe.getName());
    }

    @Test
    public void whenCreatingCafe_shouldCreateCubesListWithSortedSpecificCubesNames() {
        List<CubeName> expectedCubeNames = List.of(new CubeName("Bloom"), new CubeName("Merryweather"), new CubeName("Tinker Bell"), new CubeName("Wanda"));

        Cafe cafe = cafeFactory.createCafe();

        assertEquals(expectedCubeNames, cafe.getLayout().getCubes().stream().map(Cube::getName).toList());
    }
}

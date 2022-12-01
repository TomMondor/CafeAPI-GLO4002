package ca.ulaval.glo4002.cafe.domain;

import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;

public class CafeFactory {
    private static final CafeName CAFE_NAME = new CafeName("Les 4-Fées");
    private static final CubeSize CUBE_SIZE = new CubeSize(4);
    private static final List<CubeName> CUBE_NAMES =
        List.of(new CubeName("Wanda"), new CubeName("Tinker Bell"),
            new CubeName("Bloom"), new CubeName("Merryweather"));
    private static final ReservationType RESERVATION_STRATEGY_TYPE = ReservationType.Default;
    private static final TipRate GROUP_TIP_RATE = new TipRate(0);
    private static final Location LOCATION = new Location(Country.None, Optional.empty(), Optional.empty());

    public Cafe createCafe() {
        CafeConfiguration cafeConfiguration = new CafeConfiguration(CUBE_SIZE, CAFE_NAME, RESERVATION_STRATEGY_TYPE, LOCATION, GROUP_TIP_RATE);
        return new Cafe(CUBE_NAMES, cafeConfiguration);
    }
}

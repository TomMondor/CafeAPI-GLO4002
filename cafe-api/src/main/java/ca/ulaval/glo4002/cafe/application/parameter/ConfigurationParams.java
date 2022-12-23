package ca.ulaval.glo4002.cafe.application.parameter;

import ca.ulaval.glo4002.cafe.domain.CafeName;
import ca.ulaval.glo4002.cafe.domain.TipRate;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.location.Location;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;

public record ConfigurationParams(CubeSize cubeSize, CafeName cafeName, ReservationType reservationType, Location location, TipRate groupTipRate) {
    public ConfigurationParams(int cubeSize, String cafeName, String reservationType, String country, String province, String state, float groupTipRate) {
        this(new CubeSize(cubeSize), new CafeName(cafeName), ReservationType.fromString(reservationType), Location.fromDetails(country, province, state),
            new TipRate(groupTipRate / 100));
    }

    public static ConfigurationParams from(int cubeSize, String cafeName, String reservationType, String country, String province, String state,
                                           float groupTipRate) {
        return new ConfigurationParams(cubeSize, cafeName, reservationType, country, province, state, groupTipRate);
    }
}

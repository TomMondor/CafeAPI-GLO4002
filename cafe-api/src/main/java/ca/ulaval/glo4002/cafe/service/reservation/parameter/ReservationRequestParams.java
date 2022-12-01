package ca.ulaval.glo4002.cafe.service.reservation.parameter;

import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;

public record ReservationRequestParams(GroupName groupName, GroupSize groupSize) {
    public ReservationRequestParams(String groupName, int groupSize) {
        this(new GroupName(groupName), new GroupSize(groupSize));
    }

    public static ReservationRequestParams from(String groupName, int groupSize) {
        return new ReservationRequestParams(groupName, groupSize);
    }
}

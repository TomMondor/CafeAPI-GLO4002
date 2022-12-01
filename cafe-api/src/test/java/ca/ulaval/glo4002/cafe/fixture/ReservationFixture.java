package ca.ulaval.glo4002.cafe.fixture;

import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

public class ReservationFixture {
    private GroupName groupName = new GroupName("DefaultGroupName");
    private GroupSize groupSize = new GroupSize(2);

    public ReservationFixture withGroupName(GroupName groupName) {
        this.groupName = groupName;
        return this;
    }

    public ReservationFixture withGroupSize(GroupSize groupSize) {
        this.groupSize = groupSize;
        return this;
    }

    public Reservation build() {
        return new Reservation(groupName, groupSize);
    }
}

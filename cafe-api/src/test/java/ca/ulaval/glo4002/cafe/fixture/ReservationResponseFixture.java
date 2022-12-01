package ca.ulaval.glo4002.cafe.fixture;

import ca.ulaval.glo4002.cafe.api.reservation.response.ReservationResponse;

public class ReservationResponseFixture {
    private String group_name = "Wolfpack";
    private int group_size = 4;

    public ReservationResponseFixture withGroupName(String groupName) {
        this.group_name = groupName;
        return this;
    }

    public ReservationResponseFixture withGroupSize(int groupSize) {
        this.group_size = groupSize;
        return this;
    }

    public ReservationResponse build() {
        return new ReservationResponse(group_name, group_size);
    }
}

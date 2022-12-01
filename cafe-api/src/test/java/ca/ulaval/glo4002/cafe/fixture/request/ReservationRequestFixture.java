package ca.ulaval.glo4002.cafe.fixture.request;

import ca.ulaval.glo4002.cafe.api.reservation.request.ReservationRequest;

public class ReservationRequestFixture {
    private String groupName = "value";
    private int groupSize = 5;

    public ReservationRequestFixture withGroupName(String name) {
        this.groupName = name;
        return this;
    }

    public ReservationRequestFixture withGroupSize(int size) {
        this.groupSize = size;
        return this;
    }

    public ReservationRequest build() {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.group_name = groupName;
        reservationRequest.group_size = groupSize;
        return reservationRequest;
    }
}

package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;

import ca.ulaval.glo4002.cafe.api.layout.SeatStatus;
import ca.ulaval.glo4002.cafe.api.layout.response.CubeResponse;
import ca.ulaval.glo4002.cafe.api.layout.response.SeatResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;

public class CubeResponseFixture {
    private String name = "DefaultCubeName";
    private List<Seat> seats = new SeatFixture().withSeatNumber(new SeatNumber(1)).buildMultipleSeats(4);

    private static SeatResponse toSeatResponse(Seat seat) {
        String customerId = seat.getCustomer().isPresent() ? seat.getCustomer().get().getId().value() : null;
        String groupName = seat.getGroupName().isPresent() ? seat.getGroupName().get().value() : null;
        return new SeatResponse(seat.getNumber().value(), CubeResponseFixture.getSeatStatus(seat), customerId, groupName);
    }

    private static SeatStatus getSeatStatus(Seat seat) {
        if (seat.isCurrentlyOccupied()) {
            return SeatStatus.Occupied;
        } else if (seat.isCurrentlyReserved()) {
            return SeatStatus.Reserved;
        } else {
            return SeatStatus.Available;
        }
    }

    public CubeResponseFixture withCubeName(String cubeName) {
        this.name = cubeName;
        return this;
    }

    public CubeResponseFixture withSeatList(List<Seat> seats) {
        this.seats = seats;
        return this;
    }

    public CubeResponse build() {
        return new CubeResponse(name, seats.stream().map(CubeResponseFixture::toSeatResponse).toList());
    }

    public List<CubeResponse> createCubeResponsesWithCubes(List<Cube> cubes) {
        return cubes.stream().map(cube -> withCubeName(cube.getName().value()).withSeatList(cube.getSeats()).build()).toList();
    }
}

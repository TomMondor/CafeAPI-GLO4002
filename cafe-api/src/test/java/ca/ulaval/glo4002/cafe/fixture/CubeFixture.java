package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;

public class CubeFixture {
    private CubeName cubeName = new CubeName("DefaultCubeName");
    private List<Seat> seats = new SeatFixture().withSeatNumber(new SeatNumber(1)).buildMultipleSeats(4);

    public CubeFixture withCubeName(CubeName cubeName) {
        this.cubeName = cubeName;
        return this;
    }

    public CubeFixture withSeatList(List<Seat> seats) {
        this.seats = seats;
        return this;
    }

    public Cube build() {
        return new Cube(cubeName, seats);
    }
}

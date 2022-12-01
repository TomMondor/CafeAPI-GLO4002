package ca.ulaval.glo4002.cafe.domain.layout.cube;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;

public class CubeFactory {
    public Cube createCube(SeatNumber firstSeat, CubeName cubeName, CubeSize cubeSize) {
        List<Seat> seats = createCubeSeats(firstSeat.value(), cubeSize);
        return new Cube(cubeName, seats);
    }

    private List<Seat> createCubeSeats(int firstSeat, CubeSize cubeSize) {
        List<Seat> seats = new ArrayList<>();

        for (int i = firstSeat; i < firstSeat + cubeSize.value(); i++) {
            seats.add(createSeat(new SeatNumber(i)));
        }

        return seats;
    }

    private Seat createSeat(SeatNumber seatNumber) {
        return new Seat(seatNumber);
    }
}

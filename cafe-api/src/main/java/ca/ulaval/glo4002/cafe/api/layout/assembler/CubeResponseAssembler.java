package ca.ulaval.glo4002.cafe.api.layout.assembler;

import java.util.List;

import ca.ulaval.glo4002.cafe.api.layout.SeatStatus;
import ca.ulaval.glo4002.cafe.api.layout.response.CubeResponse;
import ca.ulaval.glo4002.cafe.api.layout.response.SeatResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;

public class CubeResponseAssembler {
    public CubeResponse toCubeResponse(Cube cube) {
        List<SeatResponse> seats = cube.getSeats().stream().map(this::toSeatResponse).toList();
        return new CubeResponse(cube.getName().value(), seats);
    }

    private SeatResponse toSeatResponse(Seat seat) {
        String customerId = seat.getCustomer().isPresent() ? seat.getCustomer().get().getId().value() : null;
        String groupName = seat.getGroupName().isPresent() ? seat.getGroupName().get().value() : null;
        return new SeatResponse(seat.getNumber().value(), getSeatStatus(seat), customerId, groupName);
    }

    private SeatStatus getSeatStatus(Seat seat) {
        if (seat.isCurrentlyOccupied()) {
            return SeatStatus.Occupied;
        } else if (seat.isCurrentlyReserved()) {
            return SeatStatus.Reserved;
        } else {
            return SeatStatus.Available;
        }
    }
}

package ca.ulaval.glo4002.cafe.service.customer.dto;

import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;

public record CustomerDTO(CustomerName name, SeatNumber seatNumber, Optional<GroupName> groupName) {
    public static CustomerDTO fromSeat(Seat seat) {
        return new CustomerDTO(seat.getCustomer().get().getName(), seat.getNumber(), seat.getGroupName());
    }
}

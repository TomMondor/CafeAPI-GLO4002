package ca.ulaval.glo4002.cafe.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;

public class SeatFixture {
    private SeatNumber seatNumber = new SeatNumber(1);
    private Optional<Customer> customer = Optional.empty();

    public SeatFixture withSeatNumber(SeatNumber seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public SeatFixture withCustomer(Customer customer) {
        this.customer = Optional.of(customer);
        return this;
    }

    public Seat build() {
        Seat seat = new Seat(seatNumber);
        customer.ifPresent(seat::sitCustomer);
        return seat;
    }

    public List<Seat> buildMultipleSeats(int numberOfSeat) {
        List<Seat> seatList = new ArrayList<>();
        for (int i = 0; i < numberOfSeat; i++) {
            seatList.add(new Seat(new SeatNumber(seatNumber.value() + i)));
        }
        return seatList;
    }
}

package ca.ulaval.glo4002.cafe.domain.layout.cube;

import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;

public class Cube {
    private final CubeName name;
    private final List<Seat> seats;

    public Cube(CubeName name, List<Seat> seats) {
        this.name = name;
        this.seats = seats;
    }

    public CubeName getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getNumberOfSeats() {
        return seats.size();
    }

    public Optional<Seat> getFirstAvailableSeat() {
        return seats.stream()
            .filter(Seat::isCurrentlyAvailable)
            .findFirst();
    }

    public Optional<Seat> getFirstReservedSeatForGroup(GroupName groupName) {
        return seats.stream()
            .filter(Seat::isCurrentlyReserved)
            .filter(seat -> seat.getGroupName().get().equals(groupName))
            .findFirst();
    }

    public List<Seat> getAllAvailableSeats() {
        return seats.stream()
            .filter(Seat::isCurrentlyAvailable).toList();
    }

    public void reserveAllSeats(GroupName groupName) {
        for (Seat seat : seats) {
            seat.reserveForGroup(groupName);
        }
    }
}

package ca.ulaval.glo4002.cafe.domain.layout.cube.seat;

import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.exception.SeatAlreadyOccupiedException;
import ca.ulaval.glo4002.cafe.domain.exception.SeatAlreadyReservedException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;

public class Seat {
    private final SeatNumber number;
    private Optional<GroupName> groupName = Optional.empty();
    private Optional<Customer> customer = Optional.empty();

    public Seat(SeatNumber number) {
        this.number = number;
    }

    public SeatNumber getNumber() {
        return number;
    }

    public Optional<Customer> getCustomer() {
        return customer;
    }

    public Optional<GroupName> getGroupName() {
        return groupName;
    }

    public void sitCustomer(Customer customer) {
        if (isCurrentlyOccupied()) {
            throw new SeatAlreadyOccupiedException();
        }
        this.customer = Optional.of(customer);
    }

    public void reserveForGroup(GroupName groupName) {
        if (isCurrentlyOccupied()) {
            throw new SeatAlreadyOccupiedException();
        }
        if (isCurrentlyReserved()) {
            throw new SeatAlreadyReservedException();
        }
        this.groupName = Optional.of(groupName);
    }

    public boolean isCurrentlyAvailable() {
        return this.customer.isEmpty() && this.groupName.isEmpty();
    }

    public boolean isCurrentlyReserved() {
        return this.groupName.isPresent() && this.customer.isEmpty();
    }

    public boolean isCurrentlyOccupied() {
        return this.customer.isPresent();
    }

    public boolean isReservedForGroup() {
        return this.groupName.isPresent();
    }

    public void checkout() {
        this.customer = Optional.empty();
        this.groupName = Optional.empty();
    }

    public void removeReservation() {
        this.groupName = Optional.empty();
    }
}

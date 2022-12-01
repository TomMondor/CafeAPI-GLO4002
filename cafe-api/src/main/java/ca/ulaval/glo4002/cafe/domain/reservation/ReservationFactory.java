package ca.ulaval.glo4002.cafe.domain.reservation;

public class ReservationFactory {
    public Reservation createReservation(GroupName groupName, GroupSize groupSize) {
        return new Reservation(groupName, groupSize);
    }
}

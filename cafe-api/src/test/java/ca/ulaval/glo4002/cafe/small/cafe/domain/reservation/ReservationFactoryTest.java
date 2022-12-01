package ca.ulaval.glo4002.cafe.small.cafe.domain.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationFactoryTest {

    private static final GroupName GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    private static final GroupSize GROUP_SIZE = new GroupSize(2);

    private ReservationFactory reservationFactory;

    @BeforeEach
    public void createFactory() {
        reservationFactory = new ReservationFactory();
    }

    @Test
    public void whenCreatingGroup_shouldHaveName() {
        Reservation reservation = reservationFactory.createReservation(GROUP_NAME, GROUP_SIZE);

        assertEquals(GROUP_NAME, reservation.name());
    }

    @Test
    public void whenCreatingGroup_shouldHaveGroupSize() {
        Reservation reservation = reservationFactory.createReservation(GROUP_NAME, GROUP_SIZE);

        assertEquals(GROUP_SIZE, reservation.size());
    }
}

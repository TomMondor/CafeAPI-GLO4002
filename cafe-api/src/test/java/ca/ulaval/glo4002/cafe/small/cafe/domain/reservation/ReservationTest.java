package ca.ulaval.glo4002.cafe.small.cafe.domain.reservation;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTest {

    private static final GroupName GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    private static final GroupSize GROUP_SIZE = new GroupSize(2);

    @Test
    public void whenCreateGroup_shouldHaveName() {
        Reservation reservation = new Reservation(GROUP_NAME, GROUP_SIZE);

        assertEquals(GROUP_NAME, reservation.name());
    }

    @Test
    public void whenCreateGroup_shouldHaveGroupSize() {
        Reservation reservation = new Reservation(GROUP_NAME, GROUP_SIZE);

        assertEquals(GROUP_SIZE, reservation.size());
    }
}

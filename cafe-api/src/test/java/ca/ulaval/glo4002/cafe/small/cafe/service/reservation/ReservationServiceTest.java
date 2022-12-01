package ca.ulaval.glo4002.cafe.small.cafe.service.reservation;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.fixture.CafeFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationDTOFixture;
import ca.ulaval.glo4002.cafe.fixture.ReservationFixture;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.reservation.ReservationService;
import ca.ulaval.glo4002.cafe.service.reservation.dto.ReservationDTO;
import ca.ulaval.glo4002.cafe.service.reservation.parameter.ReservationRequestParams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {
    private static final GroupName A_GROUP_NAME = new GroupName("My Hero Academia");
    private static final GroupSize A_GROUP_SIZE = new GroupSize(5);
    private static final GroupName ANOTHER_GROUP_NAME = new GroupName("Batman VS the Chipmunks");
    private static final GroupSize ANOTHER_GROUP_SIZE = new GroupSize(3);
    private static final Reservation A_RESERVATION = new ReservationFixture().withGroupName(A_GROUP_NAME).withGroupSize(A_GROUP_SIZE).build();
    private static final Reservation ANOTHER_RESERVATION = new ReservationFixture().withGroupName(ANOTHER_GROUP_NAME).withGroupSize(ANOTHER_GROUP_SIZE).build();
    private static final List<Reservation> SOME_RESERVATIONS = List.of(A_RESERVATION, ANOTHER_RESERVATION);
    private static final Cafe A_CAFE = new CafeFixture().build();

    private ReservationService reservationService;
    private ReservationFactory reservationFactory;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void createReservationService() {
        reservationFactory = mock(ReservationFactory.class);
        cafeRepository = mock(CafeRepository.class);
        reservationService = new ReservationService(cafeRepository, reservationFactory);
    }

    @Test
    public void whenMakingReservation_shouldRetrieveCafe() {
        Cafe aCafe = new CafeFixture().build();
        when(cafeRepository.get()).thenReturn(aCafe);
        when(reservationFactory.createReservation(any(), any())).thenReturn(A_RESERVATION);

        reservationService.makeReservation(new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value()));

        verify(cafeRepository).get();
    }

    @Test
    public void whenMakingReservation_shouldCreateNewGroup() {
        Cafe aCafe = new CafeFixture().build();
        when(cafeRepository.get()).thenReturn(aCafe);
        when(reservationFactory.createReservation(A_GROUP_NAME, A_GROUP_SIZE)).thenReturn(A_RESERVATION);

        reservationService.makeReservation(new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value()));

        verify(reservationFactory).createReservation(A_GROUP_NAME, A_GROUP_SIZE);
    }

    @Test
    public void whenMakingReservation_shouldTellCafeToMakeReservation() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);
        when(reservationFactory.createReservation(any(), any())).thenReturn(A_RESERVATION);

        reservationService.makeReservation(new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value()));

        verify(mockCafe).makeReservation(A_RESERVATION);
    }

    @Test
    public void whenMakingReservation_shouldUpdateCafe() {
        Cafe aCafe = new CafeFixture().build();
        when(cafeRepository.get()).thenReturn(aCafe);
        when(reservationFactory.createReservation(any(), any())).thenReturn(A_RESERVATION);

        reservationService.makeReservation(new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value()));

        verify(cafeRepository).saveOrUpdate(aCafe);
    }

    @Test
    public void whenGettingReservations_shouldRetrieveCafe() {
        when(cafeRepository.get()).thenReturn(A_CAFE);

        reservationService.getReservations();

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingReservations_shouldRetrieveCafeReservations() {
        Cafe cafe = cafeWithReservations(SOME_RESERVATIONS);
        when(cafeRepository.get()).thenReturn(cafe);

        reservationService.getReservations();

        verify(cafe).getReservations();
    }

    @Test
    public void whenGettingReservations_shouldReturnMatchingReservationDTO() {
        Cafe cafe = cafeWithReservations(SOME_RESERVATIONS);
        when(cafeRepository.get()).thenReturn(cafe);
        ReservationDTO expectedDTO = new ReservationDTOFixture().withReservation(SOME_RESERVATIONS).build();

        ReservationDTO reservationDTO = reservationService.getReservations();

        assertEquals(expectedDTO, reservationDTO);
    }

    private Cafe cafeWithReservations(List<Reservation> reservations) {
        Cafe cafe = mock(Cafe.class);
        when(cafe.getReservations()).thenReturn(reservations);
        return cafe;
    }
}

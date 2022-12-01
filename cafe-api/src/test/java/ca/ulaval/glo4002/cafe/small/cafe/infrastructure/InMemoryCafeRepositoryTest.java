package ca.ulaval.glo4002.cafe.small.cafe.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.fixture.CafeFixture;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.exception.CafeNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryCafeRepositoryTest {
    private static final Cafe A_CAFE = new CafeFixture().build();

    private CafeRepository cafeRepository;

    @BeforeEach
    public void createCafeRepository() {
        cafeRepository = new InMemoryCafeRepository();
    }

    @Test
    public void whenSavingOrUpdatingCafe_shouldSaveCafe() {
        cafeRepository.saveOrUpdate(A_CAFE);

        assertEquals(A_CAFE, cafeRepository.get());
    }

    @Test
    public void givenExistingCafe_whenGettingCafe_shouldReturnCafe() {
        cafeRepository.saveOrUpdate(A_CAFE);

        Cafe cafe = cafeRepository.get();

        assertEquals(A_CAFE, cafe);
    }

    @Test
    public void givenNoCafe_whenGettingCafe_shouldThrowCafeNotFoundException() {
        assertThrows(CafeNotFoundException.class, () -> cafeRepository.get());
    }
}

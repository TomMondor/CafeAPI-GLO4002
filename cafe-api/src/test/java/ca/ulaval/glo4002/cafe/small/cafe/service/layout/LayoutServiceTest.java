package ca.ulaval.glo4002.cafe.small.cafe.service.layout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.fixture.CafeFixture;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.layout.LayoutService;
import ca.ulaval.glo4002.cafe.service.layout.dto.LayoutDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LayoutServiceTest {
    private static final Cafe A_CAFE = new CafeFixture().build();
    private LayoutService layoutService;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void createLayoutService() {
        cafeRepository = mock(CafeRepository.class);
        layoutService = new LayoutService(cafeRepository);
    }

    @Test
    public void whenGettingLayout_shouldGetCafe() {
        when(cafeRepository.get()).thenReturn(A_CAFE);

        layoutService.getLayout();

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingLayout_shouldReturnLayoutDTO() {
        when(cafeRepository.get()).thenReturn(A_CAFE);
        LayoutDTO expectedLayoutDTO = new LayoutDTO(A_CAFE.getName(), A_CAFE.getLayout().getCubes());

        LayoutDTO actualLayoutDTO = layoutService.getLayout();

        assertEquals(expectedLayoutDTO, actualLayoutDTO);
    }
}

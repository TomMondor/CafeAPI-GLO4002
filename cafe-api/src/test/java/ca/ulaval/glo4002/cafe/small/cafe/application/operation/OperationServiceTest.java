package ca.ulaval.glo4002.cafe.small.cafe.application.operation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.operation.OperationService;
import ca.ulaval.glo4002.cafe.application.parameter.CoffeeParams;
import ca.ulaval.glo4002.cafe.application.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.application.parameter.IngredientsParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OperationServiceTest {
    private static final ConfigurationParams A_CONFIGURATION_PARAMS = new ConfigurationParams(4, "Les 4-Fées", "Default", "CA", "QC", "", 5);
    private static final IngredientsParams AN_INGREDIENTS_PARAMS = new IngredientsParams(1, 2, 3, 4);
    private static final CoffeeParams A_COFFEE_PARAMS = new CoffeeParams("coffee name", 3.25f, AN_INGREDIENTS_PARAMS);
    private OperationService operationService;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void createCafeService() {
        cafeRepository = mock(CafeRepository.class);
        operationService = new OperationService(cafeRepository);
    }

    @Test
    public void whenClosingCafe_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.closeCafe();

        verify(cafeRepository).get();
    }

    @Test
    public void whenClosingCafe_shouldCloseCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.closeCafe();

        verify(mockCafe).close();
    }

    @Test
    public void whenClosingCafe_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.closeCafe();

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenUpdatingConfiguration_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenUpdatingConfiguration_shouldCloseCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(mockCafe).close();
    }

    @Test
    public void whenUpdatingConfiguration_shouldUpdateCafeConfiguration() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);
        ArgumentCaptor<CafeConfiguration> argument = ArgumentCaptor.forClass(CafeConfiguration.class);
        CafeConfiguration expectedConfiguration = new CafeConfiguration(
            A_CONFIGURATION_PARAMS.cubeSize(),
            A_CONFIGURATION_PARAMS.cafeName(),
            A_CONFIGURATION_PARAMS.reservationType(),
            A_CONFIGURATION_PARAMS.location(),
            A_CONFIGURATION_PARAMS.groupTipRate());

        operationService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(mockCafe).updateConfiguration(argument.capture());
        assertEquals(expectedConfiguration, argument.getValue());
    }

    @Test
    public void whenUpdatingConfiguration_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenAddingMenuItem_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.addMenuItem(A_COFFEE_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenAddingMenuItem_shouldAddMenuItem() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.addMenuItem(A_COFFEE_PARAMS);

        verify(mockCafe).addMenuItem(new Coffee(A_COFFEE_PARAMS.name(), A_COFFEE_PARAMS.cost(), A_COFFEE_PARAMS.ingredients()));
    }

    @Test
    public void whenAddingMenuItem_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.addMenuItem(A_COFFEE_PARAMS);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }
}

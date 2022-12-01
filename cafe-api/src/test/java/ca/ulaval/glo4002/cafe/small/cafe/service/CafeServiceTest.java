package ca.ulaval.glo4002.cafe.small.cafe.service;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.fixture.CafeFixture;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeService;
import ca.ulaval.glo4002.cafe.service.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.service.dto.LayoutDTO;
import ca.ulaval.glo4002.cafe.service.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CafeServiceTest {
    private static final Cafe A_CAFE = new CafeFixture().build();
    private static final ConfigurationParams A_CONFIGURATION_PARAMS = new ConfigurationParams(4, "Les 4-Fées", "Default", "CA", "QC", "", 5);
    private static final IngredientsParams AN_INGREDIENTS_PARAMS = new IngredientsParams(1, 2, 3, 4);
    private CafeService cafeService;
    private CafeRepository cafeRepository;
    private CafeFactory cafeFactory;

    @BeforeEach
    public void createCafeService() {
        cafeFactory = mock(CafeFactory.class);
        cafeRepository = mock(CafeRepository.class);
        cafeService = new CafeService(cafeRepository, cafeFactory);
    }

    @Test
    public void whenInitializingCafe_shouldCreateCafe() {
        cafeService.initializeCafe();

        verify(cafeFactory).createCafe();
    }

    @Test
    public void whenInitializingCafe_shouldSaveCafe() {
        when(cafeFactory.createCafe()).thenReturn(A_CAFE);

        cafeService.initializeCafe();

        verify(cafeRepository).saveOrUpdate(A_CAFE);
    }

    @Test
    public void whenGettingLayout_shouldGetCafe() {
        when(cafeRepository.get()).thenReturn(A_CAFE);

        cafeService.getLayout();

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingLayout_shouldReturnLayoutDTO() {
        when(cafeRepository.get()).thenReturn(A_CAFE);
        LayoutDTO expectedLayoutDTO = new LayoutDTO(A_CAFE.getName(), A_CAFE.getLayout().getCubes());

        LayoutDTO actualLayoutDTO = cafeService.getLayout();

        assertEquals(expectedLayoutDTO, actualLayoutDTO);
    }

    @Test
    public void whenClosingCafe_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        cafeService.closeCafe();

        verify(cafeRepository).get();
    }

    @Test
    public void whenClosingCafe_shouldCloseCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        cafeService.closeCafe();

        verify(mockCafe).close();
    }

    @Test
    public void whenClosingCafe_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        cafeService.closeCafe();

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenUpdatingConfiguration_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        cafeService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenUpdatingConfiguration_shouldCloseCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        cafeService.updateConfiguration(A_CONFIGURATION_PARAMS);

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

        cafeService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(mockCafe).updateConfiguration(argument.capture());
        assertEquals(expectedConfiguration, argument.getValue());
    }

    @Test
    public void whenUpdatingConfiguration_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        cafeService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        cafeService.addIngredientsToInventory(AN_INGREDIENTS_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldAddIngredientsInInventory() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        cafeService.addIngredientsToInventory(AN_INGREDIENTS_PARAMS);

        verify(mockCafe).addIngredientsToInventory(
            List.of(AN_INGREDIENTS_PARAMS.chocolate(), AN_INGREDIENTS_PARAMS.milk(), AN_INGREDIENTS_PARAMS.water(), AN_INGREDIENTS_PARAMS.espresso()));
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        cafeService.addIngredientsToInventory(AN_INGREDIENTS_PARAMS);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenGettingInventory_shouldGetCafe() {
        when(cafeRepository.get()).thenReturn(A_CAFE);

        cafeService.getInventory();

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingInventory_shouldReturnInventoryDTO() {
        when(cafeRepository.get()).thenReturn(A_CAFE);
        InventoryDTO expectedInventoryDTO = new InventoryDTO(new HashMap<>());

        InventoryDTO actualInventoryDTO = cafeService.getInventory();

        assertEquals(expectedInventoryDTO, actualInventoryDTO);
    }
}

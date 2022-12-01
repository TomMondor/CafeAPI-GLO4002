package ca.ulaval.glo4002.cafe.service;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.service.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.service.dto.LayoutDTO;
import ca.ulaval.glo4002.cafe.service.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

public class CafeService {
    private final CafeRepository cafeRepository;
    private final CafeFactory cafeFactory;

    public CafeService(CafeRepository cafeRepository, CafeFactory cafeFactory) {
        this.cafeRepository = cafeRepository;
        this.cafeFactory = cafeFactory;
    }

    public void initializeCafe() {
        Cafe cafe = cafeFactory.createCafe();
        cafeRepository.saveOrUpdate(cafe);
    }

    public LayoutDTO getLayout() {
        Cafe cafe = cafeRepository.get();
        return LayoutDTO.fromCafe(cafe);
    }

    public void updateConfiguration(ConfigurationParams configurationParams) {
        Cafe cafe = cafeRepository.get();
        CafeConfiguration cafeConfiguration = new CafeConfiguration(
            configurationParams.cubeSize(),
            configurationParams.cafeName(),
            configurationParams.reservationType(),
            configurationParams.location(),
            configurationParams.groupTipRate());
        cafe.updateConfiguration(cafeConfiguration);
        cafe.close();
        cafeRepository.saveOrUpdate(cafe);
    }

    public void closeCafe() {
        Cafe cafe = cafeRepository.get();
        cafe.close();
        cafeRepository.saveOrUpdate(cafe);
    }

    public void addIngredientsToInventory(IngredientsParams ingredientsParams) {
        Cafe cafe = cafeRepository.get();
        cafe.addIngredientsToInventory(
            List.of(ingredientsParams.chocolate(), ingredientsParams.milk(), ingredientsParams.water(), ingredientsParams.espresso()));
        cafeRepository.saveOrUpdate(cafe);
    }

    public InventoryDTO getInventory() {
        Cafe cafe = cafeRepository.get();
        return InventoryDTO.fromInventory(cafe.getInventory());
    }
}

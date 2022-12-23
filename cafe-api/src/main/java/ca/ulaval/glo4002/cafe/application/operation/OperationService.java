package ca.ulaval.glo4002.cafe.application.operation;

import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.parameter.CoffeeParams;
import ca.ulaval.glo4002.cafe.application.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;

public class OperationService {
    private final CafeRepository cafeRepository;

    public OperationService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
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

    public void addMenuItem(CoffeeParams coffeeParams) {
        Cafe cafe = cafeRepository.get();
        cafe.addMenuItem(new Coffee(coffeeParams.name(), coffeeParams.cost(), coffeeParams.ingredients()));
        cafeRepository.saveOrUpdate(cafe);
    }
}

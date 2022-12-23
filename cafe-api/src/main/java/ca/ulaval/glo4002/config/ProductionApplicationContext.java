package ca.ulaval.glo4002.config;

import java.util.List;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import ca.ulaval.glo4002.cafe.api.customer.customerAction.CustomerActionResource;
import ca.ulaval.glo4002.cafe.api.customer.registration.RegistrationResource;
import ca.ulaval.glo4002.cafe.api.exception.mapper.CafeExceptionMapper;
import ca.ulaval.glo4002.cafe.api.exception.mapper.CatchallExceptionMapper;
import ca.ulaval.glo4002.cafe.api.exception.mapper.ConstraintViolationExceptionMapper;
import ca.ulaval.glo4002.cafe.api.inventory.InventoryResource;
import ca.ulaval.glo4002.cafe.api.layout.LayoutResource;
import ca.ulaval.glo4002.cafe.api.operation.OperationResource;
import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.application.layout.LayoutService;
import ca.ulaval.glo4002.cafe.application.operation.OperationService;
import ca.ulaval.glo4002.cafe.application.registration.RegistrationService;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;

public class ProductionApplicationContext implements ApplicationContext {
    private static final int PORT = 8181;

    public int getPort() {
        return PORT;
    }

    public ResourceConfig initializeResourceConfig() {
        CafeRepository cafeRepository = new InMemoryCafeRepository();

        RegistrationService groupService = new RegistrationService(cafeRepository, new ReservationFactory(), new CustomerFactory());
        CustomerService customersService = new CustomerService(cafeRepository);
        OperationService cafeService = new OperationService(cafeRepository);
        LayoutService layoutService = new LayoutService(cafeRepository);
        InventoryService inventoryService = new InventoryService(cafeRepository);

        initializeCafe(cafeRepository);

        return createResourceConfig(cafeService, groupService, customersService, layoutService, inventoryService);
    }

    private void initializeCafe(CafeRepository cafeRepository) {
        List<Coffee> defaultMenuItems = getDefaultMenuItems();
        Cafe cafe = new CafeFactory().createCafe(defaultMenuItems);
        cafeRepository.saveOrUpdate(cafe);
    }

    private ResourceConfig createResourceConfig(OperationService cafeService, RegistrationService reservationService, CustomerService customersService,
                                                LayoutService layoutService, InventoryService inventoryService) {
        return new ResourceConfig().packages("ca.ulaval.glo4002.cafe").property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
            .register(new OperationResource(cafeService))
            .register(new InventoryResource(inventoryService))
            .register(new LayoutResource(layoutService))
            .register(new CustomerActionResource(customersService))
            .register(new RegistrationResource(reservationService))
            .register(new CafeExceptionMapper())
            .register(new CatchallExceptionMapper())
            .register(new ConstraintViolationExceptionMapper());
    }

    private List<Coffee> getDefaultMenuItems() {
        return List.of(
            new Coffee(new CoffeeName("Americano"), new Amount(2.25f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Water, new Quantity(50))))),

            new Coffee(new CoffeeName("Dark Roast"), new Amount(2.10f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(40)),
                new Ingredient(IngredientType.Water, new Quantity(40)),
                new Ingredient(IngredientType.Chocolate, new Quantity(10)),
                new Ingredient(IngredientType.Milk, new Quantity(10))))),

            new Coffee(new CoffeeName("Cappuccino"), new Amount(3.29f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Water, new Quantity(40)),
                new Ingredient(IngredientType.Milk, new Quantity(10))))),

            new Coffee(new CoffeeName("Espresso"), new Amount(2.95f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(60))))),

            new Coffee(new CoffeeName("Flat White"), new Amount(3.75f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(50))))),

            new Coffee(new CoffeeName("Latte"), new Amount(2.95f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(50))))),

            new Coffee(new CoffeeName("Macchiato"), new Amount(4.75f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(80)),
                new Ingredient(IngredientType.Milk, new Quantity(20))))),

            new Coffee(new CoffeeName("Mocha"), new Amount(4.15f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(40)),
                new Ingredient(IngredientType.Chocolate, new Quantity(10)))))
        );
    }
}

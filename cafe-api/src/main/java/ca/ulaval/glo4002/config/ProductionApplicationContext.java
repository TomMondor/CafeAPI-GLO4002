package ca.ulaval.glo4002.config;

import java.util.List;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import ca.ulaval.glo4002.cafe.api.CafeResource;
import ca.ulaval.glo4002.cafe.api.customer.CustomerResource;
import ca.ulaval.glo4002.cafe.api.exception.mapper.CafeExceptionMapper;
import ca.ulaval.glo4002.cafe.api.exception.mapper.CatchallExceptionMapper;
import ca.ulaval.glo4002.cafe.api.exception.mapper.ConstraintViolationExceptionMapper;
import ca.ulaval.glo4002.cafe.api.reservation.ReservationResource;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeService;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.reservation.ReservationService;

public class ProductionApplicationContext implements ApplicationContext {
    private static final int PORT = 8181;

    public int getPort() {
        return PORT;
    }

    public ResourceConfig initializeResourceConfig() {
        CafeRepository cafeRepository = new InMemoryCafeRepository();

        ReservationService groupService = new ReservationService(cafeRepository, new ReservationFactory());
        CustomerService customersService = new CustomerService(cafeRepository, new CustomerFactory());
        CafeService cafeService = new CafeService(cafeRepository);

        initializeCafe(cafeRepository);

        return createResourceConfig(cafeService, groupService, customersService);
    }

    private void initializeCafe(CafeRepository cafeRepository) {
        List<MenuItem> defaultMenuItems = getDefaultMenuItems();
        Cafe cafe = new CafeFactory().createCafe(defaultMenuItems);
        cafeRepository.saveOrUpdate(cafe);
    }

    private ResourceConfig createResourceConfig(CafeService cafeService, ReservationService groupService, CustomerService customersService) {
        return new ResourceConfig().packages("ca.ulaval.glo4002.cafe").property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
            .register(new CafeResource(cafeService, customersService))
            .register(new CustomerResource(customersService))
            .register(new ReservationResource(groupService))
            .register(new CafeExceptionMapper())
            .register(new CatchallExceptionMapper())
            .register(new ConstraintViolationExceptionMapper());
    }

    private List<MenuItem> getDefaultMenuItems() {
        return List.of(
            new MenuItem(new MenuItemName("Americano"), new Amount(2.25f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Water, new Quantity(50))))),

            new MenuItem(new MenuItemName("Dark Roast"), new Amount(2.10f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(40)),
                new Ingredient(IngredientType.Water, new Quantity(40)),
                new Ingredient(IngredientType.Chocolate, new Quantity(10)),
                new Ingredient(IngredientType.Milk, new Quantity(10))))),

            new MenuItem(new MenuItemName("Cappuccino"), new Amount(3.29f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Water, new Quantity(40)),
                new Ingredient(IngredientType.Milk, new Quantity(10))))),

            new MenuItem(new MenuItemName("Espresso"), new Amount(2.95f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(60))))),

            new MenuItem(new MenuItemName("Flat White"), new Amount(3.75f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(50))))),

            new MenuItem(new MenuItemName("Latte"), new Amount(2.95f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(50))))),

            new MenuItem(new MenuItemName("Macchiato"), new Amount(4.75f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(80)),
                new Ingredient(IngredientType.Milk, new Quantity(20))))),

            new MenuItem(new MenuItemName("Mocha"), new Amount(4.15f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(40)),
                new Ingredient(IngredientType.Chocolate, new Quantity(10)))))
        );
    }
}

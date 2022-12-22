package ca.ulaval.glo4002.cafe.api.customer.customerAction;

import ca.ulaval.glo4002.cafe.api.customer.customerAction.assembler.BillResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.customerAction.assembler.CustomerResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.customerAction.assembler.OrdersResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.customerAction.request.OrderRequest;
import ca.ulaval.glo4002.cafe.api.customer.customerAction.response.BillResponse;
import ca.ulaval.glo4002.cafe.api.customer.customerAction.response.CustomerResponse;
import ca.ulaval.glo4002.cafe.api.customer.customerAction.response.OrdersResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CustomerOrderParams;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerActionResource {
    private final CustomerService customersService;
    private final CustomerResponseAssembler customerResponseAssembler = new CustomerResponseAssembler();
    private final BillResponseAssembler billResponseAssembler = new BillResponseAssembler();
    private final OrdersResponseAssembler ordersResponseAssembler = new OrdersResponseAssembler();

    public CustomerActionResource(CustomerService customersService) {
        this.customersService = customersService;
    }

    @GET
    @Path("/{customerId}")
    public Response getCustomer(@PathParam("customerId") String customerId) {
        CustomerResponse customerResponse = customerResponseAssembler.toCustomerResponse(customersService.getCustomer(new CustomerId(customerId)));
        return Response.ok(customerResponse).build();
    }

    @PUT
    @Path("/{customerId}/orders")
    public Response putOrderForCustomer(@PathParam("customerId") String customerId, @Valid OrderRequest orderRequest) {
        CustomerOrderParams customerOrderParams = CustomerOrderParams.from(customerId, orderRequest.orders);
        customersService.placeOrder(customerOrderParams);
        return Response.ok().build();
    }

    @GET
    @Path("/{customerId}/bill")
    public Response getCustomerBill(@PathParam("customerId") String customerId) {
        BillResponse billResponse = billResponseAssembler.toBillResponse(customersService.getCustomerBill(new CustomerId(customerId)));
        return Response.ok(billResponse).build();
    }

    @GET
    @Path("/{customerId}/orders")
    public Response getOrders(@PathParam("customerId") String customerId) {
        OrdersResponse ordersResponse = ordersResponseAssembler.toOrdersResponse(customersService.getOrder(new CustomerId(customerId)));
        return Response.ok(ordersResponse).build();
    }
}

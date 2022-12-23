package ca.ulaval.glo4002.cafe.api.layout;

import ca.ulaval.glo4002.cafe.api.layout.assembler.LayoutResponseAssembler;
import ca.ulaval.glo4002.cafe.api.layout.response.LayoutResponse;
import ca.ulaval.glo4002.cafe.application.layout.LayoutService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/layout")
@Produces(MediaType.APPLICATION_JSON)
public class LayoutResource {
    private final LayoutService layoutService;
    private final LayoutResponseAssembler layoutResponseAssembler = new LayoutResponseAssembler();

    public LayoutResource(LayoutService layoutService) {
        this.layoutService = layoutService;
    }

    @GET
    @Path("")
    public Response layout() {
        LayoutResponse response = layoutResponseAssembler.toLayoutResponse(layoutService.getLayout());
        return Response.ok(response).build();
    }
}

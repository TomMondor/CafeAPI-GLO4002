package ca.ulaval.glo4002.cafe.small.cafe.api.layout.assembler;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.layout.assembler.LayoutResponseAssembler;
import ca.ulaval.glo4002.cafe.api.layout.response.LayoutResponse;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.fixture.CafeFixture;
import ca.ulaval.glo4002.cafe.fixture.CubeResponseFixture;
import ca.ulaval.glo4002.cafe.fixture.LayoutResponseFixture;
import ca.ulaval.glo4002.cafe.service.dto.LayoutDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LayoutResponseAssemblerTest {
    private static final Cafe A_CAFE = new CafeFixture().build();

    private final LayoutResponseAssembler layoutResponseAssembler = new LayoutResponseAssembler();

    @Test
    public void givenACafe_whenAssemblingLayoutResponse_shouldReturnValidLayoutResponse() {
        LayoutDTO layoutDTO = LayoutDTO.fromCafe(A_CAFE);
        LayoutResponse expectedLayoutResponse = new LayoutResponseFixture().withName(A_CAFE.getName().value()).withCubeResponses(
            new CubeResponseFixture().createCubeResponsesWithCubes(A_CAFE.getLayout().getCubes())).build();

        LayoutResponse actualLayoutResponse =
            layoutResponseAssembler.toLayoutResponse(layoutDTO);

        assertEquals(expectedLayoutResponse.name(), actualLayoutResponse.name());
        assertEquals(expectedLayoutResponse.cubes(), actualLayoutResponse.cubes());
    }
}

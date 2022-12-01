package ca.ulaval.glo4002.cafe.small.cafe.api.layout.assembler;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.layout.assembler.CubeResponseAssembler;
import ca.ulaval.glo4002.cafe.api.layout.response.CubeResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.fixture.CubeFixture;
import ca.ulaval.glo4002.cafe.fixture.CubeResponseFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CubeResponseAssemblerTest {
    private static final CubeName A_CUBE_NAME = new CubeName("John");

    @Test
    public void whenAssemblingCubeResponse_shouldReturnCubeResponse() {
        CubeResponseAssembler cubeResponseAssembler = new CubeResponseAssembler();
        CubeResponse expectedResponse = new CubeResponseFixture().withCubeName(A_CUBE_NAME.value()).build();

        CubeResponse actualCubeResponse = cubeResponseAssembler.toCubeResponse(new CubeFixture().withCubeName(A_CUBE_NAME).build());

        assertEquals(expectedResponse, actualCubeResponse);
    }
}

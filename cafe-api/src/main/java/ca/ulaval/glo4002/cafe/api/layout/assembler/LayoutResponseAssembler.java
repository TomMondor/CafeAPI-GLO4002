package ca.ulaval.glo4002.cafe.api.layout.assembler;

import java.util.List;

import ca.ulaval.glo4002.cafe.api.layout.response.CubeResponse;
import ca.ulaval.glo4002.cafe.api.layout.response.LayoutResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.service.dto.LayoutDTO;

public class LayoutResponseAssembler {

    public LayoutResponse toLayoutResponse(LayoutDTO layoutDTO) {
        List<CubeResponse> cubeResponses = toCubeResponses(layoutDTO.cubes());
        return new LayoutResponse(layoutDTO.name().value(), cubeResponses);
    }

    private List<CubeResponse> toCubeResponses(List<Cube> cubes) {
        CubeResponseAssembler cubeResponseAssembler = new CubeResponseAssembler();
        return cubes.stream().map(cubeResponseAssembler::toCubeResponse).toList();
    }
}

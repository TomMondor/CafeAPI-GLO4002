package ca.ulaval.glo4002.cafe.api.exception.mapper;

import ca.ulaval.glo4002.cafe.api.exception.response.ErrorResponse;
import ca.ulaval.glo4002.cafe.domain.exception.CafeException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CafeExceptionMapper implements ExceptionMapper<CafeException> {
    @Override
    public Response toResponse(CafeException exception) {
        ErrorResponse response = new ErrorResponse(exception.getError(), exception.getMessage());
        return Response.status(ExceptionStatusMapper.getResponseStatus(exception))
                .entity(response).build();
    }
}

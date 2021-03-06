package it.xpug.frameworkless.hangman.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebResponse {
    private final HttpServletResponse httpServletResponse;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public void respond(int statusCode, Object body) throws IOException {
        httpServletResponse.setStatus(statusCode);
        httpServletResponse.setContentType("application/json");
        objectMapper.writeValue(httpServletResponse.getWriter(), body);
    }

    public void clientError(ClientError clientError) throws IOException {
        respond(clientError.getStatus(), new ExceptionBody(clientError));
    }

    public void serverError(Exception exception) throws IOException {
        respond(500, new ExceptionBody(exception));
    }

    @Getter
    static class ExceptionBody {
        final String exception;
        final String message;
        final Integer status;

        public ExceptionBody(ClientError clientError) {
            this.exception = clientError.getClass().getName();
            this.message = clientError.getMessage();
            this.status = clientError.getStatus();
        }

        public ExceptionBody(Exception exception) {
            this.exception = exception.getClass().getName();
            this.message = "Internal server error";
            this.status = 500;
        }
    }
}

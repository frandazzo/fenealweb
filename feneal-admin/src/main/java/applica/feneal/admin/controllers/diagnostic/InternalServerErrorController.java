package applica.feneal.admin.controllers.diagnostic;

import applica.feneal.domain.model.utils.LoggerClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by fgran on 22/05/2016.
 */
@ControllerAdvice
public class InternalServerErrorController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handle(HttpMessageNotReadableException e) {
        LoggerClass.error("HTTP Internal Server Error " + e.getMessage());
        throw e;
    }
}

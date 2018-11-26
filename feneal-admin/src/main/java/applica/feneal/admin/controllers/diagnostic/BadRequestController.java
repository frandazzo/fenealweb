package applica.feneal.admin.controllers.diagnostic;

import applica.feneal.domain.model.utils.LoggerClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by fgran on 14/04/2016.
 */
@ControllerAdvice
public class BadRequestController {

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
//    public void handle(Exception e) throws Exception {
//        LoggerClass.error("Bad HTTP request " + e.getMessage());
//        throw e;
//    }
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
//    public void handle1(Exception e) throws Exception {
//        LoggerClass.error("Bad HTTP request " + e.getMessage());
//        throw e;
//    }
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public void handle11(Exception e) throws Exception {
//        LoggerClass.error("Bad HTTP request " + e.getMessage());
//        throw e;
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.FOUND)
//    public void handle0(Exception e) throws Exception {
//        LoggerClass.error("Bad HTTP request " + e.getMessage());
//        throw e;
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
//    public void handle00(Exception e) throws Exception {
//        LoggerClass.error("Bad HTTP request " + e.getMessage());
//        throw e;
//    }
//
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
//    public void handle2(Exception e) throws Exception {
//        LoggerClass.error("Bad HTTP request " + e.getMessage());
//        throw e;
//    }
}

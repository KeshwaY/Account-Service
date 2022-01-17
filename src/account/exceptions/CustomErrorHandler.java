package account.exceptions;

import account.exceptions.RequestValidationExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomErrorHandler{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RequestValidationExceptionDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest httpServletRequest
    ) {
        BindingResult bindingResult = ex.getBindingResult();
        RequestValidationExceptionDTO dto = new RequestValidationExceptionDTO(
                Timestamp.valueOf(LocalDateTime.now()),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                bindingResult.getFieldErrors().get(0).getDefaultMessage(),
                httpServletRequest.getRequestURI()
        );
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RequestValidationExceptionDTO> handleConstraintViolationException(
            ConstraintViolationException ex,
            HttpServletRequest httpServletRequest
    ) {
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            builder.append(violation.getMessage()).append(", ");
        }
        builder.replace(builder.length() - 2, builder.length(), "");
        RequestValidationExceptionDTO dto = new RequestValidationExceptionDTO(
                Timestamp.valueOf(LocalDateTime.now()),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                builder.toString(),
                httpServletRequest.getRequestURI()
        );
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}

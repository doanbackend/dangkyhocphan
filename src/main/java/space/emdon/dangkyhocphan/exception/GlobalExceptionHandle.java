package space.emdon.dangkyhocphan.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

import java.nio.file.AccessDeniedException;
import java.util.ConcurrentModificationException;

@Slf4j
@ControllerAdvice
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GlobalExceptionHandle {

    @ExceptionHandler(value = ConstraintViolationException.class)
    ResponseEntity<ApiResponse> handlingConstraitViolationException(
            ConstraintViolationException exception){
            String message = exception.getConstraintViolations().stream()
                    .findFirst()
                    .map(violation ->violation.getMessage())
                    .orElse(ErrorCode.INVALID_KEY.getMessage());

            ApiResponse response = new ApiResponse<>();
            response.setCode(ErrorCode.INVALID_KEY.getCode());
            response.setMessage(message);
            return  ResponseEntity.badRequest().body(response);

    }
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(Exception exception){
        log.error("EXCEPTION CLASS: {}",exception.getClass().getName());
        log.error("EXCEPTION MESSAGE: {}" , exception.getMessage(), exception);
        ApiResponse response = new ApiResponse();
        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        response.setMessage(
                ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()
                        + " : " + exception.getMessage());
        return ResponseEntity.status(500).body(response);

    }
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    ResponseEntity<ApiResponse> handlingDataIntegrityViolation(
            DataIntegrityViolationException exception){
        ApiResponse response = new ApiResponse();
        response.setCode(ErrorCode.INVALID_KEY.getCode());
        response.setMessage(
                "Data integrity violation" + exception.getRootCause().getMessage());
        return ResponseEntity.status(ErrorCode.INVALID_KEY.getStatusCode()).body(response);
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedExceptionException(
            AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(
                        ApiResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        if (errorCode == ErrorCode.USER_NOT_EXIST || errorCode == ErrorCode.INVALID_CREDENTIAL) {
            return ResponseEntity.status(401).body(response);

        }
        return ResponseEntity.badRequest().body(response);

    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidException(
            MethodArgumentNotValidException exception){
        var fieldError= exception.getBindingResult().getFieldErrors().get(0);
        String messagekey = fieldError.getDefaultMessage();
        ErrorCode errorCode;
        String errorMessage;
        try{
            errorCode = ErrorCode.valueOf(messagekey);
            errorMessage = errorCode.getMessage();
        }catch (IllegalArgumentException e) {
            errorCode = ErrorCode.INVALID_KEY;
            errorMessage = messagekey;
        }
        ApiResponse response = ApiResponse.builder().code(errorCode
                                .getCode())
                                .message(errorMessage)
                            .build();
        return  ResponseEntity.badRequest().body(response);

    }
}

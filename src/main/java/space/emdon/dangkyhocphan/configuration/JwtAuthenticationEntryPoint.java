package space.emdon.dangkyhocphan.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;
import space.emdon.dangkyhocphan.exception.ErrorCode;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
@Override
public void commence(
	@NotNull HttpServletRequest request,
	HttpServletResponse response,
	@NotNull AuthenticationException authentication)
	throws IOException, ServletException {
	ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
	response.setStatus(errorCode.getStatusCode().value());
	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	ApiResponse<?> apiresponse =
		ApiResponse.builder().code(errorCode.getCode()).message((errorCode.getMessage())).build();

	ObjectMapper objectMapper = new ObjectMapper();
	response.getWriter().write(objectMapper.writeValueAsString(apiresponse));
	response.flushBuffer();
}
}

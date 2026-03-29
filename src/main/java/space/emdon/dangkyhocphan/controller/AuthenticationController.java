package space.emdon.dangkyhocphan.controller;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.emdon.dangkyhocphan.dto.request.AuthenticationRequest;
import space.emdon.dangkyhocphan.dto.request.IntrospectRequest;
import space.emdon.dangkyhocphan.dto.request.LogoutRequest;
import space.emdon.dangkyhocphan.dto.request.RefreshRequest;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;
import space.emdon.dangkyhocphan.dto.response.AuthenticationResponse;
import space.emdon.dangkyhocphan.dto.response.IntrospectResponse;
import space.emdon.dangkyhocphan.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
final AuthenticationService authenticationService;

@PostMapping("/login")
ApiResponse<AuthenticationResponse> authenticate(
	@RequestBody @Valid AuthenticationRequest request) throws Exception, JOSEException {
	var result = authenticationService.authenticate(request);
	return ApiResponse.<AuthenticationResponse>builder().result(result).build();
}

@PostMapping("/introspect")
ApiResponse<IntrospectResponse> introspect(@RequestBody @Valid IntrospectRequest request)
	throws Exception, JOSEException {
	var result = authenticationService.introspect(request);
	return ApiResponse.<IntrospectResponse>builder().result(result).build();
}

@PostMapping("/refresh")
ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request)
	throws Exception, JOSEException {
	var result = authenticationService.refreshToken(request);
	return ApiResponse.<AuthenticationResponse>builder().result(result).build();
}

@PostMapping("/logout")
ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws Exception, JOSEException {
	authenticationService.logout(request);
	return ApiResponse.<Void>builder().build();
}
}

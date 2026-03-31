package space.emdon.dangkyhocphan.rbac.user;

import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
static final String LOG_USER_EMAIL_FORMAT = "Useremail: {}";

UserService userService;

@PostMapping
public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
	ApiResponse<UserResponse> response = new ApiResponse<>();
	response.setCode(1000);
	response.setMessage("User created successfully");
	response.setResult(userService.createUser(request));
	return response;
}

@GetMapping
ApiResponse<List<UserResponse>> getAllUsers() {

	var authentication = SecurityContextHolder.getContext().getAuthentication();

	log.info(LOG_USER_EMAIL_FORMAT, authentication.getName());

	authentication
		.getAuthorities()
		.forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

	return ApiResponse.<List<UserResponse>>builder().result(userService.getAllUsers()).build();
}

@GetMapping("/normal")
ApiResponse<List<UserResponse>> getUsers() {
	var authentication = SecurityContextHolder.getContext().getAuthentication();

	log.info(LOG_USER_EMAIL_FORMAT, authentication.getName());

	authentication
		.getAuthorities()
		.forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

	return ApiResponse.<List<UserResponse>>builder().result(userService.getUsers()).build();
}

@GetMapping("/students")
ApiResponse<List<UserResponse>> getStudents() {
	var authentication = SecurityContextHolder.getContext().getAuthentication();

	log.info(LOG_USER_EMAIL_FORMAT, authentication.getName());

	authentication
		.getAuthorities()
		.forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

	return ApiResponse.<List<UserResponse>>builder().result(userService.getStudents()).build();
}

@GetMapping("/{id}")
ApiResponse<UserResponse> getUser(@PathVariable String id) {
	return ApiResponse.<UserResponse>builder().result(userService.getUserById(id)).build();
}

@GetMapping("/myinfo")
ApiResponse<UserResponse> getMyInfo() {
	return ApiResponse.<UserResponse>builder().result(userService.getMyInfo(SecurityContextHolder.getContext().getAuthentication())).build();
}

@PutMapping("/{id}")
ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody @Valid UserRequest request) {
	return ApiResponse.<UserResponse>builder().result(userService.updateUser(id, request)).build();
}

@DeleteMapping("/{id}")
public void deleteUser(@PathVariable String id) {
	userService.deleteUser(id);
}
}

package space.emdon.dangkyhocphan.rbac.user;

import jakarta.validation.Valid;
import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
static final String LOG_USER_EMAIL_FORMAT = "Useremail: {}";

UserService userService;

@PostMapping
ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
	return ApiResponse.<UserResponse>builder()
		.result(userService.createUser(request))
		.build();
}

@GetMapping
ApiResponse<Page<UserResponse>> getAllUsers(@PageableDefault(page = 0, size = 50, sort = "numbered", direction = Sort.Direction.DESC)Pageable pageable) {
	return ApiResponse.<Page<UserResponse>>builder()
		.result(userService.getAllUsers(pageable))
		.build();
}

@GetMapping("/normal")
ApiResponse<Page<UserResponse>> getUsers(@PageableDefault(page = 0, size = 50, sort = "numbered", direction = Sort.Direction.DESC)Pageable pageable) {
	return ApiResponse.<Page<UserResponse>>builder()
		.result(userService.getUsers(pageable))
		.build();
}

@GetMapping("/students")
ApiResponse<Page<UserResponse>> getStudents(@PageableDefault(page = 0, size = 50, sort = "numbered", direction = Sort.Direction.DESC)Pageable pageable) {
	return ApiResponse.<Page<UserResponse>>builder()
		.result(userService.getStudents(pageable))
		.build();
}

@GetMapping("/{id}")
ApiResponse<UserResponse> getUser(@PathVariable String id) {
	return ApiResponse.<UserResponse>builder().result(userService.getUserById(id)).build();
}

@GetMapping("/myinfo")
ApiResponse<UserResponse> getMyInfo() {
	return ApiResponse.<UserResponse>builder()
		.result(userService.getMyInfo(SecurityContextHolder.getContext().getAuthentication()))
		.build();
}

@PutMapping("/{numbered}")
ApiResponse<UserResponse> updateUser(
	@PathVariable String numbered, @RequestBody @Valid UserRequest request) {
	return ApiResponse.<UserResponse>builder()
		.result(userService.updateUser(numbered, request))
		.build();
}

@DeleteMapping("/{id}")
public void deleteUser(@PathVariable String id) {
	userService.deleteUser(id);
}
}

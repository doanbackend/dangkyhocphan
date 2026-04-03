package space.emdon.dangkyhocphan.rbac.permission;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

@Slf4j
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionController {
final PermissionService permissionService;

@PostMapping
ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
	return ApiResponse.<PermissionResponse>builder()
		.result(permissionService.createPermission(request))
		.build();
}

@GetMapping
ApiResponse<List<PermissionResponse>> getAll(Pageable pageable) {
	return ApiResponse.<List<PermissionResponse>>builder()
		.result(permissionService.getAllPermission(pageable))
		.build();
}

@DeleteMapping("/{name}")
public ApiResponse<Void> delete(@PathVariable String name) {
	permissionService.deletePermission(name);
	return ApiResponse.<Void>builder().build();
}
}

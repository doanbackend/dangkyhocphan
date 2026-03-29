package space.emdon.dangkyhocphan.rbac.role;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

final RoleService roleService;

@PostMapping
ApiResponse<RoleResponse> createRoles(@RequestBody RoleRequest request) {
	return ApiResponse.<RoleResponse>builder().result(roleService.createRole(request)).build();
}

@GetMapping
ApiResponse<List<RoleResponse>> getAllRoles() {
	return ApiResponse.<List<RoleResponse>>builder().result(roleService.getAllRoles()).build();
}

@PutMapping("/{name}")
ApiResponse<RoleResponse> updateRoles(
	@PathVariable String name, @RequestBody @Valid RoleRequest request) {
	return ApiResponse.<RoleResponse>builder().result(roleService.updateRoles(request)).build();
}

@DeleteMapping("/{name}")
public void deleteRoles(@PathVariable Role name) {
	roleService.deleteRole(name);
}
}

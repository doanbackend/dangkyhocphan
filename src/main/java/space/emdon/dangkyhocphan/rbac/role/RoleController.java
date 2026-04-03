package space.emdon.dangkyhocphan.rbac.role;

import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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

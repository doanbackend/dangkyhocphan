package space.emdon.dangkyhocphan.rbac.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;
import java.util.List;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRoles(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder().result(roleService.createRole(request)).build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder().result(roleService.getAllRoles()).build();
    }
    @PutMapping("/{rolename}")
    ApiResponse<RoleResponse> updateRoles(@PathVariable String rolename, @RequestBody @Valid RoleRequest request) {
        return ApiResponse.<RoleResponse>builder().result(roleService.updateRoles(request)).build();
    }

    @DeleteMapping("/name")
    public void deleteRoles(@RequestBody Role role) {
        roleService.deleteRole(role);
    }
}
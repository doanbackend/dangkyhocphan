package space.emdon.dangkyhocphan.rbac.role;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.permission.PermissionRepository;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
RoleRepository roleRepository;
PermissionRepository permissionRepository;
RoleMapper roleMapper;

@PreAuthorize("hasRole('ADMIN')")
public RoleResponse createRole(RoleRequest request) {

	var role = roleMapper.toRole(request);
	var permissions = permissionRepository.findAllById(request.getPermissions());
	role.setPermissions(new HashSet<>(permissions));
	role = roleRepository.save(role);
	return roleMapper.toRoleResponse(role);
}

@PreAuthorize("hasRole('ADMIN')")
public List<RoleResponse> getAllRoles() {
	return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
}

@PreAuthorize("hasRole('ADMIN')")
public void deleteRole(Role roleFromRequest) {
	Role role =
		roleRepository
			.findById(roleFromRequest.getName())
			.orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

	roleRepository.delete(role);
}

@PreAuthorize("hasRole('ADMIN')")
public RoleResponse updateRoles(RoleRequest request) {
	Role role =
		roleRepository
			.findById(request.getName())
			.orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
	roleMapper.updateRole(role, request);
	if (request.getPermissions() != null) {
	var permissions = permissionRepository.findAllById(request.getPermissions());
	if (permissions.size() != request.getPermissions().size()) {
		throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
	}
	role.setPermissions(new HashSet<>(permissions));
	}
	return roleMapper.toRoleResponse(roleRepository.save(role));
}
}

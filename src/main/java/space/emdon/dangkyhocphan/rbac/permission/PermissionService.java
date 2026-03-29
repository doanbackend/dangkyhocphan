package space.emdon.dangkyhocphan.rbac.permission;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
PermissionRepository permissionRepository;
PermissionMapper permissionMapper;

@PreAuthorize("hasRole('ADMIN')")
public PermissionResponse create(PermissionRequest request) {
	Permission permission = permissionMapper.toPermission(request);
	permissionRepository.save(permission);
	return permissionMapper.toPermissionResponse(permission);
}

@PreAuthorize("hasRole('ADMIN')")
public List<PermissionResponse> getAll() {
	var permissions = permissionRepository.findAll();
	return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
}

@PreAuthorize("hasRole('ADMIN')")
public void delete(String permission) {
	permissionRepository.deleteById(permission);
}
}

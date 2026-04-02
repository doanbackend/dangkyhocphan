package space.emdon.dangkyhocphan.rbac.permission;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.role.RoleRepository;

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
    RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse createPermission(PermissionRequest request) {
        if (request.getName() != null && permissionRepository.existsById(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        
        Permission permission = permissionMapper.toPermission(request);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(savedPermission);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAllPermission() {
        return permissionRepository.findAll()
            .stream().map(permissionMapper::toPermissionResponse)
            .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletePermission(String name) {
        Permission permission = 
        permissionRepository
            .findById(name)
            .orElseThrow(
                () -> new AppException(ErrorCode.PERMISSION_NOT_EXIST));
        
        permissionRepository.deleteById(permission.getName());
    }
}

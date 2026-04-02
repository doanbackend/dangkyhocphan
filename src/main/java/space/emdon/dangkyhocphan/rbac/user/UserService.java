package space.emdon.dangkyhocphan.rbac.user;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.role.Role;
import space.emdon.dangkyhocphan.rbac.role.RoleRepository;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class UserService {
UserRepository userRepository;
RoleRepository roleRepository;
PasswordEncoder passwordEncoder;
UserMapper userMapper;

@PreAuthorize("hasAuthority('CREATE_USER')")
public UserResponse createUser(UserRequest request) {
	if (request.getPhone() != null && userRepository.existsByPhone(request.getPhone())) {
		throw new AppException(ErrorCode.PHONE_EXISTED);
	}

	String finalNumberId = generateNumbered(request.getNumbered());

	User user = userMapper.toUser(request);

	user.setNumbered(finalNumberId);
	user.setPassword(passwordEncoder.encode(request.getPassword()));

	Role role =
		roleRepository
			.findByName("STUDENT")
			.orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
	user.setRoles(Collections.singleton(role));

	return userMapper.toUserResponse(userRepository.save(user));
}
private String generateNumbered(String prefix) {
    while (true) {
        long randomPart = ThreadLocalRandom.current().nextLong(100_000_000L);
        String fullId = prefix + String.format("%08d", randomPart);

        if (!userRepository.existsByNumbered(fullId)) {
            return fullId;
        }
    }
}

@PreAuthorize("hasRole('ADMIN')")
public List<UserResponse> getAllUsers() {
	return userRepository.findAll().stream()
	.map(userMapper::toUserResponse)
	.toList();
}

@PreAuthorize("hasAuthority('READ_USER')")
public List<UserResponse> getUsers() {
	return userRepository.findUsers().stream()
	.map(userMapper::toUserResponse)
	.toList();
}

@PreAuthorize("hasAuthority('READ_STUDENT')")
public List<UserResponse> getStudents() {
	return userRepository.findStudents().stream()
	.map(userMapper::toUserResponse)
	.toList();
}

@PreAuthorize("hasAuthority('READ_STUDENT')")
public UserResponse getUserById(String id) {
	return userMapper.toUserResponse(
		userRepository.findById(id).orElseThrow(
			() -> new AppException(ErrorCode.USER_NOT_EXIST)));
}

public UserResponse getMyInfo(Authentication authentication) {

    if (authentication == null
        || !authentication.isAuthenticated()
        || "anonymousUser".equals(authentication.getPrincipal())) {
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
    String phone = authentication.getName();
    User user = userRepository
            .findByPhone(phone)
            .orElseThrow(
				() -> new AppException(ErrorCode.USER_NOT_EXIST));
    return userMapper.toUserResponse(user);
}

@PreAuthorize("hasAuthority('UPDATE_USER')")
public UserResponse updateUser(String numbered, UserRequest request) {
	if (request.getPhone() != null && request.getPhone().isBlank()) {
		throw new AppException(ErrorCode.PHONE_INVALID);
	}
	if (request.getPhone() != null) {
		Optional<User> existingUser = userRepository.findByPhone(request.getPhone());
		if (existingUser.isPresent() && !existingUser.get().getNumbered().equals(numbered)) {
			throw new AppException(ErrorCode.PHONE_EXISTED);
		}
	}

	User user =
		userRepository.findByNumbered(numbered).orElseThrow(
			() -> new AppException(ErrorCode.USER_NOT_EXIST));
	userMapper.updateUser(user, request);
	user.setPassword(passwordEncoder.encode(request.getPassword()));
	var roles = roleRepository.findAllById(request.getRoles());
	user.setRoles(new HashSet<>(roles));
	User updatedUser = userRepository.save(user);
	return userMapper.toUserResponse(updatedUser);
}

@PreAuthorize("hasAuthority('DELETE_USER')")
public void deleteUser(String id) {
	User user =
		userRepository.findById(id).orElseThrow(
			() -> new AppException(ErrorCode.USER_NOT_EXIST));

	if (user.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN"))) {
	throw new AppException(ErrorCode.CANNOT_DELETE_ADMIN);
	}
	userRepository.delete(user);
}
}

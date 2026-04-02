package space.emdon.dangkyhocphan.configuration;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.role.Role;
import space.emdon.dangkyhocphan.rbac.role.RoleRepository;
import space.emdon.dangkyhocphan.rbac.user.User;
import space.emdon.dangkyhocphan.rbac.user.UserRepository;

@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ApplicationInitConfig {

final PasswordEncoder passwordEncoder;
final UserRepository userRepository;
final RoleRepository roleRepository;

@Value("${spring.admin.name}")
private String ADMIN_NAME;

@Value("${spring.admin.password}")
private String ADMIN_PASSWORD;

@Value("${spring.admin.phone}")
private String ADMIN_PHONE;

@Value("${spring.admin.numbered}")
private String ADMIN_NUMBERED;

@Bean
@Transactional
@ConditionalOnProperty(
	prefix = "spring",
	value = "datasource.driver-class-name",
	havingValue = "org.mariadb.jdbc.Driver",
	matchIfMissing = true)
public ApplicationRunner applicationRunner() {
	return args -> {
	if (roleRepository.findByName("ADMIN").isEmpty()) {
		Role adminRole = Role.builder().name("ADMIN").description("Administrator role").build();

		roleRepository.save(adminRole);
		log.info("Admin role created");
	}

	if (userRepository.findByPhone(ADMIN_PHONE).isEmpty()) {

		Role adminRole =
			roleRepository
				.findByName("ADMIN")
				.orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

		Set<Role> roles = new HashSet<>();
		roles.add(adminRole);

		User user =
			User.builder()
				.name(ADMIN_NAME)
				.password(passwordEncoder.encode(ADMIN_PASSWORD))
				.phone(ADMIN_PHONE)
				.numbered(ADMIN_NUMBERED)
				.dob(LocalDate.of(2003, 2, 28))
				.roles(roles)
				.build();

		userRepository.save(user);
		log.info("Admin user created");
	}
	};
}
}

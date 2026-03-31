package space.emdon.dangkyhocphan.ServiceTest;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.rbac.role.Role;
import space.emdon.dangkyhocphan.rbac.role.RoleRepository;
import space.emdon.dangkyhocphan.rbac.role.RoleResponse;
import space.emdon.dangkyhocphan.rbac.user.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserRepository userRepository;
    User user;
    UserRequest userRequest;
    UserResponse userResponse;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    RoleRepository roleRepository;
    @MockBean
    UserMapper userMapper;

    @BeforeEach
    void initData(){
        Role role = Role.builder()
                .name("STUDENT")
                .description("Student")
                .build();
        RoleResponse roleResponse = RoleResponse.builder()
                .name("STUDENT")
                .description("Student")
                .build();
        LocalDate dob = LocalDate.of(2000, 1, 1);
        userRequest = UserRequest.builder()
                .name("TestUser")
                .numbered("21")
                .email("TestUser@gmail.com")
                .password("12345678")
                .phone("0987654321")
                .dob(dob)
                .roles(Set.of("STUDENT"))
                .build();
        userResponse = UserResponse.builder()
                .id("100")
                .name("TestUser")
                .numbered("21")
                .email("TestUser@gmail.com")
                .phone("0987654321")
                .dob(dob)
                .roles(Set.of(roleResponse))
                .build();
        user = User.builder()
                .id("100")
                .name("TestUser")
                .numbered("21")
                .email("TestUser@gmail.com")
                .phone("0987654321")
                .dob(dob)
                .roles(Set.of(role))
                .build();
    }
    @Test
    @WithMockUser(authorities = "CREATE_USER")
    void createUserService_invalidRequest_success() throws Exception {
        when(userRepository.existsByName(anyString())).thenReturn(false);
        Role role = Role.builder().name("STUDENT").build();
        when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
        when(userMapper.toUser(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toUserResponse(any())).thenReturn(userResponse);
        var response = userService.createUser(userRequest);
        assertThat(response.getId()).isEqualTo("100");
        assertThat(response.getName()).isEqualTo("TestUser");
    }
    @Test
    @WithMockUser(authorities = "CREATE_USER")
    void createUserService_usernameException_fail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        var exception = assertThrows(AppException.class,
            ()->userService.createUser(userRequest));
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }
    @Test
    @WithMockUser(authorities = "UPDATE_USER")
    void updateUserService_validRequest_success() throws Exception {
        String userNumbered = "21";
        user.setNumbered("22");
        when(userRepository.findByNumbered(anyString()))
                .thenReturn(java.util.Optional.of(user));
        when(roleRepository.findAllById(any())).thenReturn(List.of(new Role()));
        when(userMapper.toUserResponse(any())).thenReturn(userResponse);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_pass");
        when(userRepository.save(any())).thenReturn(user);
        var response = userService.updateUser(userNumbered, userRequest);
        assertThat(response.getNumbered()).isEqualTo("22");
        assertThat(response.getName()).isEqualTo("TestUser");
    }
    @Test
    @WithMockUser(authorities = "UPDATE_USER")
    void updateUserService_usernameException_fail() {
        String userNumbered = "21";
        userRequest.setEmail("");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        var exception = assertThrows(AppException.class,
                ()->userService.updateUser(userNumbered, userRequest));
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
    }



    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_success() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toUserResponse(any())).thenReturn(userResponse);
        var response = userService.getAllUsers();
        assertThat(response).isNotEmpty();
        assertThat(response.get(0).getName()).isEqualTo("TestUser");
    }
    @Test
    @WithMockUser(authorities = "READ_USER")
    void getUsers_success() {
        when(userRepository.findUsers()).thenReturn(List.of(user));
        when(userMapper.toUserResponse(any())).thenReturn(userResponse);
        var response = userService.getUsers();
        assertThat(response).hasSize(1);
    }
    @Test
    @WithMockUser(authorities = "CREATE_USER")
    void createUser_numberedCollision_retrySuccess() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName("STUDENT")).thenReturn(Optional.of(new Role()));
        when(userMapper.toUser(any())).thenReturn(user);
        when(userMapper.toUserResponse(any())).thenReturn(userResponse);
        when(userRepository.existsByNumbered(anyString())).thenReturn(true, false);
        when(userRepository.save(any())).thenReturn(user);
        userService.createUser(userRequest);
        Mockito.verify(userRepository, Mockito.atLeast(2)).existsByNumbered(anyString());
    }
    @Test
    @WithMockUser(authorities = "UPDATE_USER")
    void updateUserService_userNotFound_fail() {
        String userNumbered = "99";
        when(userRepository.findByNumbered(userNumbered)).thenReturn(Optional.empty());
        var exception = assertThrows(AppException.class,
                () -> userService.updateUser(userNumbered, userRequest));
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }
    @Test
    void getMyInfo_userDeleted_fail() {
        org.springframework.security.core.Authentication auth = Mockito.mock(org.springframework.security.core.Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getName()).thenReturn("deleted@gmail.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(AppException.class, () -> userService.getMyInfo(auth));
    }

    @Test
    @WithMockUser(authorities = "READ_STUDENT")
    void getStudents_success() {
        when(userRepository.findStudents()).thenReturn(List.of(user));
        when(userMapper.toUserResponse(any())).thenReturn(userResponse);
        var response = userService.getStudents();
        assertThat(response).hasSize(1);
    }
    @Test
    @WithMockUser(authorities = "READ_STUDENT")
    void getUserById_validId_success() {
        String id = "100";
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
        when(userMapper.toUserResponse(any())).thenReturn(userResponse);
        var response = userService.getUserById(id);
        assertThat(response.getId()).isEqualTo("100");
    }
    @Test
    @WithMockUser(authorities = "READ_STUDENT")
    void getUserById_invalidId_fail() {
        String id = "invalid_id";
        when(userRepository.findById(id)).thenReturn(java.util.Optional.empty());
        var exception = assertThrows(AppException.class, () -> userService.getUserById(id));
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }
    @Test
    void getMyInfo_success() {
        org.springframework.security.core.Authentication auth = Mockito.mock(org.springframework.security.core.Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getName()).thenReturn("TestUser@gmail.com");
        when(auth.getPrincipal()).thenReturn("notAnonymous");
        when(userRepository.findByEmail(anyString())).thenReturn(java.util.Optional.of(user));
        when(userMapper.toUserResponse(any())).thenReturn(userResponse);
        var response = userService.getMyInfo(auth);
        assertThat(response.getEmail()).isEqualTo("TestUser@gmail.com");
    }

    @Test
    void getMyInfo_unauthenticated_fail() {
        var exception = assertThrows(AppException.class, () -> userService.getMyInfo(null));
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1010); // Giả sử mã của UNAUTHENTICATED
    }

    @Test
    @WithMockUser(authorities = "DELETE_USER")
    void deleteUser_success() {
        String userId = "100";
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    @WithMockUser(authorities = "DELETE_USER")
    void deleteUser_userNotFound_fail() {
        String userId = "non_existent_id";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        var exception = assertThrows(RuntimeException.class,
                () -> userService.deleteUser(userId));

        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

    @Test
    @WithMockUser(authorities = "DELETE_USER")
    void deleteUser_isAdmin_fail() {
        String userId = "admin_id";
        Role adminRole = Role.builder().name("ADMIN").build();
        User adminUser = User.builder()
                .id(userId)
                .roles(Set.of(adminRole))
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));

        var exception = assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> userService.deleteUser(userId));

        assertThat(exception.getMessage()).isEqualTo("Cannot delete ADMIN user");
        Mockito.verify(userRepository, Mockito.never()).delete(any());
    }
}

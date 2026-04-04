package space.emdon.dangkyhocphan.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import space.emdon.dangkyhocphan.rbac.role.RoleResponse;
import space.emdon.dangkyhocphan.rbac.user.UserRequest;
import space.emdon.dangkyhocphan.rbac.user.UserResponse;
import space.emdon.dangkyhocphan.rbac.user.UserService;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserControllerTest {
@Autowired MockMvc mockMvc;
@MockBean UserService userService;
@Autowired ObjectMapper objectMapper;

UserRequest userRequest;
UserResponse userResponse;

@BeforeEach
void initData() {
	RoleResponse roleResponse =
		RoleResponse.builder().name("STUDENT").description("Student").build();
	LocalDate dob = LocalDate.of(2000, 1, 1);
	userRequest =
		UserRequest.builder()
			.name("TestUser")
			.numbered("21")
			.password("12345678")
			.phone("0987654321")
			.dob(dob)
			.build();
	userResponse =
		UserResponse.builder()
			.id("100")
			.name("TestUser")
			.numbered("21")
			.phone("0987654321")
			.dob(dob)
			.roles(Set.of(roleResponse))
			.build();
}

@Test
@WithMockUser
void createUserController_invalidRequest_success() throws Exception {
	String content = objectMapper.writeValueAsString(userRequest);
	when(userService.createUser(any())).thenReturn(userResponse);
	mockMvc
		.perform(
			MockMvcRequestBuilders.post("/users")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value("100"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value("TestUser"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.numbered").value("21"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.phone").value("0987654321"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.dob").value("2000-01-01"))
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.result.roles[*].name", Matchers.hasItem("STUDENT")));
}

@Test
@WithMockUser
void createUserController_nameInvalid_fail() throws Exception {
	userRequest.setName("4VKT");
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.registerModule(new JavaTimeModule());
	String content = objectMapper.writeValueAsString(userRequest);
	mockMvc
		.perform(
			MockMvcRequestBuilders.post("/users")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1104));
}

@Test
@WithMockUser
void createUserController_numberedInvalid_fail() throws Exception {
	userRequest.setNumbered("05");
	String content = objectMapper.writeValueAsString(userRequest);
	mockMvc
		.perform(
			MockMvcRequestBuilders.post("/users")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
}

@Test
@WithMockUser
void createUserController_dobInvalid_fail() throws Exception {
	userRequest.setDob(LocalDate.of(2020, 1, 1));
	String content = objectMapper.writeValueAsString(userRequest);
	mockMvc
		.perform(
			MockMvcRequestBuilders.post("/users")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
}

@Test
@WithMockUser
void getAllUsers_controller_success() throws Exception {
	Page<UserResponse> page = new PageImpl<>(List.of(userResponse));
	when(userService.getAllUsers(any(Pageable.class))).thenReturn(page);
	mockMvc
		.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.content[*].name").value("TestUser"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.content[*].id").value("100"));
}

@Test
@WithMockUser
void getUsers_controller_success() throws Exception {
	Page<UserResponse> page = new PageImpl<>(List.of(userResponse));
	when(userService.getUsers(any(Pageable.class))).thenReturn(page);
	mockMvc
		.perform(
			MockMvcRequestBuilders.get("/users/normal").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.content").isArray());
}

@Test
@WithMockUser
void getUserById_controller_success() throws Exception {
	String userId = "100";
	when(userService.getUserById(userId)).thenReturn(userResponse);
	mockMvc
		.perform(
			MockMvcRequestBuilders.get("/users/{id}", userId)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value("100"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.phone").value("0987654321"));
}

@Test
@WithMockUser
void getMyInfo_controller_success() throws Exception {
	when(userService.getMyInfo(any())).thenReturn(userResponse);
	mockMvc
		.perform(
			MockMvcRequestBuilders.get("/users/myinfo").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.phone").value("0987654321"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value("TestUser"));
}

@Test
@WithMockUser
void getStudents_controller_success() throws Exception {
	Page<UserResponse> page = new PageImpl<>(List.of(userResponse));
	when(userService.getStudents(any(Pageable.class))).thenReturn(page);
	mockMvc
		.perform(
			MockMvcRequestBuilders.get("/users/students").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.content").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.content[0].name").value("TestUser"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.content[0].phone").value("0987654321"));
}

@Test
@WithMockUser
void updateUser_controller_success() throws Exception {
	String userId = "21";
	when(userService.updateUser(eq(userId), any())).thenReturn(userResponse);
	String content = objectMapper.writeValueAsString(userRequest);
	mockMvc
		.perform(
			MockMvcRequestBuilders.put("/users/{id}", userId)
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0));
}

@Test
@WithMockUser
void deleteUser_controller_success() throws Exception {
	String userId = "100";
	Mockito.doNothing().when(userService).deleteUser(userId);
	mockMvc
		.perform(MockMvcRequestBuilders.delete("/users/{id}", userId).with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	Mockito.verify(userService, Mockito.times(1)).deleteUser(userId);
}
}

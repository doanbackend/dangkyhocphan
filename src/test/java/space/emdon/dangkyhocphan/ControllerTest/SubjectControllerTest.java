package space.emdon.dangkyhocphan.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import space.emdon.dangkyhocphan.coreeducations.subject.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource("/test.properties")
public class SubjectControllerTest {
@Autowired MockMvc mockMvc;
@Autowired ObjectMapper objectMapper;
@Autowired SubjectMapper subjectMapper;
@MockBean SubjectService subjectService;

SubjectRequest subjectRequest;
SubjectResponse subjectResponse;

@BeforeEach
void initData() {
	subjectRequest = SubjectRequest.builder().code("KTH101").name("SQL").credits(4).build();
	subjectResponse =
		SubjectResponse.builder().id(101L).code("KTH101").name("SQL").credits(4).build();
}

@Test
@WithMockUser
void createController_invalidRequest_success() throws Exception {
	String content = objectMapper.writeValueAsString(subjectRequest);
	when(subjectService.createSubject(any())).thenReturn(subjectResponse);

	mockMvc
		.perform(
			MockMvcRequestBuilders.post("/subjects")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$code").value(0))
		.andExpect(MockMvcResultMatchers.jsonPath("$result.id").value(101L))
		.andExpect(MockMvcResultMatchers.jsonPath("$result.code").value("KTH101"))
		.andExpect(MockMvcResultMatchers.jsonPath("$result.name").value("SQL"))
		.andExpect(MockMvcResultMatchers.jsonPath("$result.credits").value(4));
}

@Test
@WithMockUser
void createSubjectController_codeInvalid_fail() throws Exception {
	subjectRequest.setCode("");
	String content = objectMapper.writeValueAsString(subjectRequest);
	mockMvc
		.perform(
			MockMvcRequestBuilders.post("/subjects")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("$code").value(1021));
}

@Test
@WithMockUser
void createSubjectController_nameInvalid_fail() throws Exception {
	subjectRequest.setName("");
	String content = objectMapper.writeValueAsString(subjectRequest);
	mockMvc
		.perform(
			MockMvcRequestBuilders.post("/subjects")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("$code").value(1022));
}

@Test
@WithMockUser
void createSubjectController_creditsInvalid_fail() throws Exception {
	subjectRequest.setCredits(0);
	String content = objectMapper.writeValueAsString(subjectRequest);
	mockMvc
		.perform(
			MockMvcRequestBuilders.post("/subjects")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("$code").value(1023));
}

@Test
@WithMockUser
void updateSubjectController_invalidRequest_success() throws Exception {
	String subjectCode = "KTH101";
	when(subjectService.updateSubject(eq(subjectCode), any())).thenReturn(subjectResponse);
	String content = objectMapper.writeValueAsString(subjectRequest);
	mockMvc
		.perform(
			MockMvcRequestBuilders.put("/subjects/{code}", subjectCode)
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.code").value("KTH101"));
}

@Test
@WithMockUser
void getAllSubjectController_invalidRequest_success() throws Exception {
	when(subjectService.getAllSubjects()).thenReturn(List.of(subjectResponse));
	mockMvc
		.perform(
			MockMvcRequestBuilders.get("/subjects")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result[*].code").value("KTH101"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result[*].name").value("SQL"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result[*].credits").value(4));
}

@Test
@WithMockUser
void getSubjectCodeController_invalidRequest_success() throws Exception {
	String subjectCode = "KTH101";
	when(subjectService.getSubjectById(any())).thenReturn(subjectResponse);
	mockMvc
		.perform(
			MockMvcRequestBuilders.get("/subjects/{code}", subjectCode)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.code").value("KTH101"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value("SQL"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result.credits").value(4));
}

@Test
@WithMockUser
void deleteSubjectController_invalidRequest_success() throws Exception {
	String subjectCode = "KTH101";
	Mockito.doNothing().when(subjectService).deleteSubject(subjectCode);
	mockMvc
		.perform(MockMvcRequestBuilders.delete("/subjects/{code}", subjectCode).with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	Mockito.verify(subjectService, Mockito.times(1)).deleteSubject(subjectCode);
}
}

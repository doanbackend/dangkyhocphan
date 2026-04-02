package space.emdon.dangkyhocphan.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import space.emdon.dangkyhocphan.coreeducations.sectionclass.*;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterMapper;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterRequest;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterResponse;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource("/test.properties")
public class SectionclassControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SectionclassMapper sectionclassMapper;
    @MockBean
    SectionclassService sectionclassService;
    SectionclassRequest sectionclassRequest;
    SectionclassResponse sectionclassResponse;


    @BeforeEach
    void initData(){

        sectionclassRequest = SectionclassRequest.builder()
                .subjectCode("KTH101")
                .semesterName("HK1")
                .maxStudents(100)
                .build();
        sectionclassResponse = SectionclassResponse.builder()
                .id("2")
                .subjectCode("KTH101")
                .subjectName("SQL")
                .lecturerNumbered("Test")
                .lecturerNumbered("2992")
                .semesterId("22")
                .semesterName("HK1")
                .maxStudents(100)
                .currentStudents(0)
                .build();

    }

    @Test
    @WithMockUser
    void createSectionClassController_invalidRequest_success() throws Exception{
        String content = objectMapper.writeValueAsString(sectionclassRequest);
        when(sectionclassService.createSectionclass(any()))

                .thenReturn(sectionclassResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/sectionclasses")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id")
                        .value("HK1_2025-2026"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.subjectCode")
                        .value("KTH101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.subjectName")
                        .value("SQL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.lecturerNumbered")
                        .value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.lecturerNumbered")
                        .value("2992"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.semesterId")
                        .value("22"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.semesterName")
                        .value("HK1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.maxStudents")
                        .value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.currentStudents")
                        .value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.registrationEndDate")
                        .value("2025-08-09T23:59:00"));
    }


    @Test
    @WithMockUser
    void updateSectionClassController_invalidRequest_success() throws Exception{
        String sectionClassName = "B102";
        when(sectionclassService.updateSectionclass(any(), any())).thenReturn(sectionclassResponse);
        String content = objectMapper.writeValueAsString(sectionclassRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/sectionclasses/{name}", sectionClassName)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name")
                        .value("HK1_2025-2026"));
    }

    @Test
    @WithMockUser
    void getAllSectionClassController_invalidRequest_success() throws Exception{
        when(sectionclassService.getAllSectionclasses()).thenReturn(List.of(sectionclassResponse));
        mockMvc.perform(MockMvcRequestBuilders.get("/sectionclasses")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[*].name")
                        .value("HK1_2025-2026"));

    }

    @Test
    @WithMockUser
    void deleteSectionClassController_invalidRequest_success()throws Exception {
        String sectionclassName = "B102";
        Mockito.doNothing().when(sectionclassService).deleteSectionclass(sectionclassName);
        mockMvc.perform(MockMvcRequestBuilders.delete("/sectionclasses/{name}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(sectionclassService,
                        Mockito.times(1))
                .deleteSectionlass(sectionclassName);

    }

}
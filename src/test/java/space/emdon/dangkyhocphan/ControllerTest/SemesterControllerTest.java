package space.emdon.dangkyhocphan.ControllerTest;

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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterMapper;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterRequest;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterResponse;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource("/test.properties")
public class SemesterControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SemesterMapper semesterMapper;
    @MockBean
    SemesterService semesterService;
    SemesterRequest semesterRequest;
    SemesterResponse semesterResponse;

    @BeforeEach
    void initData(){
        LocalDate startDate = LocalDate.of(2025, 8 , 10);
        LocalDate endDate = LocalDate.of(2025, 12, 15);
        LocalDateTime registrationStartDate = LocalDateTime.of(2025, 8, 1, 0, 0);
        LocalDateTime registrationEndDate = LocalDateTime.of(2025, 8, 9, 23, 59);
        semesterRequest = SemesterRequest.builder()
                .name("HK1_2025-2026")
                .startDate(startDate)
                .endDate(endDate)
                .registrationStartDate(registrationStartDate)
                .registrationEndDate(registrationEndDate)
                .build();
        semesterResponse = SemesterResponse.builder()
                .name("HK1_2025-2026")
                .startDate(startDate)
                .endDate(endDate)
                .registrationStartDate(registrationStartDate)
                .registrationEndDate(registrationEndDate)
                .build();

    }

    @Test
    @WithMockUser
    void createSemesterController_invalidRequest_success() throws Exception{
        String content = objectMapper.writeValueAsString(semesterRequest);
        when(semesterService.createSemester(any()))

                .thenReturn(semesterResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/semesters")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name")
                        .value("HK1_2025-2026"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.startDate")
                        .value("2025-08-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.endDate")
                        .value("2025-12-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.registrationStartDate")
                        .value("2025-08-01T00:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.registrationEndDate")
                        .value("2025-08-09T23:59:00"));
    }

    @Test
    @WithMockUser
    void createSemesterController_nameInvalid_fail() throws Exception{
        semesterRequest.setName("");
        String content = objectMapper.writeValueAsString(semesterRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/semesters")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value(1031));

    }

    @Test
    @WithMockUser
    void createSemesterController_startDate_fail()throws Exception{
        semesterRequest.setStartDate(null);
        String content = objectMapper.writeValueAsString(semesterRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/semesters")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value("1032"));

    }
    @Test
    @WithMockUser
    void createSemesterController_endtDate_fail()throws Exception{
        semesterRequest.setEndDate(null);
        String content = objectMapper.writeValueAsString(semesterRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/semesters")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value("1033"));

    }

    @Test
    @WithMockUser
    void createSemesterController_regStartDate_fail()throws Exception{
        semesterRequest.setRegistrationStartDate(null);
        String content = objectMapper.writeValueAsString(semesterRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/semesters")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value("1033"));

    }

    @Test
    @WithMockUser
    void createSemesterController_regEndDate_fail()throws Exception{
        semesterRequest.setRegistrationEndDate(null);
        String content = objectMapper.writeValueAsString(semesterRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/semesters")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value("1034"));

    }

    @Test
    @WithMockUser
    void updateSemesterController_invalidRequest_success() throws Exception{
        String semesterName = "HK1_2025-2026";
        when(semesterService.updateSemester(any())).thenReturn(semesterResponse);
        String content = objectMapper.writeValueAsString(semesterRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/semesters/{name}", semesterName)
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
    void getAllSemesterController_invalidRequest_success() throws Exception{
        when(semesterService.getAllSemesters()).thenReturn(List.of(semesterResponse));
        mockMvc.perform(MockMvcRequestBuilders.get("/semesters")
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
    void deleteSemesterController_invalidRequest_success()throws Exception {
        String semesterName = "HK1";
        Mockito.doNothing().when(semesterService).deleteSemester(semesterName);
        mockMvc.perform(MockMvcRequestBuilders.delete("/semesters/{name}")
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(semesterService,
                Mockito.times(1))
                .deleteSemester(semesterName);

    }

}

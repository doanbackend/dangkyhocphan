package space.emdon.dangkyhocphan.coreeducations.semester;
import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/semesters")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SemesterController {


    SemesterService semesterService;

    @PostMapping
    ApiResponse<SemesterResponse> createSemester(@RequestBody SemesterRequest request) {
        return ApiResponse.<SemesterResponse>builder().result(semesterService.createSemester(request)).build();
    }

    @GetMapping
    ApiResponse<List<SemesterResponse>> getAllSemesters() {
        return ApiResponse.<List<SemesterResponse>>builder()
                .result(semesterService.getAllSemesters())
                .build();
    }
    
    @PutMapping("/{name}")
    ApiResponse<SemesterResponse> updateSemester(@PathVariable String name, @RequestBody SemesterRequest request) {
        return ApiResponse.<SemesterResponse>builder()
                .result(semesterService.updateSemester(request))
                .build();
    }

    @DeleteMapping("/{name}")
    public void deleteSemester(@PathVariable String name) {
        SemesterRequest request = new SemesterRequest();
        request.setName(name);
        semesterService.deleteSemester(request);
    }

}

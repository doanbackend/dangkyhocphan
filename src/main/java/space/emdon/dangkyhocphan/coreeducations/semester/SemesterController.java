package space.emdon.dangkyhocphan.coreeducations.semester;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

@Slf4j
@RestController
@RequestMapping("/semesters")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SemesterController {

final SemesterService semesterService;

@PostMapping
ApiResponse<SemesterResponse> createSemester(@RequestBody @Valid SemesterRequest request) {
	return ApiResponse.<SemesterResponse>builder()
		.result(semesterService.createSemester(request))
		.build();
}

@GetMapping
ApiResponse<List<SemesterResponse>> getAllSemesters() {
	return ApiResponse.<List<SemesterResponse>>builder()
		.result(semesterService.getAllSemesters())
		.build();
}

@PutMapping("/{name}")
ApiResponse<SemesterResponse> updateSemester(
	@PathVariable String name, @RequestBody @Valid SemesterRequest request) {
	return ApiResponse.<SemesterResponse>builder()
		.result(semesterService.updateSemester(request))
		.build();
}

@DeleteMapping("/{name}")
public void deleteSemester(@PathVariable String name) {
	semesterService.deleteSemester(name);
}
}
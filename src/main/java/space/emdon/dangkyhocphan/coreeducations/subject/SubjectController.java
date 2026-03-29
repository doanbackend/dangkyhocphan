package space.emdon.dangkyhocphan.coreeducations.subject;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

@Slf4j
@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SubjectController {
final SubjectService subjectService;

@PostMapping
ApiResponse<SubjectResponse> createSubject(@RequestBody SubjectRequest request) {
	return ApiResponse.<SubjectResponse>builder()
		.result(subjectService.createSubject(request))
		.build();
}

@GetMapping
ApiResponse<List<SubjectResponse>> getAll() {
	return ApiResponse.<List<SubjectResponse>>builder()
		.result(subjectService.getAllSubjects())
		.build();
}

@PutMapping("/{code}")
public ApiResponse<SubjectResponse> updateSubject(
	@PathVariable String code, @RequestBody @Valid SubjectRequest request) {
	return ApiResponse.<SubjectResponse>builder()
		.result(subjectService.updateSubject(code, request))
		.build();
}

@DeleteMapping("/{code}")
public void delete(@PathVariable String code) {
	subjectService.deleteSubject(code);
}
}

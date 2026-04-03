package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/sectionclasses")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SectionclassController {
final SectionclassService sectionclassService;

@PostMapping
ApiResponse<SectionclassResponse> createSectionclass(@RequestBody SectionclassRequest request) {
	return ApiResponse.<SectionclassResponse>builder()
		.result(sectionclassService.createSectionclass(request))
		.build();
}

@GetMapping
ApiResponse<Page<SectionclassResponse>> getAllSectionclasses(@PageableDefault(page = 0, size = 15, sort = "name", direction = Sort.Direction.DESC)Pageable pageable) {
	return ApiResponse.<Page<SectionclassResponse>>builder()
		.result(sectionclassService.getAllSectionclasses(pageable))
		.build();
}

@PutMapping("/{id}")
ApiResponse<SectionclassResponse> updateSectionclass(
	@PathVariable String id, @RequestBody @Valid SectionclassRequest request) {
	return ApiResponse.<SectionclassResponse>builder()
		.result(sectionclassService.updateSectionclass(id, request))
		.build();
}

@DeleteMapping("/{id}")
public void deleteSectionclass(@PathVariable String id) {
	sectionclassService.deleteSectionclassByName(id);
}
}

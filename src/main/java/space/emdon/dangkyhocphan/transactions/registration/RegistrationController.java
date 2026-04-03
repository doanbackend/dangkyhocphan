package space.emdon.dangkyhocphan.transactions.registration;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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
@RequiredArgsConstructor
@RequestMapping("/registration")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationController {
final RegistrationService registrationService;

@PostMapping
ApiResponse<RegistrationResponse> createRegistration(@RequestBody RegistrationRequest request) {
	RegistrationResponse response = registrationService.createRegistration(request);
	return ApiResponse.<RegistrationResponse>builder().result(response).build();
}

@GetMapping
ApiResponse<List<RegistrationResponse>> getAll(@PageableDefault(page = 0, size = 20, sort = "student", direction = Sort.Direction.DESC)Pageable pageable) {
	List<RegistrationResponse> response = registrationService.getAll(pageable);
	return ApiResponse.<List<RegistrationResponse>>builder().result(response).build();
}

@PutMapping("/{id}")
ApiResponse<RegistrationResponse> updateRegistration(
	@PathVariable String id, @RequestBody RegistrationRequest request) {
	RegistrationResponse response = registrationService.updateRegistration(id, request);
	return ApiResponse.<RegistrationResponse>builder().result(response).build();
}

@DeleteMapping("/{id}")
public void deleteRegistration(@PathVariable String id) {
	registrationService.deleteRegistration(id);
}
}

package space.emdon.dangkyhocphan.transactions.registration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationController {

    RegistrationService registrationService;

    @PostMapping
    ApiResponse<RegistrationResponse> createRegistration(@RequestBody RegistrationRequest request) {
        RegistrationResponse response = registrationService.createRegistration(request);
        return ApiResponse.<RegistrationResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping
    ApiResponse<List<RegistrationResponse>> getAll() {
        List<RegistrationResponse> response = registrationService.getAll();
        return ApiResponse.<List<RegistrationResponse>>builder()
                .result(response)
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<RegistrationResponse> updateRegistration(@PathVariable String id, @RequestBody RegistrationRequest request) {
        RegistrationResponse response = registrationService.updateRegistration(id, request);
        return ApiResponse.<RegistrationResponse>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteRegistration(@PathVariable String id) {
        registrationService.deleteRegistration(id);
    }


}

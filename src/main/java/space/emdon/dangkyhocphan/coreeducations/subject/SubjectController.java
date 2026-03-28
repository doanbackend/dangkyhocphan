package space.emdon.dangkyhocphan.coreeducations.subject;

import lombok.experimental.FieldDefaults;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;
import space.emdon.dangkyhocphan.rbac.role.RoleRequest;
import space.emdon.dangkyhocphan.rbac.role.RoleResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SubjectController {
    SubjectService subjectService;

    @PostMapping
    ApiResponse<SubjectResponse> createSubject(@RequestBody SubjectRequest request) {
        return ApiResponse.<SubjectResponse>builder().result(subjectService.createSubject(request)).build();
    }

    @GetMapping
    ApiResponse<List<SubjectResponse>> getAll() {
        return ApiResponse.<List<SubjectResponse>>builder().result(subjectService.getAllSubjects()).build();
    }
    @PutMapping("/{subjectcode}")
    public ApiResponse<SubjectResponse> updateSubject(@PathVariable String subjectcode, @RequestBody @Valid SubjectRequest request) {
        return ApiResponse.<SubjectResponse>builder().result(subjectService.updateSubject(subjectcode, request)).build();
    }

    @DeleteMapping("/name")
    public ApiResponse<Void> delete(@RequestBody Subject subject) {
        subjectService.deleteSubject(subject.getCode());
        return ApiResponse.<Void>builder().build(); 
    }
}

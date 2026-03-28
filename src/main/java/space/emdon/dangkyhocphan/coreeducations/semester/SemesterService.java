package space.emdon.dangkyhocphan.coreeducations.semester;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.role.RoleRequest;
import space.emdon.dangkyhocphan.rbac.role.RoleResponse;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SemesterService {
    SemesterRepository semesterRepository;
    SemesterMapper semesterMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public SemesterResponse createSemester(SemesterRequest request) {

        var semester = semesterMapper.toSemester(request);
        semester = semesterRepository.save(semester);
        return semesterMapper.toSemesterResponse(semester);
    }

    @PreAuthorize("hasAuthority('READ_SEMESTER')")
    public List<SemesterResponse> getAllSemesters() {
        return semesterRepository.findAll().stream().map(semesterMapper::toSemesterResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSemester(SemesterRequest request) {
        Semester semester =
                semesterRepository
                        .findByName(request.getName())
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        semesterRepository.delete(semester);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public SemesterResponse updateSemester(SemesterRequest request) {
        Semester semester = semesterRepository.findByName(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        semesterMapper.updateSemester(semester, request);
        return semesterMapper.toSemesterResponse(semesterRepository.save(semester));
    }

}

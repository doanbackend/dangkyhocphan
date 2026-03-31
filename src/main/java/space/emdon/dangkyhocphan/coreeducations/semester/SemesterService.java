package space.emdon.dangkyhocphan.coreeducations.semester;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;

@Slf4j
@Service
@Transactional
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
public void deleteSemester(String name) {
	Semester semester =
		semesterRepository
			.findByName(name)
			.orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND));
	semesterRepository.delete(semester);
}

@PreAuthorize("hasRole('ADMIN')")
public SemesterResponse updateSemester(SemesterRequest request) {
	Semester semester =
		semesterRepository
			.findByName(request.getName())
			.orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
	semesterMapper.updateSemester(semester, request);
	return semesterMapper.toSemesterResponse(semesterRepository.save(semester));
}
}

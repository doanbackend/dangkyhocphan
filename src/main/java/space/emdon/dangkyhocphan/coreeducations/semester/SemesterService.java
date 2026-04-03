package space.emdon.dangkyhocphan.coreeducations.semester;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.SectionclassRepository;
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
SectionclassRepository sectionclassRepository;

@PreAuthorize("hasAuthority('CREATE_SEMESTER')")
public SemesterResponse createSemester(SemesterRequest request) {
	if (semesterRepository.findByName(request.getName()).isPresent()) {
	throw new AppException(ErrorCode.SEMESTER_EXISTS);
	}

	if (request.getStartDate().isAfter(request.getEndDate())) {
	throw new AppException(ErrorCode.INVALID_DATE_RANGE);
	}

	if (request.getRegistrationStartDate().isAfter(request.getRegistrationEndDate())) {
	throw new AppException(ErrorCode.INVALID_REGISTRATION_DATE_RANGE);
	}

	var semester = semesterMapper.toSemester(request);
	semester = semesterRepository.save(semester);
	return semesterMapper.toSemesterResponse(semester);
}

@PreAuthorize("hasAuthority('READ_SEMESTER')")
public List<SemesterResponse> getAllSemesters() {
	return semesterRepository.findAll().stream()
	.map(semesterMapper::toSemesterResponse)
			.toList();
}

@PreAuthorize("hasAuthority('UPDATE_SEMESTER')")
public void deleteSemester(String name) {
	Semester semester =
		semesterRepository
			.findByName(name)
			.orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_EXIST));

	if (sectionclassRepository.existsBySemesterName(name)) {
	throw new AppException(ErrorCode.SEMESTER_IN_USE);
	}

	semesterRepository.delete(semester);
}

@PreAuthorize("hasAuthority('DELETE_SEMESTER')")
public SemesterResponse updateSemester(SemesterRequest request) {
	Semester semester =
		semesterRepository
			.findByName(request.getName())
			.orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_EXIST));
	semesterMapper.updateSemester(semester, request);
	return semesterMapper.toSemesterResponse(semesterRepository.save(semester));
}
}

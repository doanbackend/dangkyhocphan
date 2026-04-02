package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.emdon.dangkyhocphan.coreeducations.semester.Semester;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterRepository;
import space.emdon.dangkyhocphan.coreeducations.subject.Subject;
import space.emdon.dangkyhocphan.coreeducations.subject.SubjectRepository;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.user.User;
import space.emdon.dangkyhocphan.rbac.user.UserRepository;
import space.emdon.dangkyhocphan.transactions.registration.RegistrationRepository;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SectionclassService {

SectionclassRepository sectionclassRepository;
SectionclassMapper sectionclassMapper;
SubjectRepository subjectRepository;
UserRepository userRepository;
SemesterRepository semesterRepository;
RegistrationRepository registrationRepository;

@PreAuthorize("hasAuthority('CREATE_SECTIONCLASS')")
public SectionclassResponse createSectionclass(SectionclassRequest request) {
	Sectionclass sectionclass = sectionclassMapper.toSectionClass(request);
	if (request.getSubjectCode() != null) {
	Subject subject =
		subjectRepository
			.findByCode(request.getSubjectCode())
			.orElseThrow(
				() ->
					new AppException(ErrorCode.SUBJECT_NOT_FOUND));
	sectionclass.setSubject(subject);
	}
	if (request.getLecturerNumbered() != null) {
	User instructor =
		userRepository
			.findByNumbered(request.getLecturerNumbered())
			.orElseThrow(
				() ->
					new AppException(ErrorCode.USER_NOT_FOUND));
	sectionclass.setInstructor(instructor);
	}
	if (request.getSemesterName() != null) {
	Semester semester =
		semesterRepository
			.findByName(request.getSemesterName())
			.orElseThrow(
				() ->
					new AppException(ErrorCode.SEMESTER_NOT_FOUND));
	sectionclass.setSemester(semester);
	}

	sectionclass = sectionclassRepository.save(sectionclass);
	return sectionclassMapper.toSectionClassResponse(sectionclass);
}

@PreAuthorize("hasAuthority('READ_SECTIONCLASS')")
public List<SectionclassResponse> getAllSectionclasses() {
	return sectionclassRepository.findAll().stream()
		.map(sectionclassMapper::toSectionClassResponse)
		.collect(Collectors.toList());
}

@PreAuthorize("hasAuthority('READ_SECTIONCLASS')")
public SectionclassResponse getSectionclassByName(String name) {
	return sectionclassMapper.toSectionClassResponse(
		sectionclassRepository.findByName(name).orElseThrow(
			() -> new AppException(ErrorCode.SECTIONCLASS_NOT_EXIST)));
}

@PreAuthorize("hasAuthority('UPDATE_SECTIONCLASS')")
public SectionclassResponse updateSectionclass(String name, SectionclassRequest request) {
	
	Sectionclass sectionclass = 
	sectionclassRepository.findByName(name).orElseThrow(
		() -> new AppException(ErrorCode.SECTIONCLASS_NOT_EXIST));
	sectionclassMapper.updateSectionClass(sectionclass, request);
	if (request.getSubjectCode() != null) {
		Subject subject =
			subjectRepository
				.findByCode(request.getSubjectCode())
				.orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));
		sectionclass.setSubject(subject);
	}
	if (request.getLecturerNumbered() != null) {
		User instructor =
			userRepository
				.findByNumbered(request.getLecturerNumbered())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
		sectionclass.setInstructor(instructor);
	}
	if (request.getSemesterName() != null) {
		Semester semester =
			semesterRepository
				.findByName(request.getSemesterName())
				.orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND));
		sectionclass.setSemester(semester);
	}

	sectionclass = sectionclassRepository.save(sectionclass);
	return sectionclassMapper.toSectionClassResponse(sectionclass);
}

@PreAuthorize("hasAuthority('DELETE_SECTIONCLASS')")
public void deleteSectionclassByName(String name) {
	Sectionclass sectionclass = sectionclassRepository.findByName(name).orElseThrow(
		() -> new AppException(ErrorCode.SECTIONCLASS_NOT_EXIST));
	
	// Check foreign key constraint - Sectionclass is used by Registration
	if (registrationRepository.existsBySectionClassId(sectionclass.getName())) {
		throw new AppException(ErrorCode.SECTIONCLASS_IN_USE);
	}
	
	sectionclassRepository.deleteByName(sectionclass.getName());
}


}


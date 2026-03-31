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
import space.emdon.dangkyhocphan.rbac.user.User;
import space.emdon.dangkyhocphan.rbac.user.UserRepository;

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

@PreAuthorize("hasRole('ADMIN')")
public SectionclassResponse createSectionclass(SectionclassRequest request) {
	Sectionclass sectionclass = sectionclassMapper.toSectionClass(request);

	// Fetch and set related entities
	if (request.getSubjectCode() != null) {
	Subject subject =
		subjectRepository
			.findByCode(request.getSubjectCode())
			.orElseThrow(
				() ->
					new RuntimeException(
						"Subject not found with code: " + request.getSubjectCode()));
	sectionclass.setSubject(subject);
	}
	if (request.getLecturerNumbered() != null) {
	User instructor =
		userRepository
			.findByNumbered(request.getLecturerNumbered())
			.orElseThrow(
				() ->
					new RuntimeException(
						"Instructor not found with numberId: " + request.getLecturerNumbered()));
	sectionclass.setInstructor(instructor);
	}
	if (request.getSemesterName() != null) {
	Semester semester =
		semesterRepository
			.findByName(request.getSemesterName())
			.orElseThrow(
				() ->
					new RuntimeException(
						"Semester not found with name: " + request.getSemesterName()));
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

@PreAuthorize("hasRole('ADMIN')")
public SectionclassResponse getSectionclass(String id) {
	try {
	Long sectionclassId = Long.valueOf(id);
	Sectionclass sectionclass =
		sectionclassRepository
			.findById(sectionclassId)
			.orElseThrow(() -> new RuntimeException("Sectionclass not found with id: " + id));
	return sectionclassMapper.toSectionClassResponse(sectionclass);
	} catch (NumberFormatException e) {
	throw new RuntimeException(
		"Invalid sectionclass ID format: " + id + ". Expected a numeric value.");
	}
}

@PreAuthorize("hasRole('ADMIN')")
public SectionclassResponse updateSectionclass(String id, SectionclassRequest request) {
	try {
	Long sectionclassId = Long.valueOf(id);
	Sectionclass sectionclass =
		sectionclassRepository
			.findById(sectionclassId)
			.orElseThrow(() -> new RuntimeException("Sectionclass not found with id: " + id));
	sectionclassMapper.updateSectionClass(sectionclass, request);

	// Fetch and set related entities
	if (request.getSubjectCode() != null) {
		Subject subject =
			subjectRepository
				.findByCode(request.getSubjectCode())
				.orElseThrow(
					() ->
						new RuntimeException(
							"Subject not found with code: " + request.getSubjectCode()));
		sectionclass.setSubject(subject);
	}
	if (request.getLecturerNumbered() != null) {
		User instructor =
			userRepository
				.findByNumbered(request.getLecturerNumbered())
				.orElseThrow(
					() ->
						new RuntimeException(
							"Instructor not found with numberId: "
								+ request.getLecturerNumbered()));
		sectionclass.setInstructor(instructor);
	}
	if (request.getSemesterName() != null) {
		Semester semester =
			semesterRepository
				.findByName(request.getSemesterName())
				.orElseThrow(
					() ->
						new RuntimeException(
							"Semester not found with name: " + request.getSemesterName()));
		sectionclass.setSemester(semester);
	}

	sectionclass = sectionclassRepository.save(sectionclass);
	return sectionclassMapper.toSectionClassResponse(sectionclass);
	} catch (NumberFormatException e) {
	throw new RuntimeException(
		"Invalid sectionclass ID format: " + id + ". Expected a numeric value.");
	}
}

@PreAuthorize("hasRole('ADMIN')")
public void deleteSectionclassById(String id) {
	try {
	sectionclassRepository.deleteById(Long.valueOf(id));
	} catch (NumberFormatException e) {
	throw new RuntimeException(
		"Invalid sectionclass ID format: " + id + ". Expected a numeric value.");
	}
}
}

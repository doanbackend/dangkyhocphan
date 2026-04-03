package space.emdon.dangkyhocphan.coreeducations.subject;

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

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SubjectService {

SubjectRepository subjectRepository;
SubjectMapper subjectMapper;
SectionclassRepository sectionclassRepository;

@PreAuthorize("hasAuthority('CREATE_SUBJECT')")
public SubjectResponse createSubject(SubjectRequest request) {
	if (subjectRepository.existsByCodeAndName(request.getCode(), request.getName())) {
	throw new AppException(ErrorCode.SUBJECT_ALREADY_EXISTS);
	}
	if (subjectRepository.existsByCode(request.getCode())) {
	throw new AppException(ErrorCode.SUBJECT_CODE_EXISTED);
	}
	if (subjectRepository.existsByName(request.getName())) {
	throw new AppException(ErrorCode.SUBJECT_NAME_EXISTED);
	}
	Subject subject = subjectMapper.toSubject(request);
	Subject savedSubject = subjectRepository.save(subject);
	return subjectMapper.toSubjectResponse(savedSubject);
}

@PreAuthorize("hasAuthority('READ_SUBJECT')")
public Page<SubjectResponse> getAllSubjects(Pageable pageable) {
	return subjectRepository.findAll(pageable)
	.map(subjectMapper::toSubjectResponse);
}

@PreAuthorize("hasAuthority('READ_SUBJECT')")
public SubjectResponse getSubjectById(String code) {
	return subjectMapper.toSubjectResponse(
		subjectRepository
			.findByCode(code)
			.orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTS)));
}

@PreAuthorize("hasAuthority('UPDATE_SUBJECT')")
public SubjectResponse updateSubject(String code, SubjectRequest request) {
	Subject subject =
		subjectRepository
			.findByCode(code)
			.orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTS));

	if (!code.equals(request.getCode()) && subjectRepository.existsByCode(request.getCode())) {
	throw new AppException(ErrorCode.SUBJECT_CODE_EXISTED);
	}

	if (!subject.getName().equals(request.getName())
		&& subjectRepository.existsByName(request.getName())) {
	throw new AppException(ErrorCode.SUBJECT_NAME_EXISTED);
	}

	subjectMapper.updateSubject(subject, request);
	subject = subjectRepository.save(subject);
	return subjectMapper.toSubjectResponse(subject);
}

@PreAuthorize("hasAuthority('DELETE_SUBJECT')")
public void deleteSubject(String code) {
	Subject subject =
		subjectRepository
			.findByCode(code)
			.orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_EXISTS));

	if (sectionclassRepository.existsBySubjectCode(code)) {
	throw new AppException(ErrorCode.SUBJECT_IN_USE);
	}

	subjectRepository.deleteByCode(subject.getCode());
}
}

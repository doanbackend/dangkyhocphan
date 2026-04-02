package space.emdon.dangkyhocphan.transactions.registration;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.SectionclassRepository;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.user.User;
import space.emdon.dangkyhocphan.rbac.user.UserRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class RegistrationService {

RegistrationRepository registrationRepository;
RegistrationMapper registrationMapper;
UserRepository userRepository;
SectionclassRepository sectionclassRepository;

@PreAuthorize("hasAuthority('CREATE_REGISTRATION')")
public RegistrationResponse createRegistration(RegistrationRequest request) {
	Registration registration = registrationMapper.toRegistration(request);
	if (request.getStudentNumberId() != null) {
	User student =
		userRepository
			.findByNumbered(request.getStudentNumberId())
			.orElseThrow(
				() -> new AppException(ErrorCode.USER_NOT_FOUND));
	registration.setStudent(student);
	}
	if (request.getSectionclassId() != null) {
	Sectionclass sectionClass =
		sectionclassRepository
			.findById(request.getSectionclassId())
			.orElseThrow(
				() ->
					new AppException(ErrorCode.SECTIONCLASS_NOT_FOUND));
	registration.setSectionclass(sectionClass);
	}

	registration = registrationRepository.save(registration);
	return registrationMapper.toRegistrationResponse(registration);
}

@PreAuthorize("hasAuthority('READ_REGISTRATION')")
public List<RegistrationResponse> getAll() {
	return registrationRepository.findAll().stream()
		.map(registrationMapper::toRegistrationResponse)
		.toList();
}

@PreAuthorize("hasAuthority('UPDATE_REGISTRATION')")
public RegistrationResponse updateRegistration(String id, RegistrationRequest request) {
	Registration registration =
		registrationRepository
			.findById(id)
			.orElseThrow(() -> new AppException(ErrorCode.REGISTRATION_NOT_EXIST));
	if (request.getStudentNumberId() != null) {
	User student =
		userRepository
			.findByNumbered(request.getStudentNumberId())
			.orElseThrow(
				() ->
					new AppException(ErrorCode.USER_NOT_FOUND));
	registration.setStudent(student);
	}
	if (request.getSectionclassId() != null) {
	Sectionclass sectionClass =
		sectionclassRepository
			.findById(request.getSectionclassId())
			.orElseThrow(
				() ->
					new AppException(ErrorCode.SECTIONCLASS_NOT_FOUND));
	registration.setSectionclass(sectionClass);
	}
	registration = registrationRepository.save(registration);
	return registrationMapper.toRegistrationResponse(registration);
}

@PreAuthorize("hasRole('ADMIN')")
public void deleteRegistration(String id) {
	Registration registration = registrationRepository
	.findById(id)
	.orElseThrow(() -> new AppException(ErrorCode.REGISTRATION_NOT_EXIST));
	registrationRepository.delete(registration);
}
}

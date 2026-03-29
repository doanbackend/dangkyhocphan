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

	// Fetch and set related entities
	if (request.getStudentNumberId() != null) {
	User student =
		userRepository
			.findByNumberid(request.getStudentNumberId())
			.orElseThrow(
				() ->
					new RuntimeException(
						"Student not found with numberId: " + request.getStudentNumberId()));
	registration.setStudent(student);
	}
	if (request.getSectionClassId() != null) {
	Sectionclass sectionClass =
		sectionclassRepository
			.findById(Long.valueOf(request.getSectionClassId()))
			.orElseThrow(
				() ->
					new RuntimeException(
						"SectionClass not found with id: " + request.getSectionClassId()));
	registration.setSectionClass(sectionClass);
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
			.orElseThrow(() -> new RuntimeException("Registration not found"));

	// Fetch and set related entities
	if (request.getStudentNumberId() != null) {
	User student =
		userRepository
			.findByNumberid(request.getStudentNumberId())
			.orElseThrow(
				() ->
					new RuntimeException(
						"Student not found with numberId: " + request.getStudentNumberId()));
	registration.setStudent(student);
	}
	if (request.getSectionClassId() != null) {
	Sectionclass sectionClass =
		sectionclassRepository
			.findById(Long.valueOf(request.getSectionClassId()))
			.orElseThrow(
				() ->
					new RuntimeException(
						"SectionClass not found with id: " + request.getSectionClassId()));
	registration.setSectionClass(sectionClass);
	}

	registration = registrationRepository.save(registration);
	return registrationMapper.toRegistrationResponse(registration);
}

@PreAuthorize("hasRole('ADMIN')")
public void deleteRegistration(String id) {
	registrationRepository.deleteById(id);
}
}

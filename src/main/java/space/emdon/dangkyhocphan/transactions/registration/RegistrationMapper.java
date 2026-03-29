package space.emdon.dangkyhocphan.transactions.registration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

@Mapping(target = "student", ignore = true)
@Mapping(target = "sectionClass", ignore = true)
@Mapping(target = "status", ignore = true)
Registration toRegistration(RegistrationRequest request);

@Mapping(target = "studentId", source = "student.id")
@Mapping(target = "studentName", source = "student.name")
@Mapping(target = "sectionClassId", source = "sectionClass.id")
@Mapping(target = "subjectName", source = "sectionClass.subject.name")
@Mapping(
	target = "room",
	expression = "java(mapRoomFromSchedules(registration.getSectionClass()))")
@Mapping(target = "status", source = "status")
RegistrationResponse toRegistrationResponse(Registration registration);

default String mapRoomFromSchedules(Sectionclass sectionClass) {
	if (sectionClass == null
		|| sectionClass.getSchedules() == null
		|| sectionClass.getSchedules().isEmpty()) {
	return null;
	}
	return sectionClass.getSchedules().iterator().next().getRoom();
}
}

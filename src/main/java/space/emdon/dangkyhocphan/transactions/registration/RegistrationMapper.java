package space.emdon.dangkyhocphan.transactions.registration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

@Mapping(target = "student", ignore = true)
@Mapping(target = "sectionclass", ignore = true)
@Mapping(target = "status", ignore = true)
Registration toRegistration(RegistrationRequest request);

@Mapping(target = "studentNumbered", source = "student.numbered")
@Mapping(target = "studentName", source = "student.name")
@Mapping(target = "sectionclassName", source = "sectionclass.name")
@Mapping(target = "subjectName", source = "sectionclass.subject.name")
@Mapping(
	target = "room",
	expression = "java(mapRoomFromSchedules(registration.getSectionclass()))")
@Mapping(target = "status", source = "status")
@Mapping(target = "id", source = "id")
@Mapping(target = "registrationDateTime", source = "registrationDateTime")
RegistrationResponse toRegistrationResponse(Registration registration);

default String mapRoomFromSchedules(Sectionclass sectionclass) {
	if (sectionclass == null
		|| sectionclass.getSchedules() == null
		|| sectionclass.getSchedules().isEmpty()) {
	return null;
	}
	return sectionclass.getSchedules().iterator().next().getRoom();
}
}

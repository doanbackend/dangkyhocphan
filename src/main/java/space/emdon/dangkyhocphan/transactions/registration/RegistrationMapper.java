package space.emdon.dangkyhocphan.transactions.registration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    RegistrationResponse toRegistrationResponse(Registration registration);


}




package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SectionclassMapper {

@Mapping(target = "name", source = "name")
@Mapping(target = "subject", ignore = true)
@Mapping(target = "lecturer", ignore = true)
@Mapping(target = "semester", ignore = true)
@Mapping(target = "schedules", ignore = true)
@Mapping(target = "version", ignore = true)
@Mapping(target = "currentStudents", constant = "0")
Sectionclass toSectionClass(SectionclassRequest request);

@Mapping(target = "subjectCode", source = "subject.code")
@Mapping(target = "subjectName", source = "subject.name")
@Mapping(target = "lecturerNumbered", source = "lecturer.numbered")
@Mapping(target = "lecturerName", source = "lecturer.name")
@Mapping(target = "semesterName", source = "semester.name")
@Mapping(target = "maxStudents", source = "maxStudents")
@Mapping(target = "currentStudents", source = "currentStudents")
@Mapping(target = "schedules", ignore = true)
@Mapping(target = "name", source = "name")
SectionclassResponse toSectionClassResponse(Sectionclass sectionClass);

@Mapping(target = "name", ignore = true)
@Mapping(target = "subject", ignore = true)
@Mapping(target = "lecturer", ignore = true)
@Mapping(target = "semester", ignore = true)
@Mapping(target = "schedules", ignore = true)
@Mapping(target = "version", ignore = true)
void updateSectionClass(@MappingTarget Sectionclass sectionClass, SectionclassRequest request);
}

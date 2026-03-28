package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SectionclassMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "currentStudents", constant = "0")
    Sectionclass toSectionClass(SectionclassRequest request);

    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "subjectName", source = "subject.name")
    @Mapping(target = "instructorId", source = "instructor.id")
    @Mapping(target = "instructorName", source = "instructor.name")
    @Mapping(target = "semesterName", source = "semester.name")
    SectionclassResponse toSectionClassResponse(Sectionclass sectionClass);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateSectionClass(@MappingTarget Sectionclass sectionClass, SectionclassRequest request);


}
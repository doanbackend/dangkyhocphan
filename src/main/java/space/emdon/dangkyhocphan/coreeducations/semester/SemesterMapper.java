package space.emdon.dangkyhocphan.coreeducations.semester;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

@Mapping(target = "sectionclass", ignore = true)
Semester toSemester(SemesterRequest request);

SemesterResponse toSemesterResponse(Semester semester);

@Mapping(target = "sectionclass", ignore = true)
void updateSemester(@MappingTarget Semester semester, SemesterRequest request);
}

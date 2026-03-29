package space.emdon.dangkyhocphan.coreeducations.subject;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.SectionclassResponse;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

@Mapping(target = "sections", ignore = true)
Subject toSubject(SubjectRequest request);

SubjectResponse toSubjectResponse(Subject subject);

@Mapping(target = "sections", ignore = true)
@Mapping(target = "id", ignore = true)
void updateSubject(@MappingTarget Subject subject, SubjectRequest request);

SectionclassResponse toSectionclassResponse(Sectionclass sectionclass);
}

package space.emdon.dangkyhocphan.coreeducations.schedule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

@Mapping(target = "id", ignore = true)
@Mapping(target = "sectionClass", ignore = true)
Schedule toSchedule(ScheduleRequest request);

@Mapping(target = "sectionClassId", source = "sectionClass.id")
@Mapping(target = "subjectName", source = "sectionClass.subject.name")
ScheduleResponse toScheduleResponse(Schedule schedule);

@Mapping(target = "id", ignore = true)
@Mapping(target = "sectionClass", ignore = true)
void updateSchedule(@MappingTarget Schedule schedule, ScheduleRequest request);
}

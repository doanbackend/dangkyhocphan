package space.emdon.dangkyhocphan.coreeducations.schedule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

@Mapping(target = "id", ignore = true)
@Mapping(target = "sectionclass", ignore = true)
Schedule toSchedule(ScheduleRequest request);

@Mapping(target = "id", source = "id")
@Mapping(target = "sectionclassId", source = "sectionclass.name")
@Mapping(target = "subjectName", source = "sectionclass.subject.name")
ScheduleResponse toScheduleResponse(Schedule schedule);

@Mapping(target = "id", ignore = true)
@Mapping(target = "sectionclass", ignore = true)
void updateSchedule(@MappingTarget Schedule schedule, ScheduleRequest request);
}

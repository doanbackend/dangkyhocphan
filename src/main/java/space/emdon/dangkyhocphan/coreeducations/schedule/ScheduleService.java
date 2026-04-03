package space.emdon.dangkyhocphan.coreeducations.schedule;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.SectionclassRepository;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ScheduleService {
ScheduleRepository scheduleRepository;
ScheduleMapper scheduleMapper;
SectionclassRepository sectionclassRepository;

@PreAuthorize("hasAuthority('CREATE_SCHEDULE')")
public ScheduleResponse createSchedule(ScheduleRequest request) {
	if (request.getStartPeriod() >= request.getEndPeriod()) {
	throw new AppException(ErrorCode.INVALID_SCHEDULE_PERIOD);
	}
	Sectionclass sectionclass =
		sectionclassRepository
			.findById(request.getSectionclassName())
			.orElseThrow(() -> new AppException(ErrorCode.SECTIONCLASS_NOT_FOUND));

	if (scheduleRepository.existsByDayOfWeekAndStartPeriodAndEndPeriodAndRoom(
		request.getDayOfWeek(),
		request.getStartPeriod(),
		request.getEndPeriod(),
		request.getRoom())) {
	throw new AppException(ErrorCode.SCHEDULE_ALREADY_EXISTS);
	}

	Schedule schedule = scheduleMapper.toSchedule(request);
	schedule.setSectionclass(sectionclass);
	schedule = scheduleRepository.save(schedule);
	return scheduleMapper.toScheduleResponse(schedule);
}

@PreAuthorize("hasAuthority('READ_SCHEDULE')")
public Page<ScheduleResponse> getAllSchedules(Pageable pageable) {
	return scheduleRepository.findAll(pageable)
		.map(scheduleMapper::toScheduleResponse);
}

@PreAuthorize("hasAuthority('DELETE_SCHEDULE')")
public void deleteSchedule(Schedule scheduleFromRequest) {
	Schedule schedule =
		scheduleRepository
			.findById(scheduleFromRequest.getId())
			.orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_EXIST));

	scheduleRepository.deleteById(schedule.getId());
}

@PreAuthorize("hasAuthority('UPDATE_SCHEDULE')")
public ScheduleResponse updateSchedule(ScheduleRequest request) {
	if (request.getStartPeriod() >= request.getEndPeriod()) {
	throw new AppException(ErrorCode.INVALID_SCHEDULE_PERIOD);
	}

	if (request.getSectionclassName() == null || request.getSectionclassName().isBlank()) {
	throw new AppException(ErrorCode.SECTION_CLASS_NAME_REQUIRED);
	}

	Sectionclass sectionclass =
		sectionclassRepository
			.findById(request.getSectionclassName())
			.orElseThrow(() -> new AppException(ErrorCode.SECTIONCLASS_NOT_FOUND));

	Schedule schedule =
		scheduleRepository
			.findById(request.getId())
			.orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_EXIST));
	scheduleMapper.updateSchedule(schedule, request);
	schedule.setSectionclass(sectionclass);
	schedule = scheduleRepository.save(schedule);
	return scheduleMapper.toScheduleResponse(schedule);
}
}

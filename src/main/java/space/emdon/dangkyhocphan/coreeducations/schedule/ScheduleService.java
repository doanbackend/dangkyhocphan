package space.emdon.dangkyhocphan.coreeducations.schedule;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.coreeducations.semester.Semester;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterRequest;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterResponse;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.role.RoleRequest;
import space.emdon.dangkyhocphan.rbac.role.RoleResponse;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleService {
    ScheduleRepository scheduleRepository;
    ScheduleMapper scheduleMapper;

    @PreAuthorize("hasAuthority('CREATE_SCHEDULE')")
    public ScheduleResponse createSchedule(ScheduleRequest request) {

        Schedule schedule = scheduleMapper.toSchedule(request);
        schedule = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleResponse(schedule);
    }

    @PreAuthorize("hasAuthority('GET_SCHEDULE')")
    public List<ScheduleResponse> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(scheduleMapper::toScheduleResponse).toList();
    }

    @PreAuthorize("hasAuthority('DELETE_SCHEDULE')")
    public void deleteSchedule(Schedule scheduleFromRequest) {
        Schedule schedule =
                scheduleRepository
                        .findById(scheduleFromRequest.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        scheduleRepository.deleteById(schedule.getId());
    }

    @PreAuthorize("hasAuthority('UPDATE_SCHEDULE')")
    public ScheduleResponse updateSchedule(ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));
        scheduleMapper.updateSchedule(schedule, request);
        schedule = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleResponse(schedule);
    }


}

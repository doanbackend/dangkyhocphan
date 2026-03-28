package space.emdon.dangkyhocphan.coreeducations.schedule;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;


@Slf4j
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleController {

    ScheduleService scheduleService;
    ScheduleRepository scheduleRepository;

    @PostMapping
    ApiResponse<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
        return ApiResponse.<ScheduleResponse>builder().result(scheduleService.createSchedule(request)).build();
    }
    
    @GetMapping
    ApiResponse<List<ScheduleResponse>> getAllSchedules() {
        return ApiResponse.<List<ScheduleResponse>>builder()
                .result(scheduleService.getAllSchedules())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ScheduleResponse> updateSchedule(@PathVariable String id, @RequestBody ScheduleRequest request) {
        return ApiResponse.<ScheduleResponse>builder().result(scheduleService.updateSchedule(request)).build();
    }
   
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new AppException(ErrorCode.SCHEDULE_NOT_EXIST);
        }
        scheduleService.deleteSchedule(Schedule.builder().id(id).build());
    }
   

}

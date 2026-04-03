package space.emdon.dangkyhocphan.coreeducations.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleListDTO {
private Long id;
private String dayOfWeek;
private int startPeriod;
private int endPeriod;
private String room;
}

package space.emdon.dangkyhocphan.coreeducations.semester;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterListDTO {
private String name;
private LocalDate startDate;
private LocalDate endDate;
}

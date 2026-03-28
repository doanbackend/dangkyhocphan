package space.emdon.dangkyhocphan.coreeducations.semester;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.constraint.RegistrationDateConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegistrationDateConstraint
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SemesterRequest {
    @NotBlank(message = "SEMESTER_NAME_INVALID")
    String name; 

    @NotNull(message = "START_DATE_INVALID")
    LocalDate startDate;

    @NotNull(message = "END_DATE_INVALID")
    LocalDate endDate;

    @NotNull(message = "REG_START_INVALID")
    LocalDateTime registrationStartDate;

    @NotNull(message = "REG_END_INVALID")
    LocalDateTime registrationEndDate;

}

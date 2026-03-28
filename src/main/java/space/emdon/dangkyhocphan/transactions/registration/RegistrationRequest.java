package space.emdon.dangkyhocphan.transactions.registration;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {

    @NotBlank(message = "SECTION_CLASS_ID_REQUIRED")
    Long sectionClassId;

}

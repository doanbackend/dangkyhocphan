package space.emdon.dangkyhocphan.coreeducations.subject;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectResponse {

Long id;
String code;
String name;
int credits;
}

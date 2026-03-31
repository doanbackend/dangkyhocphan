package space.emdon.dangkyhocphan.transactions.invoice;

import java.time.LocalDate;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceRequest {

String studentNumbered;
String semesterName;
Boolean paid;
LocalDate paymentDate;
}

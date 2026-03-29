package space.emdon.dangkyhocphan.transactions.invoice;

import java.time.LocalDate;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {

String id;
String studentName;
String msv; // Mã sinh viên
String semesterName;
Long totalAmount;
Boolean paid;
LocalDate paymentDate;
Set<String> registrationIds;
}

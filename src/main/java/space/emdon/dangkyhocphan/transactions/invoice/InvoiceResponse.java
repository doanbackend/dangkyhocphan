package space.emdon.dangkyhocphan.transactions.invoice;

import java.time.LocalDate;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {

String id;
String studentName;
String studentNumbered;
String semesterName;
Long totalAmount;
Boolean paid;
LocalDate paymentDate;
Set<String> registrationIds;
}

package space.emdon.dangkyhocphan.transactions.invoice;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceRequest {

    String studentId;
    Long semesterId;
    Boolean paid;
    LocalDate paymentDate;

}

package space.emdon.dangkyhocphan.transactions.invoice;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import space.emdon.dangkyhocphan.transactions.registration.Registration;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

@Mapping(target = "student", ignore = true)
@Mapping(target = "semester", ignore = true)
@Mapping(target = "registrations", ignore = true)
Invoice toInvoice(InvoiceRequest request);

@Mapping(target = "id", source = "id")
@Mapping(target = "studentName", source = "student.name")
@Mapping(target = "studentNumbered", source = "student.numbered")
@Mapping(target = "semesterName", source = "semester.name")
@Mapping(target = "totalAmount", source = "totalAmount")
@Mapping(target = "paid", source = "paid")
@Mapping(target = "paymentDate", source = "paymentDate")
@Mapping(
	target = "registrationIds",
	expression = "java(mapRegistrationsToIds(invoice.getRegistrations()))")
InvoiceResponse toInvoiceResponse(Invoice invoice);

@Mapping(target = "paymentDate", source = "request.paymentDate")
@Mapping(target = "paid", source = "request.paid")
void updateInvoice(@MappingTarget Invoice invoice, InvoiceRequest request);

default Set<String> mapRegistrationsToIds(Set<Registration> registrations) {
	if (registrations == null) return null;
	return registrations.stream().map(Registration::getId).collect(Collectors.toSet());
}
}

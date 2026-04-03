package space.emdon.dangkyhocphan.transactions.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.emdon.dangkyhocphan.coreeducations.semester.Semester;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterRepository;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.user.User;
import space.emdon.dangkyhocphan.rbac.user.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvoiceService {

InvoiceRepository invoiceRepository;
InvoiceMapper invoiceMapper;
UserRepository userRepository;
SemesterRepository semesterRepository;

@PreAuthorize("hasAuthority('CREATE_INVOICE')")
public InvoiceResponse createInvoice(InvoiceRequest request) {
	Invoice invoice = invoiceMapper.toInvoice(request);

	if (request.getStudentNumbered() != null) {
	User student =
		userRepository
			.findByNumbered(request.getStudentNumbered())
			.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
	invoice.setStudent(student);
	}
	if (request.getSemesterName() != null) {
	Semester semester =
		semesterRepository
			.findByName(request.getSemesterName())
			.orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND));
	invoice.setSemester(semester);
	}
	invoice = invoiceRepository.save(invoice);
	return invoiceMapper.toInvoiceResponse(invoice);
}

@PreAuthorize("hasAuthority('READ_INVOICE')")
public Page<InvoiceResponse> getInvoices(Pageable pageable) {
	return invoiceRepository.findAll(pageable).map(invoiceMapper::toInvoiceResponse);
}

@PreAuthorize("hasAuthority('GET_MY_INVOICE')")
public InvoiceResponse getMyInvoice(String id) {
	Invoice invoice =
		invoiceRepository
			.findById(id)
			.orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_EXIST));
	return invoiceMapper.toInvoiceResponse(invoice);
}

@PreAuthorize("hasAuthority('UPDATE_INVOICE')")
public InvoiceResponse updateInvoice(String id, InvoiceRequest request) {
	Invoice invoice =
		invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));

	invoiceMapper.updateInvoice(invoice, request);
	if (request.getStudentNumbered() != null) {
	User student =
		userRepository
			.findByNumbered(request.getStudentNumbered())
			.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
	invoice.setStudent(student);
	}
	if (request.getSemesterName() != null) {
	Semester semester =
		semesterRepository
			.findByName(request.getSemesterName())
			.orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND));
	invoice.setSemester(semester);
	}
	invoice = invoiceRepository.save(invoice);
	return invoiceMapper.toInvoiceResponse(invoice);
}

@PreAuthorize("hasAuthority('DELETE_INVOICE')")
public void deleteInvoice(String id) {
	invoiceRepository.deleteById(id);
}
}

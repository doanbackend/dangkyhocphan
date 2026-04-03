package space.emdon.dangkyhocphan.transactions.invoice;

import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class InvoiceController {
private final InvoiceService invoiceService;

@PostMapping
ApiResponse<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest request) {
	return ApiResponse.<InvoiceResponse>builder()
		.result(invoiceService.createInvoice(request))
		.build();
}

@GetMapping
ApiResponse<Page<InvoiceResponse>> getInvoices(@PageableDefault(page = 0, size = 10, sort = "student", direction = Sort.Direction.DESC) Pageable pageable) {
	return ApiResponse.<Page<InvoiceResponse>>builder()
		.result(invoiceService.getInvoices(pageable))
		.build();
}

@GetMapping("/{id}")
public ApiResponse<InvoiceResponse> getMyInvoice(@PathVariable String id) {
	return ApiResponse.<InvoiceResponse>builder()
		.result(invoiceService.getMyInvoice(id))
		.build();
}


@PutMapping("/{id}")
ApiResponse<InvoiceResponse> updateInvoice(
	@PathVariable String id, @RequestBody InvoiceRequest request) {
	return ApiResponse.<InvoiceResponse>builder()
		.result(invoiceService.updateInvoice(id, request))
		.build();
}

@DeleteMapping("/{id}")
void deleteInvoice(@PathVariable String id) {
	invoiceService.deleteInvoice(id);
}
}

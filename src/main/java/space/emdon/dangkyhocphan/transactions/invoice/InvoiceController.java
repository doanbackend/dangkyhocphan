package space.emdon.dangkyhocphan.transactions.invoice;

import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import lombok.RequiredArgsConstructor;


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
    ApiResponse<List<InvoiceResponse>> getInvoices() {
        return ApiResponse.<List<InvoiceResponse>>builder()
                .result(invoiceService.getInvoices())
                .build();
    }
    
    @PutMapping("/{id}")
    ApiResponse<InvoiceResponse> updateInvoice(
            @PathVariable String id, 
            @RequestBody InvoiceRequest request 
    ) {
        return ApiResponse.<InvoiceResponse>builder()
                .result(invoiceService.updateInvoice(id, request)) 
                .build();
    }
    
    @DeleteMapping("/{id}")
    void deleteInvoice(@PathVariable String id) {
        invoiceService.deleteInvoice(id);
    }
    
}

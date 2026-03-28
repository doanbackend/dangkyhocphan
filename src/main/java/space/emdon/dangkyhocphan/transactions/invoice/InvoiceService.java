package space.emdon.dangkyhocphan.transactions.invoice;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvoiceService {

    InvoiceRepository invoiceRepository;
    InvoiceMapper invoiceMapper;

    
    @PreAuthorize("hasAuthority('CREATE_INVOICE')")
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Invoice invoice = invoiceMapper.toInvoice(request);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toInvoiceResponse(invoice);
    }
    
    @PreAuthorize("hasAuthority('GET_INVOICES')")
    public List<InvoiceResponse> getInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toInvoiceResponse)
                .toList();
    }
    @PreAuthorize("hasAuthority('GET_INVOICE')")
    public InvoiceResponse getInvoice(String id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Invoice not found"));
        return invoiceMapper.toInvoiceResponse(invoice);
    }

    @PreAuthorize("hasAuthority('UPDATE_INVOICE')")
    public InvoiceResponse updateInvoice(String id, InvoiceRequest request) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Invoice not found"));
        
        invoiceMapper.updateInvoice(invoice, request); 
        
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toInvoiceResponse(invoice);
    }

    @PreAuthorize("hasAuthority('DELETE_INVOICE')")
    public void deleteInvoice(String id) {
        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException("Invoice not found to delete");
        }
        invoiceRepository.deleteById(id);
    }

}

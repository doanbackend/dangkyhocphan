package space.emdon.dangkyhocphan.transactions.registration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.transactions.invoice.InvoiceResponse;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegistrationService {

    RegistrationRepository registrationRepository;
    RegistrationMapper registrationMapper;

    @PreAuthorize("hasAuthority('CREATE_REGISTRATION')")
    public RegistrationResponse createRegistration(RegistrationRequest request) {
        Registration registration = registrationMapper.toRegistration(request);
        registration = registrationRepository.save(registration);
        return registrationMapper.toRegistrationResponse(registration);
    }

    @PreAuthorize("hasAuthority('READ_REGISTRATION')")
    public List<RegistrationResponse> getAll() {
        return registrationRepository.findAll().stream()
                .map(registrationMapper::toRegistrationResponse)
                .toList();
    }
    @PreAuthorize("hasAuthority('UPDATE_REGISTRATION')")
    public RegistrationResponse updateRegistration(String id, RegistrationRequest request) {
        Registration registration = registrationRepository.findById(id).orElseThrow(() -> new RuntimeException("Registration not found"));
        registration = registrationMapper.toRegistration(request);
        registration = registrationRepository.save(registration);
        return registrationMapper.toRegistrationResponse(registration);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRegistration(String id) {
        registrationRepository.deleteById(id);
    }


}

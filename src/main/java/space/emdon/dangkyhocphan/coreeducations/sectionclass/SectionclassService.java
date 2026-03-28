package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SectionclassService {

    SectionclassRepository sectionclassRepository;
    SectionclassMapper sectionclassMapper;


    @PreAuthorize("hasRole('ADMIN')")
    public SectionclassResponse createSectionclass(SectionclassRequest request) {
        Sectionclass sectionclass = sectionclassMapper.toSectionClass(request);
        sectionclass = sectionclassRepository.save(sectionclass);
        return sectionclassMapper.toSectionClassResponse(sectionclass);
    }

    public List<SectionclassResponse> getAllSectionclasses() {
        return sectionclassRepository.findAll().stream()
                .map(sectionclassMapper::toSectionClassResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SectionclassResponse getSectionclass(String id) {
        Sectionclass sectionclass = sectionclassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sectionclass not found"));
        return sectionclassMapper.toSectionClassResponse(sectionclass);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public SectionclassResponse updateSectionclass(String id, SectionclassRequest request) {
        Sectionclass sectionclass = sectionclassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sectionclass not found"));
        sectionclassMapper.updateSectionClass(sectionclass, request);
        sectionclass = sectionclassRepository.save(sectionclass);
        return sectionclassMapper.toSectionClassResponse(sectionclass);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteSectionclassById(String id) {
        sectionclassRepository.deleteById(id);
    }


}

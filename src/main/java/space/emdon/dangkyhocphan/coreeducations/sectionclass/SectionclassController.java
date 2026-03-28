package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;




@Slf4j
@RestController
@RequestMapping("/sectionclasses")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SectionclassController {

    SectionclassService sectionclassService;

    @PostMapping
    ApiResponse<SectionclassResponse> createSectionclass(@RequestBody SectionclassRequest request) {
        return ApiResponse.<SectionclassResponse>builder().result(sectionclassService.createSectionclass(request)).build();
    }

    @GetMapping
    ApiResponse<List<SectionclassResponse>> getAllSectionclasses() {
        return ApiResponse.<List<SectionclassResponse>>builder()
                .result(sectionclassService.getAllSectionclasses())
                .build();
    }
    
    @PutMapping("/{id}")
    ApiResponse<SectionclassResponse> updateSectionclass(@PathVariable String id, @RequestBody SectionclassRequest request) {
        return ApiResponse.<SectionclassResponse>builder()
                .result(sectionclassService.updateSectionclass(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteSectionclass(@PathVariable String id) {
        sectionclassService.deleteSectionclassById(id);
    }
    
}

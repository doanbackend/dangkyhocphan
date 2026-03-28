package space.emdon.dangkyhocphan.coreeducations.subject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.rbac.role.Role;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SubjectService {

    SubjectRepository subjectRepository;
    SubjectMapper subjectMapper;

    @PreAuthorize("hasAuthority('CREATE_SUBJECT')")
    public SubjectResponse createSubject(SubjectRequest request){
        if (subjectRepository.existsByCodeAndName(request.getCode(), request.getName())) {
            throw new AppException(ErrorCode.SUBJECT_ALREADY_EXISTS);
        }
        if (subjectRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.SUBJECT_CODE_EXISTED);
        }
        if (subjectRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.SUBJECT_NAME_EXISTED);
        }
        Subject subject = subjectMapper.toSubject(request);
        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.toSubjectResponse(savedSubject);
    }



    
    @PreAuthorize("hasAuthority('READ_SUBJECT')")
    public List<SubjectResponse> getAllSubjects(){
        return subjectRepository.findAll()
            .stream()
            .map(subjectMapper::toSubjectResponse)
            .toList();
    }
    
    @PreAuthorize("hasAuthority('READ_SUBJECT')")
    public SubjectResponse getSubjectById(String code){
        return subjectMapper.toSubjectResponse(subjectRepository.findByCode(code).orElseThrow(
            () -> new AppException(ErrorCode.SUBJECT_NOT_FOUND)));
    }

    @PreAuthorize("hasAuthority('UPDATE_SUBJECT')")
    public SubjectResponse updateSubject(String code, SubjectRequest request){
        Subject subject = subjectRepository.findByCode(code).orElseThrow(
            () -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));
        subjectMapper.updateSubject(subject, request);
        return subjectMapper.toSubjectResponse(subjectRepository.save(subject));
    }

    @PreAuthorize("hasAuthority('DELETE_SUBJECT')")
    public void deleteSubject(String code){
        Subject subject = subjectRepository.findByCode(code).orElseThrow(
            () -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));
        subjectRepository.deleteByCode(subject.getCode());
    }
}

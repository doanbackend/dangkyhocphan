package space.emdon.dangkyhocphan.rbac.user;
 
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.SectionclassRepository;
import space.emdon.dangkyhocphan.rbac.role.RoleMapper;


import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public abstract class UserMapper {
 
    @Autowired
    protected SectionclassRepository sectionclassRepository;
 
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "numberid", ignore = true)
    @Mapping(target = "password", ignore = true)
    public abstract User toUser(UserRequest request);
 
    public abstract UserResponse toUserResponse(User user);
 
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numberid", ignore = true)
    public abstract void updateUser(@MappingTarget User user, UserRequest request);

    protected Set<Sectionclass> mapAssignedClasses(Set<String> assignedClasses) {
        if (assignedClasses == null || assignedClasses.isEmpty()) {
            return Collections.emptySet();
        }
        return assignedClasses.stream()
                .filter(id -> id != null && !id.isEmpty())
                .map(id -> sectionclassRepository.findById(id).orElse(null))
                .filter(sc -> sc != null)
                .collect(Collectors.toSet());
    }
}
package space.emdon.dangkyhocphan.rbac.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.SectionclassRepository;
import space.emdon.dangkyhocphan.rbac.role.RoleMapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public abstract class UserMapper {

protected SectionclassRepository sectionclassRepository;

@Autowired
public void setSectionclassRepository(SectionclassRepository sectionclassRepository) {
	this.sectionclassRepository = sectionclassRepository;
}

@Mapping(target = "roles", ignore = true)
@Mapping(target = "numbered", ignore = true)
@Mapping(target = "password", ignore = true)
public abstract User toUser(UserRequest request);

public abstract UserResponse toUserResponse(User user);

@Mapping(target = "roles", ignore = true)
@Mapping(target = "id", ignore = true)
@Mapping(target = "numbered", ignore = true)
public abstract void updateUser(@MappingTarget User user, UserRequest request);

protected Set<Sectionclass> mapSectionclass(Set<String> sectionclass) {
	if (sectionclass == null || sectionclass.isEmpty()) {
	return Collections.emptySet();
	}

	List<Sectionclass> classes =
		sectionclassRepository.findAllById(sectionclass.stream().collect(Collectors.toList()));

	return new HashSet<>(classes);
}
}

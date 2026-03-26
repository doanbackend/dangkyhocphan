package space.emdon.dangkyhocphan.rbac.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "string", uses = RoleMapper.class)
public @interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserRequest request);
    UserResponse toUserResponse(User user);
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserRequest request);



}

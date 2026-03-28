package space.emdon.dangkyhocphan.rbac.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.emdon.dangkyhocphan.dto.response.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    @Autowired private  UserService userService;
    @Autowired private UserMapper userMapper;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest request){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setCode(1000);
        response.setMessage("User created successfully");
        response.setResult(userService.createUser(request));
        return response;

    }
    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Useremail: {}", authentication.getName());

        authentication
                .getAuthorities()
                .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder().result(userService.getAllUsers()).build();
    }
    @GetMapping("/normal")
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Useremail: {}", authentication.getName());

        authentication
                .getAuthorities()
                .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder().result(userService.getUsers()).build();
    }
    @GetMapping("/students")
    ApiResponse<List<UserResponse>> getStudents() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Useremail: {}", authentication.getName());

        authentication
                .getAuthorities()
                .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder().result(userService.getStudents()).build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userid") String userId) {
        return ApiResponse.<UserResponse>builder().result(userService.getUserById(userId)).build();
    }

    @GetMapping("/myinfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder().result(userService.getMyInfo()).build();
    }

    @PutMapping("/{userId}")
    ApiResponse<User> updateUser(@PathVariable String userId, @RequestBody @Valid UserRequest request) {
        return ApiResponse.<User>builder().result(userService.updateUser(userId, request)).build();
    }

    @DeleteMapping("/{userId}")
    public User deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }


}

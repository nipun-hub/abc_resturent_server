package com.project.abc.controller.user;

import com.project.abc.dto.order.OrderCountDTO;
import com.project.abc.dto.user.*;
import com.project.abc.model.user.User;
import com.project.abc.security.Session;
import com.project.abc.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        userDTO.validate();
        User user = userService.registerUser(userDTO);
        UserDTO createUserDTO = UserDTO.init(user);
        return ResponseEntity.ok(createUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDto) {
        User user = userService.login(userDto);
        Session.setUser(user);
        UserDTO loginUserDTO = UserDTO.init(user);
        return ResponseEntity.ok(loginUserDTO);
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserDTO> userDetails() {
        User user = userService.getUserById(Session.getUser().getId());
        UserDTO userDTO = UserDTO.init(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = UserDTO.init(user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        userUpdateDTO.validate();
        String userId = Session.getUser().getId();
        User user = userService.updateUser(userUpdateDTO,userId);
        UserDTO userDTO = UserDTO.init(user);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String userId) {
        User user = userService.deleteUser(userId);
        return ResponseEntity.ok(UserDTO.init(user));
    }

    @GetMapping("/customers")
    public ResponseEntity<Page<UserDetailDTO>> getAllCustomers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "status", required = false) User.UserStatus status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        UserSearchParamDTO searchParams = new UserSearchParamDTO();
        searchParams.setName(name);
        searchParams.setEmail(email);
        searchParams.setStatus(status);
        searchParams.setPage(page);
        searchParams.setSize(size);

        Page<User> usersPage = userService.getAllCustomers(searchParams);
        List<UserDetailDTO> userDetailDTOs = usersPage.getContent().stream()
                .map(UserDetailDTO::init)
                .collect(Collectors.toList());
        Page<UserDetailDTO> userDetailDTOPage = new PageImpl<>(
                userDetailDTOs,
                PageRequest.of(page, size),
                usersPage.getTotalElements()
        );
        return ResponseEntity.ok(userDetailDTOPage);
    }

    @GetMapping("/staff-members")
    public ResponseEntity<Page<UserDetailDTO>> getAllStaffMembers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "status", required = false) User.UserStatus status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        UserSearchParamDTO searchParams = new UserSearchParamDTO();
        searchParams.setName(name);
        searchParams.setEmail(email);
        searchParams.setStatus(status);
        searchParams.setPage(page);
        searchParams.setSize(size);

        Page<User> usersPage = userService.getAllStaffMembers(searchParams);
        List<UserDetailDTO> userDetailDTOs = usersPage.getContent().stream()
                .map(UserDetailDTO::init)
                .collect(Collectors.toList());
        Page<UserDetailDTO> userDetailDTOPage = new PageImpl<>(
                userDetailDTOs,
                PageRequest.of(page, size),
                usersPage.getTotalElements()
        );
        return ResponseEntity.ok(userDetailDTOPage);
    }

    @GetMapping("/customer-count")
    public ResponseEntity<CustomerCountDTO> getCustomerCount() {
        int customerCount = userService.getCustomerCount();
        CustomerCountDTO customerCountDTO = new CustomerCountDTO();
        customerCountDTO.setCustomerCount(customerCount);
        return ResponseEntity.ok(customerCountDTO);
    }
}

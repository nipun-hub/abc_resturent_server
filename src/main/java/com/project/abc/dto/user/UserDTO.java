package com.project.abc.dto.user;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.user.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UserDTO extends BaseRequest {
    private String id;

    @Size(max = 40, min = 5, message = "Full name length should be more than 1 and less than 40")
    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @Size(max = 100, min = 6, message = "Email length should be more than 6 and less than 100")
    @Email(message = "Email not valid",regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Size(max = 255, min = 10, message = "Address length should be between 10 and 255 characters")
    @NotBlank(message = "Address is mandatory")
    private String address;

    @Size(max = 15, min = 3, message = "phone length should be more than 3 and less than 15")
    private String phone;

    @Size(max = 100, min = 6, message = "Password length should be between 6 and 100")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "The password is not strong")
    @NotBlank(message = "Password is mandatory")
    private String password;

    private User.UserStatus status;

    private User.UserRole role;

    public static UserDTO init(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        userDTO.setStatus(user.getStatus());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

}

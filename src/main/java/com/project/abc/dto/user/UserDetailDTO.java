package com.project.abc.dto.user;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailDTO extends BaseRequest {

    private String id;

    private String fullName;

    private String email;

    private String address;

    private String phone;

    private User.UserRole userRole;

    private User.UserStatus userStatus;

    public static UserDetailDTO init(User user) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setId(user.getId());
        userDetailDTO.setFullName(user.getFullName());
        userDetailDTO.setEmail(user.getEmail());
        userDetailDTO.setAddress(user.getAddress());
        userDetailDTO.setPhone(user.getPhone());
        userDetailDTO.setUserRole(user.getRole());
        userDetailDTO.setUserStatus(user.getStatus());
        return userDetailDTO;
    }
}

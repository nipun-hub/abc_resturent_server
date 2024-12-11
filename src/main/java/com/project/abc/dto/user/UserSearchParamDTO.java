package com.project.abc.dto.user;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UserSearchParamDTO extends BaseRequest {
    private String name;
    private String email;
    private User.UserStatus status;
    private int page;
    private int size;
}

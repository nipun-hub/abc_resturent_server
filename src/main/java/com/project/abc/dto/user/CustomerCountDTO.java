package com.project.abc.dto.user;

import com.project.abc.commons.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class CustomerCountDTO extends BaseRequest {

    private int customerCount;
}

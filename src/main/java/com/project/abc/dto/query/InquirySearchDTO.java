package com.project.abc.dto.query;

import com.project.abc.commons.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class InquirySearchDTO extends BaseRequest {
    private String userId;
}

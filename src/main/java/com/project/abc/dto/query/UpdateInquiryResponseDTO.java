package com.project.abc.dto.query;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.query.Inquiry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Size;

@Getter
@Setter
@Slf4j
public class UpdateInquiryResponseDTO extends BaseRequest {
    @Size(max = 500, min = 10, message = "Subject length should be more than 10 and less than 500")
    private String Response;

    private Inquiry.InquiryStatus status;
}

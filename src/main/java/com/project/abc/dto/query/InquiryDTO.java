package com.project.abc.dto.query;

import com.project.abc.commons.BaseRequest;
import com.project.abc.dto.user.UserDTO;
import com.project.abc.model.query.Inquiry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Size;

@Getter
@Setter
@Slf4j
public class InquiryDTO extends BaseRequest {
    private String id;

    @Size(max = 100, min = 3, message = "Subject length should be more than 3 and less than 100")
    private String subject;

    @Size(max = 500, min = 10, message = "Subject length should be more than 10 and less than 500")
    private String message;

    @Size(max = 500, min = 10, message = "Subject length should be more than 10 and less than 500")
    private String response;

    private Inquiry.InquiryStatus status;

    private UserDTO user;

    public static InquiryDTO init(Inquiry query) {
        InquiryDTO inquiryDTO = new InquiryDTO();
        inquiryDTO.setId(query.getId());
        inquiryDTO.setSubject(query.getSubject());
        inquiryDTO.setMessage(query.getMessage());
        inquiryDTO.setResponse(query.getResponse());
        inquiryDTO.setStatus(query.getStatus());
        return inquiryDTO;
    }

    public static InquiryDTO initWithUser(Inquiry inquiry) {
        InquiryDTO inquiryDTO = InquiryDTO.init(inquiry);
        inquiryDTO.setUser(UserDTO.init(inquiry.getUser()));
        return inquiryDTO;
    }
}

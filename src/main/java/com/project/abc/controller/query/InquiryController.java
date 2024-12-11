package com.project.abc.controller.query;

import com.project.abc.dto.query.InquiryDTO;
import com.project.abc.dto.query.InquirySearchDTO;
import com.project.abc.dto.query.UpdateInquiryResponseDTO;
import com.project.abc.model.query.Inquiry;
import com.project.abc.service.query.InquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/quiry")
@RestController
@Slf4j
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @PostMapping("/create-inquiry")
    public ResponseEntity<InquiryDTO> createInquiry(@RequestBody InquiryDTO inquiryDTO) {
        inquiryDTO.validate();
        Inquiry inquiry = inquiryService.createQuery(inquiryDTO);
        InquiryDTO dto = InquiryDTO.initWithUser(inquiry);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/add-response/{id}")
    public ResponseEntity<InquiryDTO> updateInquiryResponse(
            @RequestBody UpdateInquiryResponseDTO dto,
            @PathVariable String id
    ) {
        dto.validate();
        Inquiry query = inquiryService.updateResponse(dto, id);
        InquiryDTO inquiryDTO = InquiryDTO.init(query);
        return ResponseEntity.ok(inquiryDTO);
    }

    @GetMapping("/inquiries")
    public ResponseEntity<Page<InquiryDTO>> searchInquiries(
            @RequestParam(required = false) String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        InquirySearchDTO searchDTO = new InquirySearchDTO();
        searchDTO.setUserId(userId);
        Page<Inquiry> inquiriesPage = inquiryService.searchInquiries(searchDTO, page, size);
        Page<InquiryDTO> inquiryDTOPage = inquiriesPage.map(InquiryDTO::init);
        return ResponseEntity.ok(inquiryDTOPage);
    }
}

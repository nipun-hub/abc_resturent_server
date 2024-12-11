package com.project.abc.service.query;

import com.project.abc.commons.Check;
import com.project.abc.commons.exceptions.http.CategoryNotFoundException;
import com.project.abc.commons.exceptions.http.InquiryNotFoundException;
import com.project.abc.commons.exceptions.http.UserNotFoundException;
import com.project.abc.dto.query.InquiryDTO;
import com.project.abc.dto.query.InquirySearchDTO;
import com.project.abc.dto.query.UpdateInquiryResponseDTO;
import com.project.abc.model.category.Category;
import com.project.abc.model.item.Item;
import com.project.abc.model.query.Inquiry;
import com.project.abc.model.user.User;
import com.project.abc.repository.query.InquiryRepository;
import com.project.abc.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private UserRepository userRepository;

    public Inquiry createQuery(InquiryDTO inquiryDTO) {
        log.info("create query");
        Optional<User> user = userRepository.findById(inquiryDTO.getUser().getId());
        Check.throwIfEmpty(user, new UserNotFoundException("User not found with Id : " + inquiryDTO.getUser().getId()));
        Inquiry inquiry = Inquiry.initWithUser(inquiryDTO, user.get());
        return inquiryRepository.save(inquiry);
    }

    public Inquiry getInquiryById(String inquiryId) {
        log.info("Get inquiry by id = {}", inquiryId);
        Optional<Inquiry> optionalInquiry = inquiryRepository.findById(inquiryId);
        Check.throwIfEmpty(optionalInquiry, new InquiryNotFoundException("Inquiry not found with Id : " + inquiryId));
        Inquiry inquiry = optionalInquiry.get();
        return inquiry;
    }

    public Inquiry updateResponse(UpdateInquiryResponseDTO updateInquiryResponseDTO, String inquiryId) {
        log.info("updated inquiry id {}", inquiryId);
        Inquiry inquiry = this.getInquiryById(inquiryId);
        inquiry.setResponse(updateInquiryResponseDTO.getResponse());
        inquiry.setStatus(Inquiry.InquiryStatus.RESPONDED);
        return inquiryRepository.save(inquiry);
    }

    public Page<Inquiry> searchInquiries(InquirySearchDTO searchDTO, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return inquiryRepository.searchInquiriesByUserId(searchDTO.getUserId(), pageable);
    }
}

package com.project.abc.service.offer;

import com.project.abc.model.offer.OfferDetail;
import com.project.abc.repository.offer.OfferDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class OfferDetailService {

    @Autowired
    private OfferDetailRepository offerDetailRepository;

    public List<OfferDetail> getOfferDetailsByOfferId(String offerId) {
        log.info("get offer detail by id {}", offerId);
        return offerDetailRepository.findByOfferId(offerId);
    }
}

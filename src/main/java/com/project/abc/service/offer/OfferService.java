package com.project.abc.service.offer;

import com.project.abc.commons.Check;
import com.project.abc.commons.exceptions.http.BadRequestException;
import com.project.abc.commons.exceptions.http.OfferNotFoundException;
import com.project.abc.commons.exceptions.offer.OfferExType;
import com.project.abc.dto.item.ItemDTO;
import com.project.abc.dto.offer.OfferDTO;
import com.project.abc.dto.offer.OfferSearchParamDTO;
import com.project.abc.dto.offer.UpdateOfferDTO;
import com.project.abc.model.item.Item;
import com.project.abc.model.offer.Offer;
import com.project.abc.model.offer.OfferDetail;
import com.project.abc.repository.offer.OfferDetailRepository;
import com.project.abc.repository.offer.OfferRepository;
import com.project.abc.service.item.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OfferDetailRepository offerDetailRepository;

    @Transactional
    public Offer createOffer(OfferDTO offerDTO) {
        log.info("create offer {}", offerDTO.getOfferName());
        Offer offer = Offer.init(offerDTO);
        if (offerRepository.findByOfferNameAndStatusNot(offerDTO.getOfferName(), Offer.OfferStatus.DELETED).isPresent()) {
            throw new BadRequestException("offer already exist", OfferExType.OFFER_ALREADY_EXIST);
        }
        offerRepository.save(offer);

        List<ItemDTO> items = offerDTO.getItems();
        for (ItemDTO itemDTO : items) {
            Item item = itemService.getItemById(itemDTO.getId());

            OfferDetail offerDetail = new OfferDetail();
            offerDetail.setId(UUID.randomUUID().toString());
            offerDetail.setItemId(item.getId());
            offerDetail.setOfferId(offer.getId());
            offerDetail.setStartDate(offerDTO.getStartDate());
            offerDetail.setEndDate(offerDTO.getEndDate());
            offerDetail.setItem(item);
            offerDetail.setOffer(offer);

            offerDetailRepository.save(offerDetail);
        }
        return offer;
    }

    public Offer getOfferById(String id) {
        log.info("Get offer by id = {}", id);
        Optional<Offer> offerOptional = offerRepository.findById(id);
        Check.throwIfEmpty(offerOptional, new OfferNotFoundException("Offer not found with Id : " + id));
        Offer offer = offerOptional.get();
        return offer;
    }

    public Offer updateOffer(String offerId, UpdateOfferDTO updateOfferDTO) {
        log.info("Updating offer with id={}", offerId);
        Offer offer = getOfferById(offerId);
        if (updateOfferDTO.getOfferName() != null) {
            offer.setOfferName(updateOfferDTO.getOfferName());
        }
        if (updateOfferDTO.getDescription() != null) {
            offer.setDescription(updateOfferDTO.getDescription());
        }
        if (updateOfferDTO.getOfferUnitPrice() != null) {
            offer.setOfferUnitPrice(updateOfferDTO.getOfferUnitPrice());
        }
        offerRepository.save(offer);
        return offer;
    }

    public Page<Offer> getAllOffers(OfferSearchParamDTO searchDTO) {
        log.info("get all offers");
        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize());
        return offerRepository.searchOffers(
                searchDTO.getOfferName(),
                searchDTO.getMinUnitPrice(),
                searchDTO.getMaxUnitPrice(),
                searchDTO.getStatus(),
                searchDTO.getStartDate(),
                searchDTO.getEndDate(),
                pageable
        );
    }

    public Offer deleteOffer(String offerId) {
        log.info("delete. offer id={}", offerId);
        Offer offer = getOfferById(offerId);
        if (offer.getStatus() == Offer.OfferStatus.DELETED) {
            throw new BadRequestException(offerId + " is already deleted");
        } else {
            offer.setStatus(Offer.OfferStatus.DELETED);
        }
        return offerRepository.save(offer);
    }
}

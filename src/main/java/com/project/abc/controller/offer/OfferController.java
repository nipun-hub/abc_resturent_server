package com.project.abc.controller.offer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.abc.commons.ImageService;
import com.project.abc.dto.offer.OfferDTO;
import com.project.abc.dto.offer.OfferSearchParamDTO;
import com.project.abc.dto.offer.UpdateOfferDTO;
import com.project.abc.model.offer.Offer;
import com.project.abc.model.offer.OfferDetail;
import com.project.abc.service.offer.OfferDetailService;
import com.project.abc.service.offer.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/offer")
@RestController
@Slf4j
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferDetailService offerDetailService;

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/create-offer", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<OfferDTO> createOffer(
            @RequestPart("offer") String offerData,
            @RequestParam("image") MultipartFile image
    ) throws JsonProcessingException {
        OfferDTO offerDTO = new ObjectMapper().readValue(offerData, OfferDTO.class);
        String imageUrl = imageService.uploadImage(image);
        offerDTO.setImageUrl(imageUrl);
        Offer offer = offerService.createOffer(offerDTO);
        List<OfferDetail> offerDetails = offerDetailService.getOfferDetailsByOfferId(offer.getId());
        OfferDTO dto = OfferDTO.initWithOfferDetails(offer, offerDetails);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update-offer/{offerId}")
    public ResponseEntity<OfferDTO> updateOffer(
            @PathVariable String offerId,
            @RequestBody UpdateOfferDTO updateOfferDTO)
    {
        updateOfferDTO.validate();
        Offer updatedOffer = offerService.updateOffer(offerId, updateOfferDTO);
        List<OfferDetail> offerDetails = offerDetailService.getOfferDetailsByOfferId(updatedOffer.getId());
        OfferDTO dto = OfferDTO.initWithOfferDetails(updatedOffer, offerDetails);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/offers")
    public ResponseEntity<Page<OfferDTO>> getAllOffers(
            @RequestParam(value = "offerName", required = false) String offerName,
            @RequestParam(value = "status", required = false) Offer.OfferStatus status,
            @RequestParam(value = "minUnitPrice", required = false) Double minUnitPrice,
            @RequestParam(value = "maxUnitPrice", required = false) Double maxUnitPrice,
            @RequestParam(value = "startDate", required = false) Instant startDate,
            @RequestParam(value = "endDate", required = false) Instant endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        OfferSearchParamDTO searchParams = new OfferSearchParamDTO();
        searchParams.setOfferName(offerName);
        searchParams.setStatus(status);
        searchParams.setMinUnitPrice(minUnitPrice);
        searchParams.setMaxUnitPrice(maxUnitPrice);
        searchParams.setStartDate(startDate);
        searchParams.setEndDate(endDate);
        searchParams.setPage(page);
        searchParams.setSize(size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Offer> offersPage = offerService.getAllOffers(searchParams);

        List<OfferDTO> offerDTOs = offersPage.getContent().stream()
                .map(offer -> {
                    List<OfferDetail> offerDetails = offerDetailService.getOfferDetailsByOfferId(offer.getId());
                    return OfferDTO.initWithOfferDetails(offer, offerDetails);
                })
                .collect(Collectors.toList());

        Page<OfferDTO> offerDTOPage = new PageImpl<>(
                offerDTOs,
                pageable,
                offersPage.getTotalElements()
        );
        return ResponseEntity.ok(offerDTOPage);
    }

    @DeleteMapping("/delete/{offerId}")
    public ResponseEntity<OfferDTO> deleteItem(@PathVariable String offerId) {
        Offer offer = offerService.deleteOffer(offerId);
        List<OfferDetail> offerDetails = offerDetailService.getOfferDetailsByOfferId(offer.getId());
        return ResponseEntity.ok(OfferDTO.initWithOfferDetails(offer, offerDetails));
    }
}

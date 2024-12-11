package com.project.abc.dto.offer;

import com.project.abc.commons.BaseRequest;
import com.project.abc.dto.item.ItemDTO;
import com.project.abc.model.offer.Offer;
import com.project.abc.model.offer.OfferDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class OfferDTO extends BaseRequest {
    private String id;

    @Size(max = 40, min = 3, message = "Offer name length should be more than 3 and less than 40")
    @NotBlank(message = "Offer name is mandatory")
    private String offerName;

    @Size(max = 200, min = 5, message = "Description length should be more than 5 and less than 200")
    @NotBlank(message = "Description is mandatory")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Offer Unit price must be greater than zero")
    private Double offerUnitPrice;

    private String imageUrl;

    private Offer.OfferStatus status;

    private String startDate;

    private String endDate;

    private List<ItemDTO> items;

    public static OfferDTO init(Offer offer) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offer.getId());
        offerDTO.setOfferName(offer.getOfferName());
        offerDTO.setDescription(offer.getDescription());
        offerDTO.setOfferUnitPrice(offer.getOfferUnitPrice());
        offerDTO.setImageUrl(offer.getImageUrl());
        offerDTO.setStatus(offer.getStatus());
        return offerDTO;
    }

    public static OfferDTO initWithOfferDetails(Offer offer, List<OfferDetail> offerDetails) {
        OfferDTO offerDTO = OfferDTO.init(offer);
        if (!offerDetails.isEmpty()) {
            OfferDetail firstOfferDetail = offerDetails.get(0);
            offerDTO.setStartDate(firstOfferDetail.getStartDate());
            offerDTO.setEndDate(firstOfferDetail.getEndDate());
        }
        List<ItemDTO> itemDTOs = offerDetails.stream()
                .map(offerDetail -> {
                    ItemDTO itemDTO = ItemDTO.initWithCategory(offerDetail.getItem());;
                    itemDTO.setId(offerDetail.getItemId());
                    return itemDTO;
                })
                .collect(Collectors.toList());
        offerDTO.setItems(itemDTOs);
        return offerDTO;
    }
}

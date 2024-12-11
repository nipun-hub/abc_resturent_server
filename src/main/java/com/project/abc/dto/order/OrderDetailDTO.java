package com.project.abc.dto.order;

import com.project.abc.commons.BaseRequest;
import com.project.abc.dto.item.ItemDTO;
import com.project.abc.dto.offer.OfferDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Slf4j
public class OrderDetailDTO extends BaseRequest {
    private String id;

    @NotNull(message = "Quantity is mandatory")
    private int quantity;

    private ItemDTO item;

    private OfferDTO offer;
}

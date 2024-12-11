package com.project.abc.dto.offer;

import com.project.abc.commons.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Getter
@Setter
@Slf4j
public class UpdateOfferDTO extends BaseRequest {
    @Size(max = 40, min = 3, message = "Offer name length should be more than 3 and less than 40")
    private String offerName;

    @Size(max = 200, min = 5, message = "Description length should be more than 5 and less than 200")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Offer Unit price must be greater than zero")
    private Double offerUnitPrice;
}

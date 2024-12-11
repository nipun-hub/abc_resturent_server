package com.project.abc.dto.item;

import com.project.abc.commons.BaseRequest;
import com.project.abc.dto.category.CategoryDTO;
import com.project.abc.model.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;

@Getter
@Setter
@Slf4j
public class ItemDTO extends BaseRequest {
    private String id;

    @Size(max = 40, min = 3, message = "Item name length should be more than 3 and less than 40")
    @NotBlank(message = "Item name is mandatory")
    private String itemName;

    @Size(max = 200, min = 5, message = "Description length should be more than 5 and less than 200")
    @NotBlank(message = "Description is mandatory")
    private String description;

    private int rate;

    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than zero")
    private Double unitPrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "Discount percentage must be greater than or equal to 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount percentage must be less than or equal to 100")
    private Double discountPercentage;

    private String imageUrl;

    private Item.ItemStatus status;

    private CategoryDTO category;

    public static ItemDTO init(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setItemName(item.getItemName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setRate(item.getRate());
        itemDTO.setUnitPrice(item.getUnitPrice());
        itemDTO.setDiscountPercentage(item.getDiscountPercentage());
        itemDTO.setImageUrl(item.getImageUrl());
        itemDTO.setStatus(item.getStatus());
        return itemDTO;
    }

    public static ItemDTO initWithCategory(Item item) {
        ItemDTO itemDTO = ItemDTO.init(item);
        itemDTO.setCategory(CategoryDTO.init(item.getCategory()));
        return itemDTO;
    }

}

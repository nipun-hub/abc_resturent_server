package com.project.abc.dto.item;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ItemSearchParamDTO extends BaseRequest {
    private String itemName;
    private Item.ItemStatus status;
    private Double minPrice;
    private Double maxPrice;
    private String categoryName;
    private Integer rate;
    private int page;
    private int size;
}

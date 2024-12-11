package com.project.abc.dto.category;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.category.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class CategorySearchParamDTO extends BaseRequest {
    private String categoryName;
    private Category.CategoryStatus status;
    private int page;
    private int size;
}

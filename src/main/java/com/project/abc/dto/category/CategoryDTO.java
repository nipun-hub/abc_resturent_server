package com.project.abc.dto.category;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.category.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Slf4j
public class CategoryDTO extends BaseRequest {
    private String id;

    @Size(max = 40, min = 3, message = "Item name length should be more than 3 and less than 40")
    @NotBlank(message = "Category name is mandatory")
    private String categoryName;

    private Category.CategoryStatus status;

    public static CategoryDTO init(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;
    }
}

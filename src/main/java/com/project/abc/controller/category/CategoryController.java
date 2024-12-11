package com.project.abc.controller.category;

import com.project.abc.dto.category.CategoryCountDTO;
import com.project.abc.dto.category.CategoryDTO;
import com.project.abc.dto.category.CategorySearchParamDTO;
import com.project.abc.dto.category.UpdateCategoryDTO;
import com.project.abc.dto.item.ItemCountDTO;
import com.project.abc.model.category.Category;
import com.project.abc.service.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/category")
@RestController
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryDTO.validate();
        Category category = categoryService.createCategory(categoryDTO);
        CategoryDTO createCategoryDTO = CategoryDTO.init(category);
        return ResponseEntity.ok(createCategoryDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id);
        CategoryDTO categoryDTO = CategoryDTO.init(category);
        return ResponseEntity.ok(categoryDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @RequestBody UpdateCategoryDTO updateCategoryDTO,
            @PathVariable String id
    ) {
        updateCategoryDTO.validate();
        Category category = categoryService.updateCategory(updateCategoryDTO, id);
        CategoryDTO categoryDTO = CategoryDTO.init(category);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable String id) {
        Category category = categoryService.deleteCategory(id);
        return ResponseEntity.ok(CategoryDTO.init(category));
    }

    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "status", required = false) Category.CategoryStatus status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        CategorySearchParamDTO searchParams = new CategorySearchParamDTO();
        searchParams.setCategoryName(categoryName);
        searchParams.setStatus(status);
        searchParams.setPage(page);
        searchParams.setSize(size);

        Page<Category> categoryPage = categoryService.getAllCategories(searchParams);
        List<CategoryDTO> categoryDTOS = categoryPage.getContent().stream()
                .map(CategoryDTO::init)
                .collect(Collectors.toList());
        Page<CategoryDTO> categoryDTOPage = new PageImpl<>(
                categoryDTOS,
                PageRequest.of(page, size),
                categoryPage.getTotalElements()
        );
        return ResponseEntity.ok(categoryDTOPage);
    }

    @GetMapping("/category-count")
    public ResponseEntity<CategoryCountDTO> getCategoryCount() {
        int categoryCount = categoryService.getCategoryCount();
        CategoryCountDTO categoryCountDTO = new CategoryCountDTO();
        categoryCountDTO.setCategoryCount(categoryCount);
        return ResponseEntity.ok(categoryCountDTO);
    }
}

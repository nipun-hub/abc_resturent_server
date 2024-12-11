package com.project.abc.service.category;

import com.project.abc.commons.Check;
import com.project.abc.commons.exceptions.http.BadRequestException;
import com.project.abc.commons.exceptions.http.CategoryNotFoundException;
import com.project.abc.commons.exceptions.user.UserExType;
import com.project.abc.dto.category.CategoryDTO;
import com.project.abc.dto.category.CategorySearchParamDTO;
import com.project.abc.dto.category.UpdateCategoryDTO;
import com.project.abc.model.category.Category;
import com.project.abc.model.item.Item;
import com.project.abc.repository.category.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryDTO dto) {
        log.info("create category name {}", dto.getCategoryName());
        Category category = Category.init(dto);
        if (categoryRepository.findByCategoryNameAndStatusNot(dto.getCategoryName() , Category.CategoryStatus.DELETED).isPresent()) {
            throw new BadRequestException("category already exist", UserExType.EMAIL_ALREADY_EXIST);
        }
        return categoryRepository.save(category);
    }

    public Category getCategoryById(String categoryId) {
        log.info("Get category by id = {}", categoryId);
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Check.throwIfEmpty(categoryOptional, new CategoryNotFoundException("Category not found with Id : " + categoryId));
        Category category = categoryOptional.get();
        return category;
    }

    public Category updateCategory(UpdateCategoryDTO updateCategoryDTO, String categoryId) {
        log.info("updated category id {}", categoryId);
        Category category = this.getCategoryById(categoryId);
        if (categoryRepository.findByCategoryNameAndStatusNot(updateCategoryDTO.getCategoryName() , Category.CategoryStatus.DELETED).isPresent()) {
            throw new BadRequestException("email already exist", UserExType.EMAIL_ALREADY_EXIST);
        }
        category.setCategoryName(updateCategoryDTO.getCategoryName());
        return categoryRepository.save(category);
    }

    public Category deleteCategory(String categoryId) {
        log.info("delete. category id={}", categoryId);
        Category category = getCategoryById(categoryId);
        if (category.getStatus() == Category.CategoryStatus.DELETED) {
            throw new BadRequestException(categoryId + " is already deleted");
        } else {
            category.setStatus(Category.CategoryStatus.DELETED);
        }
        return categoryRepository.save(category);
    }

    public Page<Category> getAllCategories(CategorySearchParamDTO searchParams) {
        log.info("get all categories");
        Pageable pageable = PageRequest.of(searchParams.getPage(), searchParams.getSize());
        return categoryRepository.findCategories(
                searchParams.getCategoryName(),
                searchParams.getStatus(),
                pageable
        );
    }

    public int getCategoryCount() {
        log.info("get category count");
        int categoryCount = categoryRepository.countByStatus(Category.CategoryStatus.ACTIVE);
        return categoryCount;
    }
}

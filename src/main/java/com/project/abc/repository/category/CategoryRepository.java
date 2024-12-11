package com.project.abc.repository.category;

import com.project.abc.model.category.Category;
import com.project.abc.model.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByCategoryNameAndStatusNot(String name, Category.CategoryStatus status);

    @Query("SELECT c FROM category c " +
            "WHERE (:categoryName IS NULL OR c.categoryName LIKE %:categoryName%) " +
            "AND (:status IS NULL OR c.status = :status)")
    Page<Category> findCategories(
            @Param("categoryName") String categoryName,
            @Param("status") Category.CategoryStatus status,
            Pageable pageable
    );

    int countByStatus(Category.CategoryStatus status);
}

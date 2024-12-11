package com.project.abc.model.category;

import com.project.abc.dto.category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category")
public class Category {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public enum CategoryStatus {
        ACTIVE,
        INACTIVE,
        DELETED
    }

    public static Category init(CategoryDTO dto) {
        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setCategoryName(dto.getCategoryName());
        category.setStatus(CategoryStatus.ACTIVE);
        return category;
    }
}

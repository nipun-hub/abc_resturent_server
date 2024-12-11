package com.project.abc.model.item;

import javax.persistence.*;

import com.project.abc.dto.item.ItemDTO;
import com.project.abc.model.category.Category;
import com.project.abc.model.offer.OfferDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "item")
public class Item {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rate")
    private int rate;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "is_discount")
    private Double discountPercentage;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "item")
    private Set<OfferDetail> offerDetails;

    public enum ItemStatus {
        ACTIVE,
        INACTIVE,
        DELETED
    }

    public static Item init(ItemDTO dto) {
        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setItemName(dto.getItemName());
        item.setDescription(dto.getDescription());
        item.setRate(dto.getRate());
        item.setUnitPrice(dto.getUnitPrice());
        item.setDiscountPercentage(dto.getDiscountPercentage());
        item.setImageUrl(dto.getImageUrl());
        item.setStatus(ItemStatus.ACTIVE);
        return item;
    }

    public static Item initWithCategory(ItemDTO dto, Category category) {
        Item item = Item.init(dto);
        item.setCategory(category);
        return item;
    }
}

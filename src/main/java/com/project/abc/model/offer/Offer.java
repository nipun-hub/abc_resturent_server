package com.project.abc.model.offer;

import com.project.abc.dto.offer.OfferDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "offer")
public class Offer {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "offer_name", nullable = false)
    private String offerName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "unit_price")
    private Double offerUnitPrice;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "offer")
    private Set<OfferDetail> offerDetails;

    public enum OfferStatus {
        ACTIVE,
        INACTIVE,
        DELETED
    }

    public static Offer init(OfferDTO dto) {
        Offer offer = new Offer();
        offer.setId(UUID.randomUUID().toString());
        offer.setOfferName(dto.getOfferName());
        offer.setDescription(dto.getDescription());
        offer.setOfferUnitPrice(dto.getOfferUnitPrice());
        offer.setImageUrl(dto.getImageUrl());
        offer.setStatus(OfferStatus.ACTIVE);
        return offer;
    }
}

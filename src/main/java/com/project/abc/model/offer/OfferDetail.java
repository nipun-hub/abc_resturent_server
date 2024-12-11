package com.project.abc.model.offer;

import com.project.abc.model.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "offer_detail")
public class OfferDetail {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "offer_id", nullable = false)
    private String offerId;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "item_id", insertable = false, updatable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "offer_id", insertable = false, updatable = false)
    private Offer offer;
}

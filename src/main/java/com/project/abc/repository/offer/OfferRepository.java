package com.project.abc.repository.offer;

import com.project.abc.model.offer.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    Optional<Offer> findByOfferNameAndStatusNot(String offerName, Offer.OfferStatus status);

    @Query("SELECT DISTINCT o FROM offer o " +
            "JOIN offer_detail od ON o.id = od.offerId " +
            "WHERE (:offerName IS NULL OR o.offerName LIKE %:offerName%) " +
            "AND (:minPrice IS NULL OR o.offerUnitPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR o.offerUnitPrice <= :maxPrice) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:startDate IS NULL OR od.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR od.endDate <= :endDate)")
    Page<Offer> searchOffers(
            @Param("offerName") String offerName,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("status") Offer.OfferStatus status,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable
    );
}

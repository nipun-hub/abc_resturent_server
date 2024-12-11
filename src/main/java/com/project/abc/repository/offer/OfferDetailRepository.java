package com.project.abc.repository.offer;

import com.project.abc.model.offer.OfferDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferDetailRepository extends JpaRepository<OfferDetail, String> {

    List<OfferDetail> findByOfferId(String offerId);
}

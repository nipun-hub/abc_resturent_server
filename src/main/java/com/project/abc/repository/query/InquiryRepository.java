package com.project.abc.repository.query;

import com.project.abc.model.query.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, String> {
    @Query("SELECT i FROM inquiry i WHERE (:userId IS NULL OR i.user.id = :userId)")
    Page<Inquiry> searchInquiriesByUserId(
            @Param("userId") String userId,
            Pageable pageable
    );
}

package com.project.abc.repository.item;

import com.project.abc.model.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    Optional<Item> findByItemNameAndStatusNot(String itemName, Item.ItemStatus status);

    @Query("SELECT i FROM item i " +
            "JOIN i.category c " +
            "WHERE (:itemName IS NULL OR i.itemName LIKE %:itemName%) " +
            "AND (:status IS NULL OR i.status = :status) " +
            "AND (:minPrice IS NULL OR i.unitPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR i.unitPrice <= :maxPrice) " +
            "AND (:categoryName IS NULL OR c.categoryName = :categoryName)" +
            "AND (:rate IS NULL OR i.rate = :rate)")
    Page<Item> findItems(
            @Param("itemName") String itemName,
            @Param("status") Item.ItemStatus status,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("categoryName") String categoryName,
            @Param("rate") Integer rate,
            Pageable pageable
    );

    int countByStatus(Item.ItemStatus status);

}

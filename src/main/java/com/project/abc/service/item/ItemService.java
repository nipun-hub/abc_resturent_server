package com.project.abc.service.item;

import com.project.abc.commons.Check;
import com.project.abc.commons.exceptions.http.BadRequestException;
import com.project.abc.commons.exceptions.http.CategoryNotFoundException;
import com.project.abc.commons.exceptions.http.ItemNotFoundException;
import com.project.abc.commons.exceptions.item.ItemExType;
import com.project.abc.dto.item.ItemDTO;
import com.project.abc.dto.item.ItemRateDTO;
import com.project.abc.dto.item.ItemSearchParamDTO;
import com.project.abc.dto.item.ItemUpdateDTO;
import com.project.abc.model.category.Category;
import com.project.abc.model.item.Item;
import com.project.abc.repository.category.CategoryRepository;
import com.project.abc.repository.item.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Item createItem(ItemDTO itemDTO) {
        log.info("create item {}", itemDTO.getItemName());
        if (itemRepository.findByItemNameAndStatusNot(itemDTO.getItemName(), Item.ItemStatus.DELETED).isPresent()) {
            throw new BadRequestException("item already exist", ItemExType.ITEM_ALREADY_EXIST);
        }
        Optional<Category> category = categoryRepository.findById(itemDTO.getCategory().getId());
        Check.throwIfEmpty(category, new CategoryNotFoundException("Category not found with Id : " + itemDTO.getCategory().getId()));
        Item item = Item.initWithCategory(itemDTO, category.get());
        item.setImageUrl(itemDTO.getImageUrl());
        return itemRepository.save(item);
    }

    public Item getItemById(String itemId) {
        log.info("Get item by id = {}", itemId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        Check.throwIfEmpty(itemOptional, new ItemNotFoundException("Item not found with Id : " + itemId));
        Item item = itemOptional.get();
        return item;
    }

    public Item updateItem(ItemUpdateDTO itemUpdateDTO, String itemId) {
        log.info("updated item id {}", itemId);
        Item item = this.getItemById(itemId);
        item.setItemName(itemUpdateDTO.getItemName());
        item.setDescription(itemUpdateDTO.getDescription());
        item.setUnitPrice(itemUpdateDTO.getUnitPrice());
        item.setDiscountPercentage(itemUpdateDTO.getDiscountPercentage());
        item = itemRepository.save(item);
        return item;
    }

    public Page<Item> getAllItems(ItemSearchParamDTO searchParams) {
        log.info("get all items");
        Pageable pageable = PageRequest.of(searchParams.getPage(), searchParams.getSize());
        return itemRepository.findItems(
                searchParams.getItemName(),
                searchParams.getStatus(),
                searchParams.getMaxPrice(),
                searchParams.getMinPrice(),
                searchParams.getCategoryName(),
                searchParams.getRate(),
                pageable
        );
    }

    public Item deleteItem(String itemId) {
        log.info("delete. item id={}", itemId);
        Item item = getItemById(itemId);
        if (item.getStatus() == Item.ItemStatus.DELETED) {
            throw new BadRequestException(itemId + " is already deleted");
        } else {
            item.setStatus(Item.ItemStatus.DELETED);
        }
        return itemRepository.save(item);
    }

    public Item updateItemRate(ItemRateDTO itemRateDTO, String itemId) {
        log.info("updated item rate id {}", itemId);
        Item item = this.getItemById(itemId);
        item.setRate(itemRateDTO.getRate());
        item = itemRepository.save(item);
        return item;
    }

    public int getItemCount() {
        log.info("get item count");
        int itemCount = itemRepository.countByStatus(Item.ItemStatus.ACTIVE);
        return itemCount;
    }
}

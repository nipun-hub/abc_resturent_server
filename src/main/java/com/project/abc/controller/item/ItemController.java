package com.project.abc.controller.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.abc.commons.ImageService;
import com.project.abc.dto.item.*;
import com.project.abc.model.item.Item;
import com.project.abc.service.item.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/item")
@RestController
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/create-item", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ItemDTO> createItem(
            @RequestPart("item") String itemDetails,
            @RequestParam("image") MultipartFile image
    ) throws JsonProcessingException {
        ItemDTO itemDTO = new ObjectMapper().readValue(itemDetails, ItemDTO.class);
        itemDTO.validate();
        String imageUrl = imageService.uploadImage(image);
        itemDTO.setImageUrl(imageUrl);
        Item item = itemService.createItem(itemDTO);
        ItemDTO createItemDTO = ItemDTO.initWithCategory(item);
        return ResponseEntity.ok(createItemDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable String id) {
        Item item = itemService.getItemById(id);
        ItemDTO itemDTO = ItemDTO.initWithCategory(item);
        return ResponseEntity.ok(itemDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDTO> updateItem(
            @RequestBody ItemUpdateDTO itemUpdateDTO,
            @PathVariable String id
    ) {
        itemUpdateDTO.validate();
        Item item = itemService.updateItem(itemUpdateDTO, id);
        ItemDTO itemDTO = ItemDTO.initWithCategory(item);
        return ResponseEntity.ok(itemDTO);
    }

    @GetMapping("/items")
    public ResponseEntity<Page<ItemDTO>> getAllItems(
            @RequestParam(value = "itemName", required = false) String itemName,
            @RequestParam(value = "status", required = false) Item.ItemStatus status,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "rate", required = false) Integer rate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        ItemSearchParamDTO searchParams = new ItemSearchParamDTO();
        searchParams.setItemName(itemName);
        searchParams.setStatus(status);
        searchParams.setMinPrice(minPrice);
        searchParams.setMaxPrice(maxPrice);
        searchParams.setCategoryName(categoryName);
        searchParams.setRate(rate);
        searchParams.setPage(page);
        searchParams.setSize(size);

        Page<Item> itemsPage = itemService.getAllItems(searchParams);
        List<ItemDTO> itemDTOs = itemsPage.getContent().stream()
                .map(ItemDTO::initWithCategory)
                .collect(Collectors.toList());
        Page<ItemDTO> itemDTOPage = new PageImpl<>(
                itemDTOs,
                PageRequest.of(page, size),
                itemsPage.getTotalElements()
        );
        return ResponseEntity.ok(itemDTOPage);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<ItemDTO> deleteItem(@PathVariable String itemId) {
        Item item = itemService.deleteItem(itemId);
        return ResponseEntity.ok(ItemDTO.initWithCategory(item));
    }

    @PutMapping("/rate/{id}")
    public ResponseEntity<ItemDTO> updateItemRate(
            @RequestBody ItemRateDTO itemRateDTO,
            @PathVariable String id
    ) {
        itemRateDTO.validate();
        Item item = itemService.updateItemRate(itemRateDTO, id);
        ItemDTO itemDTO = ItemDTO.initWithCategory(item);
        return ResponseEntity.ok(itemDTO);
    }

    @GetMapping("/item-count")
    public ResponseEntity<ItemCountDTO> getItemCount() {
        int itemCount = itemService.getItemCount();
        ItemCountDTO itemCountDTO = new ItemCountDTO();
        itemCountDTO.setItemCount(itemCount);
        return ResponseEntity.ok(itemCountDTO);
    }
}

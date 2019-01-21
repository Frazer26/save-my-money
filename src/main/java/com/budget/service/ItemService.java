package com.budget.service;

import com.budget.model.Item;
import com.budget.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Optional<Item> getItemByIdAndMainCategoryId(Long id, Long mainCategoryId) {
        return itemRepository.findItemByIdAndMainCategory_Id(id,mainCategoryId);
    }

    public Optional<Item> getItemByIdAndSubCategoryId(Long id, Long subCategoryId) {
        return itemRepository.findItemByIdAndSubCategory_Id(id,subCategoryId);
    }

    public Item addItem(Item item) {
        return itemRepository.saveAndFlush(item);
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }
}

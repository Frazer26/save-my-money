package com.budget.service;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
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

    public Item addItem(Item item, Long id) {
        item.setId(id);
        return itemRepository.saveAndFlush(item);
    }

    public Item addItemUnderMainCategory(Item item, MainCategory mainCategory) {
        item.setMainCategory(mainCategory);
        return itemRepository.saveAndFlush(item);
    }

    public Item addItemUnderSubCategory(Item item, SubCategory subCategory) {
        item.setSubCategory(subCategory);
        return itemRepository.saveAndFlush(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteItemById(id);
    }
}

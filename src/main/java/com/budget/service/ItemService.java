package com.budget.service;

import com.budget.model.Item;
import com.budget.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}

package com.budget.service;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.repository.ItemRepository;
import com.budget.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.budget.model.Item.EMPTY;
import static com.budget.model.MainCategory.INCOME;
import static com.budget.model.MainCategory.SAVED_MONEY;

@Transactional
@Service
public class ItemService {

    private ItemRepository itemRepository;
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, SubCategoryRepository subCategoryRepository) {
        this.itemRepository = itemRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    private Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> getItemsUnderMainCategory(MainCategory mainCategory, LocalDate start, LocalDate end) {
        return itemRepository.findItemsByMainCategoryAndDateBetween(mainCategory, start, end);
    }

    public List<Item> getAllItemsWhereSubCategoryNotNull() {
        return itemRepository.findItemsBySubCategoryNotNull();
    }

    public Item saveItemUnderMainCategory(Item item, MainCategory mainCategory) {
        if (mainCategory.getMainCategory().equals("Income")) {
            item.setMainCategory(INCOME);
        } else if (mainCategory.getMainCategory().equals("Saved money")) {
            item.setMainCategory(SAVED_MONEY);
        } else {
            return EMPTY;
        }
        return itemRepository.save(item);
    }

    public Item saveItemUnderSubCategory(Item item, String subCategoryName) {
        SubCategory subCategory = subCategoryRepository.findSubCategoryByName(subCategoryName);
        if (subCategory != null) {
            item.setSubCategory(subCategory);
            return itemRepository.save(item);
        }
        return EMPTY;
    }

    public Item updateItem(Item item, Long id) {
        Optional<Item> itemFromDB = getItemById(id);

        if (itemFromDB.isEmpty()) {
            return EMPTY;
        }

        item.setId(id);
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteItemById(id);
    }
}

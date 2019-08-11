package com.budget.repository;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteItemById(Long id);

    List<Item> findItemsByMainCategory(MainCategory mainCategory);

    List<Item> findItemsBySubCategory(SubCategory subCategory);
}

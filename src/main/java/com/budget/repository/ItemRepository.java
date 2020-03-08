package com.budget.repository;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteItemById(Long id);

    List<Item> findItemsByMainCategoryAndDateBetween(MainCategory mainCategory, LocalDate start, LocalDate end);

    List<Item> findItemsBySubCategoryNotNull();
}

package com.budget.repository;

import com.budget.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByIdAndMainCategory_Id(Long id, Long mainCategoryId);

    Optional<Item> findItemByIdAndSubCategory_Id(Long id, Long subCategoryId);

    void deleteItemById(Long id);
}

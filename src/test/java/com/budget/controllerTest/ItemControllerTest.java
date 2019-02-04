package com.budget.controllerTest;

import com.budget.controller.ItemController;
import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.service.ItemService;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.assertEquals;

public class ItemControllerTest {

    private static final String SUB_CATEGORY_NAME = "Household";
    private static final String MAIN_CATEGORY_NAME = "Bills";
    private static final String ITEM_NAME = "Electricity";
    private static final Integer ITEM_VALUE = 5000;


    private SubCategoryService mockedSubCategoryService;
    private MainCategoryService mockedMainCategoryService;
    private ItemService mockedItemService;
    private ItemController itemController;

    @BeforeMethod
    public void setup() {
        mockedSubCategoryService = createMock(SubCategoryService.class);
        mockedMainCategoryService = createMock(MainCategoryService.class);
        mockedItemService = createMock(ItemService.class);
        itemController = new ItemController(mockedItemService, mockedMainCategoryService, mockedSubCategoryService);
    }

    /*TODO: testSaveItemInSubCategoryWhenDBNotContainsSubCategory
            testSaveItemInMainCategoryWhenDBNotContainsMainCategory
            testSaveItemInMainCategoryWhenDBContainsMainCategory
     */

    @Test
    public void testSaveItemInSubCategoryWhenDBContainsSubCategory() {
        SubCategory testSubCategory = createSubCategory();
        Item testItem = createItem();

        expect(mockedSubCategoryService.getSubCategoryById((long) 0)).andReturn(Optional.of(testSubCategory));
        expect(mockedItemService.addItem(anyObject())).andReturn(testItem);
        replay(mockedSubCategoryService, mockedItemService);

        Item item = itemController.saveItemInSubCategory(anyLong(), testItem);

        assertEquals(item, testItem);

        verify(mockedSubCategoryService, mockedItemService);
    }


    @Test
    public void testDeleteItemWhenItemDeletedFromDB() {
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);

        mockedItemService.deleteItem(anyLong());
        expectLastCall();
        replay(mockedItemService);

        ResponseEntity responseEntity = itemController.deleteItem(anyLong());

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedItemService);
    }


    @Test
    public void testUpdateItemWhenItemFoundInDB() {
        Item testItem = createItem();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);

        expect(mockedItemService.getItemById((long) 0)).andReturn(Optional.of(testItem));
        expect(mockedItemService.addItem(anyObject())).andReturn(testItem);
        replay(mockedItemService);

        ResponseEntity responseEntity = itemController.updateItem(anyLong(), testItem);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedItemService);
    }

    @Test
    public void testUpdateItemWhenItemNotFoundInDB() {
        Item testItem = createItem();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        expect(mockedItemService.getItemById((long) 0)).andReturn(Optional.empty());
        replay(mockedItemService);

        ResponseEntity responseEntity = itemController.updateItem(anyLong(), testItem);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedItemService);
    }

    private Item createItem() {
        Item item = new Item
                .ItemBuilder(ITEM_NAME, ITEM_VALUE)
                .build();
        return item;
    }

    private SubCategory createSubCategory() {
        MainCategory mainCategory = new MainCategory(MAIN_CATEGORY_NAME);
        SubCategory subCategory = new SubCategory(SUB_CATEGORY_NAME, mainCategory);
        return subCategory;
    }
}

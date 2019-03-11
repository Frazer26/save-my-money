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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
    private static final int STATUS_CODE_404 = 404;
    private static final long ID = 1L;
    private static final int STATUS_CODE_201 = 201;
    private static final String SAVE_ITEM_IN_MAINCAT_METHOD_ENDPOINT = "/budget/mainCategory/1/saveItem";
    private static final String SAVE_ITEM_IN_MAINCAT_METHOD_RETURNED_URI = "{Location=[http://localhost/budget/mainCategory/1/saveItem/1]}";
    private static final String SAVE_ITEM_IN_SUBCAT_METHOD_ENDPOINT = "/budget/subCategory/1/saveItem";
    private static final String SAVE_ITEM_IN_SUBCAT_METHOD_RETURNED_URI = "{Location=[http://localhost/budget/subCategory/1/saveItem/1]}";


    private SubCategoryService mockedSubCategoryService;
    private MainCategoryService mockedMainCategoryService;
    private ItemService mockedItemService;
    private ItemController itemController;
    private MockHttpServletRequest request;


    @BeforeMethod
    public void setup() {
        mockedSubCategoryService = createMock(SubCategoryService.class);
        mockedMainCategoryService = createMock(MainCategoryService.class);
        mockedItemService = createMock(ItemService.class);
        itemController = new ItemController(mockedItemService, mockedMainCategoryService, mockedSubCategoryService);
        request = new MockHttpServletRequest();
    }

    @Test
    public void testSaveItemInMainCategoryWhenMainCategoryNotExist() {
        expect(mockedMainCategoryService.getMainCategoryById(ID)).andReturn(Optional.empty());
        replay(mockedMainCategoryService);

        ResponseEntity<Object> responseEntity = itemController.saveItemInMainCategory(ID, createItem());

        assertEquals(STATUS_CODE_404, responseEntity.getStatusCode().value());

        verify(mockedMainCategoryService);
    }


    @Test
    public void testSaveItemInMainCategoryWhenItemUnderMainCategory() {
        Item testItem = createItem();
        request.setRequestURI(SAVE_ITEM_IN_MAINCAT_METHOD_ENDPOINT);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

        expect(mockedMainCategoryService.getMainCategoryById(ID)).andReturn(Optional.of(createMainCategory()));
        expect(mockedItemService.addItemUnderMainCategory(anyObject(), anyObject())).andReturn(testItem);
        replay(mockedMainCategoryService, mockedItemService);

        ResponseEntity<Object> responseEntity = itemController.saveItemInMainCategory(ID, testItem);

        assertEquals(SAVE_ITEM_IN_MAINCAT_METHOD_RETURNED_URI, responseEntity.getHeaders().toString());
        assertEquals(STATUS_CODE_201, responseEntity.getStatusCode().value());

        verify(mockedMainCategoryService, mockedItemService);
    }

    @Test
    public void testSaveItemInSubCategoryWhenSubCategoryNotExist() {
        expect(mockedSubCategoryService.getSubCategoryById(ID)).andReturn(Optional.empty());
        replay(mockedSubCategoryService);

        ResponseEntity<Object> responseEntity = itemController.saveItemInSubCategory(ID, createItem());

        assertEquals(STATUS_CODE_404, responseEntity.getStatusCode().value());

        verify(mockedSubCategoryService);
    }

    @Test
    public void testSaveItemInSubCategoryWhenItemUnderSubCategory() {
        Item testItem = createItem();
        request.setRequestURI(SAVE_ITEM_IN_SUBCAT_METHOD_ENDPOINT);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

        expect(mockedSubCategoryService.getSubCategoryById(ID)).andReturn(Optional.of(createSubCategory()));
        expect(mockedItemService.addItemUnderSubCategory(anyObject(), anyObject())).andReturn(testItem);
        replay(mockedSubCategoryService, mockedItemService);

        ResponseEntity<Object> responseEntity = itemController.saveItemInSubCategory(ID, testItem);

        assertEquals(SAVE_ITEM_IN_SUBCAT_METHOD_RETURNED_URI, responseEntity.getHeaders().toString());
        assertEquals(STATUS_CODE_201, responseEntity.getStatusCode().value());

        verify(mockedSubCategoryService, mockedItemService);
    }


    @Test
    public void testDeleteItemWhenItemDeletedFromDB() {
        mockedItemService.deleteItem(anyLong());
        expectLastCall();
        replay(mockedItemService);

        itemController.deleteItem(anyLong());

        verify(mockedItemService);
    }


    @Test
    public void testUpdateItemWhenItemFoundInDB() {
        Item testItem = createItem();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);

        expect(mockedItemService.getItemById(ID)).andReturn(Optional.of(testItem));
        expect(mockedItemService.addItem(anyObject())).andReturn(testItem);
        replay(mockedItemService);

        ResponseEntity responseEntity = itemController.updateItem(ID, testItem);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedItemService);
    }

    @Test
    public void testUpdateItemWhenItemNotFoundInDB() {
        Item testItem = createItem();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        expect(mockedItemService.getItemById(ID)).andReturn(Optional.empty());
        replay(mockedItemService);

        ResponseEntity responseEntity = itemController.updateItem(ID, testItem);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedItemService);
    }

    private Item createItem() {
        Item item = new Item
                .ItemBuilder(ITEM_NAME, ITEM_VALUE)
                .build();
        item.setId(ID);
        return item;
    }

    private SubCategory createSubCategory() {
        return new SubCategory(SUB_CATEGORY_NAME);
    }

    private MainCategory createMainCategory() {
        return new MainCategory(MAIN_CATEGORY_NAME);
    }
}

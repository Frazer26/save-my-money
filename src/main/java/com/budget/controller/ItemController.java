package com.budget.controller;

import com.budget.model.Item;
import com.budget.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/budget/saveItem", method = RequestMethod.POST)
    public Item newItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @RequestMapping(value = "/budget/deleteItem", method = RequestMethod.POST)
    public void deleteItem(@RequestBody Item item) {
        itemService.deleteItem(item);
    }
}

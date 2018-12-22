package com.budget.model;

import java.time.LocalDate;

public class Item{
    private String name;
    private int money;
    private LocalDate date;
    private SubCategory subCategory;
    private MainCategory mainCategory;

    private Item(ItemBuilder itemBuilder) {
        this.name = itemBuilder.name;
        this.money = itemBuilder.money;
        this.date = itemBuilder.date;
        this.subCategory = itemBuilder.subCategory;
        this.mainCategory = itemBuilder.mainCategory;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    @Override
    public String toString() {
        return name + " " + money + " " + date;
    }

    public static class ItemBuilder {
        private String name;
        private int money;
        private LocalDate date;
        private SubCategory subCategory;
        private MainCategory mainCategory;

        public ItemBuilder(String name, int money) {
            this.name = name;
            this.money = money;
        }

        public ItemBuilder attachDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public ItemBuilder belongsToSubCategory(SubCategory subCategory){
            this.subCategory = subCategory;
            return this;
        }

        public ItemBuilder belongsToMainCategory(MainCategory mainCategory){
            this.mainCategory = mainCategory;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}

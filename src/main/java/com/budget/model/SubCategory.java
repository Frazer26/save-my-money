package com.budget.model;

public class SubCategory {
    private String name;
    private int money;
    private MainCategory mainCategory;

    public SubCategory() {}

    public SubCategory(String name, int money, MainCategory mainCategory) {
        this.name = name;
        this.money = money;
        this.mainCategory = mainCategory;
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
        return name + " " + money;
    }
}

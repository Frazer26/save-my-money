package com.budget.model;


public class MainCategory {
    private String name;
    private int money;

    public MainCategory() {}

    public MainCategory(String name, int money) {
        this.name = name;
        this.money = money;
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

    @Override
    public String toString() {
        return name + " " + money;
    }
}

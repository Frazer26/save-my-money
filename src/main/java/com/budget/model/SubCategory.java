//package com.budget.model;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class SubCategory {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Integer id;
//
//    private String name;
//
//    private int money;
//
//    private MainCategory mainCategory;
//
//    public SubCategory() {}
//
//    public SubCategory(String name, int money, MainCategory mainCategory) {
//        this.name = name;
//        this.money = money;
//        this.mainCategory = mainCategory;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getMoney() {
//        return money;
//    }
//
//    public void setMoney(int money) {
//        this.money = money;
//    }
//
//    public MainCategory getMainCategory() {
//        return mainCategory;
//    }
//
//    public void setMainCategory(MainCategory mainCategory) {
//        this.mainCategory = mainCategory;
//    }
//
//    @Override
//    public String toString() {
//        return name + " " + money;
//    }
//}

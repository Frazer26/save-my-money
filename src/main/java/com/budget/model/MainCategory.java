package com.budget.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity  //This tells Hibernate to make a table out of this class
public class MainCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "mainCategory")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "mainCategory")
    private List<SubCategory> subCategories = new ArrayList<>();

    public MainCategory() {
    }

    public MainCategory(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(SubCategory subCategory) {
        this.subCategories.add(subCategory);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

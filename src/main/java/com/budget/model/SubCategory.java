package com.budget.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SubCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SUB_ID = "sub_id";
    public static final String MAIN_ID = "main_id";
    public static final String SUB_CATEGORY = "subCategory";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = SUB_ID)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = MAIN_ID, nullable = false)
    private MainCategory mainCategory;

    @OneToMany(mappedBy = SUB_CATEGORY)
    private List<Item> items = new ArrayList<>();

    public SubCategory() {
    }

    public SubCategory(String name) {
        this.name = name;
    }

    public SubCategory(String name, MainCategory mainCategory) {
        this.name = name;
        this.mainCategory = mainCategory;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
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

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    @Override
    public String toString() {
        return name;
    }
}

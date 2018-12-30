package com.budget.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity  //This tells Hibernate to make a table out of this class
public class MainCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "mainCategory", fetch = FetchType.LAZY)
    private Set<Item> items;

    @OneToMany(mappedBy = "mainCategory", fetch = FetchType.LAZY)
    private Set<SubCategory> subCategories = new HashSet<>();

    public MainCategory() {
    }

    public MainCategory(String name) {
        this.name = name;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(SubCategory subCategories) {
        this.subCategories.add(subCategories);
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

package com.budget.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SubCategory implements Serializable {

    public static final SubCategory EMPTY = new SubCategory();
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "mainCategory", columnDefinition = "varchar(255) default 'COST'", nullable = false)
    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;

    public SubCategory() {
    }

    public SubCategory(String name) {
        this.name = name;
    }

    public SubCategory(String name, MainCategory mainCategory) {
        this.name = name;
        this.mainCategory = mainCategory;
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

package com.budget.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private Integer money;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sub_id", nullable = false)
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "main_id")
    private MainCategory mainCategory;

    @Column(name = "is_repeat", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean repeat;

    private Item() {
    }

    private Item(ItemBuilder itemBuilder) {
        this.name = itemBuilder.name;
        this.money = itemBuilder.money;
        this.date = itemBuilder.date;
        this.subCategory = itemBuilder.subCategory;
        this.mainCategory = itemBuilder.mainCategory;
        this.repeat = itemBuilder.repeat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    @Override
    public String toString() {
        return name + " " + money + " " + date;
    }

    public static class ItemBuilder {
        private String name;
        private Integer money;
        private Date date;
        private SubCategory subCategory;
        private MainCategory mainCategory;
        private boolean repeat;

        public ItemBuilder(String name, Integer money) {
            this.name = name;
            this.money = money;
        }

        public ItemBuilder attachDate(Date date) {
            this.date = date;
            return this;
        }

        public ItemBuilder belongsToSubCategory(SubCategory subCategory) {
            this.subCategory = subCategory;
            return this;
        }

        public ItemBuilder belongsToMainCategory(MainCategory mainCategory) {
            this.mainCategory = mainCategory;
            return this;
        }

        public ItemBuilder isRepeat(boolean repeat) {
            this.repeat = repeat;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}

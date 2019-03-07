package com.budget.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ITEM_ID = "item_id";
    private static final String SUB_ID = "sub_id";
    private static final String MAIN_ID = "main_id";
    private static final String IS_REPEAT = "is_repeat";
    private static final String ORG_HIBERNATE_TYPE_NUMERIC_BOOLEAN_TYPE = "org.hibernate.type.NumericBooleanType";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ITEM_ID)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer money;

    private Date date;

    @ManyToOne
    @JoinColumn(name = SUB_ID)
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = MAIN_ID)
    private MainCategory mainCategory;

    @Column(name = IS_REPEAT, nullable = false)
    @Type(type = ORG_HIBERNATE_TYPE_NUMERIC_BOOLEAN_TYPE)
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

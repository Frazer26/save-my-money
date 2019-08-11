package com.budget.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MainCategory {

    INCOME("Income"),
    COST("Cost"),
    SAVED_MONEY("Saved money");

    private final String mainCategory;

    MainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getMainCategory() {
        return mainCategory;
    }
}

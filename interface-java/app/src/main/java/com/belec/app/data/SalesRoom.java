package com.belec.app.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalesRoom {

    private int id;
    private String categoryName;

    public SalesRoom(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return categoryName;
    }

    public void setCategory(String category) {
        this.categoryName = category;
    }

    public void setId(int id) {
        this.id = id;
    }
}

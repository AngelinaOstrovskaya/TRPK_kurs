package com.company.model;

import java.sql.Date;

public class BookIncome {
    private Integer B_number;

    private Date date;
    private String way;

    public Integer getB_number() {
        return B_number;
    }

    public String getWay() {
        return way;
    }

    public Date getDate() {
        return date;
    }

    public BookIncome(Integer b_number, Date date, String way) {
        B_number = b_number;
        this.date = date;
        this.way = way;
    }
}

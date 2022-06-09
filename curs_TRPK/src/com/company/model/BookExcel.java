package com.company.model;

import java.sql.Date;

public class BookExcel {
    public int id;
    public String name;
    public Date date;
    public String way;
    public String safety;
    public String fund;
    public String location;
    public Date I_date;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getWay() {
        return way;
    }

    public String getSafety() {
        return safety;
    }

    public String getFund() {
        return fund;
    }

    public String getLocation() {
        return location;
    }

    public Date getI_date() {
        return I_date;
    }

    public BookExcel(int id, String name, Date date, String way, String safety, String fund, String location, Date i_date) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.way = way;
        this.safety = safety;
        this.fund = fund;
        this.location = location;
        I_date = i_date;
    }


}

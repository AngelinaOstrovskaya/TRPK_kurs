package com.company.model;

import java.sql.Date;

public class Inventory {
    private Integer fund_id;
    private Integer loc_id;
    private Integer mus_id;
    private Date date;

    public Integer getFund_id() {
        return fund_id;
    }

    public Integer getLoc_id() {
        return loc_id;
    }

    public Integer getMus_id() {
        return mus_id;
    }

    public Date getDate() {
        return date;
    }

    public Inventory(Integer fund_id, Integer loc_id, Integer mus_id, Date date) {
        this.fund_id = fund_id;
        this.loc_id = loc_id;
        this.mus_id = mus_id;
        this.date = date;
    }
}

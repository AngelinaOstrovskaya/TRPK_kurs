package com.company.model;

import java.util.ArrayList;

public class DescriptionList {
    public static ArrayList<Description> invList= new ArrayList<>();
    public int getCount(){

        return this.invList.size();
    }
    public void clear() {
        this.invList.clear();
    }
}

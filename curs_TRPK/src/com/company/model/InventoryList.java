package com.company.model;

import java.util.ArrayList;

public class InventoryList {
    public static ArrayList<Inventory> invList= new ArrayList<>();
    public int getCount(){

        return this.invList.size();
    }
    public void clear() {
        this.invList.clear();
    }
}

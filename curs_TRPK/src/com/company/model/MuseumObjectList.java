package com.company.model;

import java.util.ArrayList;

public class MuseumObjectList {
    public ArrayList<MuseumObject> museumList= new ArrayList<>();
    public int getCount(){

        return this.museumList.size();
    }
    public void clear() {
        this.museumList.clear();
    }

}

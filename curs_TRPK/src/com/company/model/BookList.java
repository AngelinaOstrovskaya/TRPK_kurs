package com.company.model;

import java.util.ArrayList;

public class BookList {
    public static ArrayList<BookIncome> bookList= new ArrayList<>();
    public int getCount(){

        return this.bookList.size();
    }
    public void clear() {
        this.bookList.clear();
    }
}

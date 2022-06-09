package com.company.model;

public class MuseumObject {
    private Integer B_number;
    private String name;

//    private String I_number;
    private String size;
    private String material;
    private String safety;

    public Integer getB_number() {
        return B_number;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }

    public String getSafety() {
        return safety;
    }

    public MuseumObject(Integer b_number, String name, String size, String material, String safety ) {
        B_number = b_number;
        this.name = name;
        this.size = size;
        this.material = material;
        this.safety=safety;
    }
}

package com.company.model;

public class Description {
    private Integer M_id;
    private String avtor;
    private String description;

    public Integer getM_id() {
        return M_id;
    }

    public String getAvtor() {
        return avtor;
    }

    public String getDescription() {
        return description;
    }

    public Description(Integer m_id, String avtor, String description) {
        M_id = m_id;
        this.avtor = avtor;
        this.description = description;
    }
}

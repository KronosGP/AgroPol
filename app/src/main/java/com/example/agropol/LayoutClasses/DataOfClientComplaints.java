package com.example.agropol.LayoutClasses;

public class DataOfClientComplaints {

    private int id;
    private String status;

    public DataOfClientComplaints(int id, String status)
    {
        this.id=id;
        this.status=status;
    }

    public int getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
}

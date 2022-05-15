package com.example.agropol;

public class DataOfClientComplaints {

    private int id;
    private String status;

    DataOfClientComplaints(int id, String status)
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

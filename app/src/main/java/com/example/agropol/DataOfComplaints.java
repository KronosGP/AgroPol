package com.example.agropol;

public class DataOfComplaints {

    private int id;
    private String status;

    DataOfComplaints(int id, String status)
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

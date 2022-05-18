package com.example.agropol;

public class DataOfEmployeeComplaints {

    private int client_id;
    private int complaint_id;
    private String status;

    public DataOfEmployeeComplaints(int client_id, int complaint_id, String status)
    {
        this.client_id=client_id;
        this.complaint_id=complaint_id;
        this.status=status;
    }

    public int getClientId() {
        return client_id;
    }
    public int getComplaintId() {
        return complaint_id;
    }
    public String getStatus() {
        return status;
    }
}

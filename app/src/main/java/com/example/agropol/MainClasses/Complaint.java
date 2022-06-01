package com.example.agropol.MainClasses;

import android.content.Context;
import android.database.Cursor;

import com.example.agropol.LayoutClasses.DataOfClientComplaints;
import com.example.agropol.LayoutClasses.DataOfEmployeeComplaints;

import java.util.ArrayList;

public class Complaint {
    private int idClient;
    private int idRequest;
    private int idEmployee;
    private String contents;
    private String status;
    private String dateOfArComplaint;

    public void addComplaint(Context context,String IdC,String IdR,String C,String S,String DoC)
    {
        DBHelper dbHelper=new DBHelper(context);
        String[] col={"IDClient","IDRequest","Contents","Status","Date_of_Complaint"};
        String[] val={IdC,IdR,C,S,DoC};
        dbHelper.setData("complaint",col,val);
    }

    public void editComplaint(Context context,String where,String[] col,String[] val)
    {
        DBHelper dbHelper=new DBHelper(context);
        dbHelper.editData("complaint",where,col,val);
    }

    public ArrayList<DataOfClientComplaints> loadClientComplaint(Context context,ArrayList<DataOfClientComplaints> complaintItem,int IdUser)
    {
        DBHelper dbHelper=new DBHelper(context);
        Cursor result;
        if(IdUser==0)
            result=dbHelper.getDate("Select * from complaint");
        else
            result=dbHelper.getDate("Select * from complaint where IDClient="+IdUser);
        while (result.isAfterLast()==false)
        {
            complaintItem.add(new DataOfClientComplaints(result.getInt(0),result.getString(5)));
            result.moveToNext();
        }
        return complaintItem;
    }

    public ArrayList<DataOfEmployeeComplaints> loadEmployeeComplaint(Context context, ArrayList<DataOfEmployeeComplaints> complaintItem)
    {
        DBHelper dbHelper=new DBHelper(context);
        Cursor result=dbHelper.getDate("Select * from complaint");
        while(result.isAfterLast()==false)
        {
            int IdClient=result.getInt(1);
            int IdComplaint=result.getInt(0);
            String status=result.getString(5);
            complaintItem.add(new DataOfEmployeeComplaints(IdClient,IdComplaint,status));
            result.moveToNext();
        }
        return complaintItem;
    }
}

package com.example.agropol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DataOfEmployeeComplaintsAdapter extends RecyclerView.Adapter<DataOfEmployeeComplaintsAdapter.DataOfComplaintsViewHolder> {
    private List<DataOfEmployeeComplaints> dataOfEmployeeComplaints;
    private DataOfEmployeeComplaintsAdapter.OnItemClickListener adapterListener;

    public interface OnItemClickListener { ;
        void onShowClick(int position);
    }

    public void setOnItemClickListener(DataOfEmployeeComplaintsAdapter.OnItemClickListener listener) {
        adapterListener = listener;
    }


    public static class DataOfComplaintsViewHolder extends RecyclerView.ViewHolder {
        public TextView howClientId, howComplaintID, howStatus;

        public DataOfComplaintsViewHolder(View itemView, final DataOfEmployeeComplaintsAdapter.OnItemClickListener listener) {
            super(itemView);
            howClientId=itemView.findViewById(R.id.how_client_id);
            howComplaintID=itemView.findViewById(R.id.how_complaint_id);
            howStatus=itemView.findViewById(R.id.how_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShowClick(position);
                        }
                    }
                }
            });
        }
    }

    public DataOfEmployeeComplaintsAdapter(List<DataOfEmployeeComplaints> dataOfEmployeeComplaints) {
        this.dataOfEmployeeComplaints=dataOfEmployeeComplaints;
    }

    @Override
    public DataOfComplaintsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_data_of_employee_complaint, parent, false);
        DataOfComplaintsViewHolder docvh = new DataOfComplaintsViewHolder(v,adapterListener);
        return docvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DataOfComplaintsViewHolder holder, int position) {

        DataOfEmployeeComplaints currentItem = dataOfEmployeeComplaints.get(position);
        holder.howClientId.setText(holder.howClientId.getText()+String.valueOf(currentItem.getClientId()));
        holder.howComplaintID.setText(holder.howComplaintID.getText()+String.valueOf(currentItem.getComplaintId()));
        holder.howStatus.setText(holder.howStatus.getText()+currentItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return dataOfEmployeeComplaints.size();
    }

}

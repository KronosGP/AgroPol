package com.example.agropol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DataOfClientComplaintsAdapter extends RecyclerView.Adapter<DataOfClientComplaintsAdapter.DataOfComplaintsViewHolder> {
    private List<DataOfClientComplaints> dataOfClientComplaints;
    private DataOfClientComplaintsAdapter.OnItemClickListener adapterListener;

    public interface OnItemClickListener { ;
        void onShowClick(int position);
    }

    public void setOnItemClickListener(DataOfClientComplaintsAdapter.OnItemClickListener listener) {
        adapterListener = listener;
    }


    public static class DataOfComplaintsViewHolder extends RecyclerView.ViewHolder {
        public TextView howId, howStatus;

        public DataOfComplaintsViewHolder(View itemView, final DataOfClientComplaintsAdapter.OnItemClickListener listener) {
            super(itemView);
            howId=itemView.findViewById(R.id.how_id);
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

    public DataOfClientComplaintsAdapter(List<DataOfClientComplaints> dataOfClientComplaints) {
        this.dataOfClientComplaints = dataOfClientComplaints;
    }

    @Override
    public DataOfComplaintsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_data_of_client_complaint, parent, false);
        DataOfComplaintsViewHolder docvh = new DataOfComplaintsViewHolder(v,adapterListener);
        return docvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DataOfComplaintsViewHolder holder, int position) {

        DataOfClientComplaints currentItem = dataOfClientComplaints.get(position);
        holder.howId.setText(holder.howId.getText()+String.valueOf(currentItem.getId()));
        holder.howStatus.setText(holder.howStatus.getText()+currentItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return dataOfClientComplaints.size();
    }

}

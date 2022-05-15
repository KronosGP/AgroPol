package com.example.agropol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CatalogOfOrderEmployeeAdapter extends RecyclerView.Adapter<CatalogOfOrderEmployeeAdapter.CatalogOfOrderViewHolder> {
    private List<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders;
    private CatalogOfOrderEmployeeAdapter.OnItemClickListener adapterListener;

    public interface OnItemClickListener {
        void onShowClick(int position);
    }

    public void setOnItemClickListener(CatalogOfOrderEmployeeAdapter.OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class CatalogOfOrderViewHolder extends RecyclerView.ViewHolder {
        public TextView how_id, how_date_of_order, how_total_sum, how_status;

        public CatalogOfOrderViewHolder(View itemView, final CatalogOfOrderEmployeeAdapter.OnItemClickListener listener) {
            super(itemView);
            how_id=itemView.findViewById(R.id.how_id);
            how_date_of_order=itemView.findViewById(R.id.how_date_of_order);
            how_total_sum=itemView.findViewById(R.id.how_total_sum);
            how_status=itemView.findViewById(R.id.how_status);

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

    public CatalogOfOrderEmployeeAdapter(List<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders) {
        this.itemOfRecyclerViewOrders = itemOfRecyclerViewOrders;
    }

    @Override
    public CatalogOfOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_employee_order, parent, false);
        CatalogOfOrderViewHolder coovh = new CatalogOfOrderViewHolder(v, adapterListener);
        return coovh;
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogOfOrderEmployeeAdapter.CatalogOfOrderViewHolder holder, int position) {
        ItemOfRecyclerViewOrder currentItem = itemOfRecyclerViewOrders.get(position);
        holder.how_id.setText(holder.how_id.getText()+String.valueOf(currentItem.getId()));
        holder.how_date_of_order.setText(holder.how_date_of_order.getText()+currentItem.getDate());
        holder.how_total_sum.setText(holder.how_total_sum.getText()+String.valueOf(currentItem.getPrice())+"zł");
        holder.how_status.setText(holder.how_status.getText()+currentItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return itemOfRecyclerViewOrders.size();
    }

}

package com.example.agropol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CatalogOfOrderAdapter extends RecyclerView.Adapter<CatalogOfOrderAdapter.CatalogOfOrderViewHolder> {
    private List<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders;
    private CatalogOfOrderAdapter.OnItemClickListener adapterListener;

    public interface OnItemClickListener {
        void onShowClick(int position);
    }

    public void setOnItemClickListener(CatalogOfOrderAdapter.OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class CatalogOfOrderViewHolder extends RecyclerView.ViewHolder {
        public TextView how_id, how_date_of_order, how_total_sum;

        public CatalogOfOrderViewHolder(View itemView, final CatalogOfOrderAdapter.OnItemClickListener listener) {
            super(itemView);
            how_id=itemView.findViewById(R.id.how_id);
            how_date_of_order=itemView.findViewById(R.id.how_date_of_order);
            how_total_sum=itemView.findViewById(R.id.how_total_sum);

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

    public CatalogOfOrderAdapter(List<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders) {
        this.itemOfRecyclerViewOrders = itemOfRecyclerViewOrders;
    }

    @Override
    public CatalogOfOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_order, parent, false);
        CatalogOfOrderViewHolder coovh = new CatalogOfOrderViewHolder(v, adapterListener);
        return coovh;
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogOfOrderAdapter.CatalogOfOrderViewHolder holder, int position) {
        ItemOfRecyclerViewOrder currentItem = itemOfRecyclerViewOrders.get(position);
        holder.how_id.setText(holder.how_id.getText()+String.valueOf(currentItem.getId()));
        holder.how_date_of_order.setText(holder.how_date_of_order.getText()+currentItem.getDate());
        holder.how_total_sum.setText(holder.how_total_sum.getText()+String.valueOf(currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return itemOfRecyclerViewOrders.size();
    }

}

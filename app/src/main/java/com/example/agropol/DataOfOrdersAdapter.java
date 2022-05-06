package com.example.agropol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DataOfOrdersAdapter extends RecyclerView.Adapter<DataOfOrdersAdapter.DataOfOrdersViewHolder> {
    private List<DataOfOrders> dataOfOrders;


    public static class DataOfOrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView species, variety, quantity, price, sum;

        public DataOfOrdersViewHolder(View itemView) {
            super(itemView);
            species=itemView.findViewById(R.id.species);
            variety=itemView.findViewById(R.id.variety);
            quantity=itemView.findViewById(R.id.quantity);
            price=itemView.findViewById(R.id.price);
            sum=itemView.findViewById(R.id.sum);
        }
    }

    public DataOfOrdersAdapter(List<DataOfOrders> dataOfOrdersts) {
        this.dataOfOrders = dataOfOrdersts;
    }

    @Override
    public DataOfOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_data_of_orders, parent, false);
        DataOfOrdersViewHolder doovh = new DataOfOrdersViewHolder(v);
        return doovh;
    }

    @Override
    public void onBindViewHolder(@NonNull DataOfOrdersViewHolder holder, int position) {
        DataOfOrders currentItem = dataOfOrders.get(position);
        holder.species.setText(holder.species.getText()+currentItem.getSpecies());
        holder.variety.setText(holder.variety.getText()+currentItem.getVariety());
        holder.quantity.setText(holder.quantity.getText()+String.valueOf(currentItem.getQuantity()));
        holder.price.setText(holder.price.getText()+String.valueOf(currentItem.getPrice()));
        holder.sum.setText(holder.sum.getText()+String.valueOf(currentItem.getSum()));
    }

    @Override
    public int getItemCount() {
        return dataOfOrders.size();
    }

}

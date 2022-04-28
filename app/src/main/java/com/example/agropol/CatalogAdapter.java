package com.example.agropol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agropol.Plant;
import com.example.agropol.R;

import java.util.List;


public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogAdapterViewHolder> {
    private List<Plant> plants;
    private OnItemClickListener adapterListener;

    public interface OnItemClickListener { ;
        void onShowClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class CatalogAdapterViewHolder extends RecyclerView.ViewHolder {
        public ImageView howImage;
        public TextView howSpecies, howVariety, howQuantity, howPrice;

        public CatalogAdapterViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            howImage=itemView.findViewById(R.id.how_image);
            howSpecies=itemView.findViewById(R.id.how_species);
            howVariety=itemView.findViewById(R.id.how_variety);
            howQuantity=itemView.findViewById(R.id.how_quantity);
            howPrice=itemView.findViewById(R.id.how_price);


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

    public CatalogAdapter(List<Plant> plants) {
        this.plants = plants;
    }

    @Override
    public CatalogAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_catalog, parent, false);
        CatalogAdapterViewHolder cvh = new CatalogAdapterViewHolder(v, adapterListener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogAdapterViewHolder holder, int position) {
        Plant currentItem = plants.get(position);
        holder.howImage.setImageResource(currentItem.getImage());
        holder.howSpecies.setText(holder.howSpecies.getText()+currentItem.getSpecies());
        holder.howVariety.setText(holder.howVariety.getText()+currentItem.getVariety());
        holder.howQuantity.setText(holder.howQuantity.getText()+String.valueOf(currentItem.getQuantity()));
        holder.howPrice.setText(holder.howPrice.getText()+String.valueOf(currentItem.getPrice()) + " zł");

    }



    @Override
    public int getItemCount() {
        return plants.size();
    }

}

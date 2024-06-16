package edu.upc.dsa.android_upz_apocalypse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterInventory extends RecyclerView.Adapter<RecyclerViewAdapterInventory.ViewHolder> {

    private List<Item> itemList;
    private RecyclerClickViewListener listener;

    public RecyclerViewAdapterInventory(List<Item> itemList, RecyclerClickViewListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.idItem.setText(String.valueOf(item.getID()));
        holder.name.setText(item.getName());
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.description.setText(item.getDescription());
        if (item.getID() == 1) {
            holder.itemImage.setImageResource(R.drawable.item0);
        } else if (item.getID() == 2) {
            holder.itemImage.setImageResource(R.drawable.item1);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.recyclerViewListClicked(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idItem;
        public TextView name;
        public TextView price;
        public TextView description;
        public ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idItem = itemView.findViewById(R.id.idItem);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }

    public Item getItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            return itemList.get(position);
        }
        return null;
    }

}




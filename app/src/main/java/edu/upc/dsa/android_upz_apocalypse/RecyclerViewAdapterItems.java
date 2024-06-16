package edu.upc.dsa.android_upz_apocalypse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterItems extends RecyclerView.Adapter<RecyclerViewAdapterItems.ViewHolder> {
    private RecyclerClickViewListener listener;
    public List<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView id, name, description, price;
        public ImageView image;
        private RecyclerClickViewListener listener;

        public ViewHolder(@NonNull View itemView, RecyclerClickViewListener listener) {
            super(itemView);
            id = itemView.findViewById(R.id.idItem);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.itemImage);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.recyclerViewListClicked(this.getLayoutPosition());
        }
    }

    public RecyclerViewAdapterItems(List<Item> items, RecyclerClickViewListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.id.setText(String.valueOf(item.getID()));
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText(String.valueOf(item.getPrice()));

        if (item.getID() == 1) {
            holder.image.setImageResource(R.drawable.item0);
        } else if (item.getID() == 2) {
            holder.image.setImageResource(R.drawable.item1);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}


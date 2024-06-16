package edu.upc.dsa.android_upz_apocalypse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterInventory extends RecyclerView.Adapter<RecyclerViewAdapterInventory.ViewHolder>{
    private static RecyclerClickViewListener listener;
    public List<Item> inventory;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView ID;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ID =(TextView)itemView.findViewById(R.id.ID);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            listener.recyclerViewListClicked(this.getLayoutPosition());
        }
    }

    public RecyclerViewAdapterInventory(List<Item> inventory, RecyclerClickViewListener listener) {
        this.inventory = inventory;
        this.listener =listener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterInventory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inventory,parent,false);
        RecyclerViewAdapterInventory.ViewHolder viewHolder= new RecyclerViewAdapterInventory.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterInventory.ViewHolder holder, int position) {
        holder.ID.setText(inventory.get(position).getID());
    }

    @Override
    public int getItemCount() {
        return inventory.size();
    }
}

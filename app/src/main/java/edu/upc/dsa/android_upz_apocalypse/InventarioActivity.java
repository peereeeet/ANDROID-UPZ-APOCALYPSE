package edu.upc.dsa.android_upz_apocalypse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class InventarioActivity extends AppCompatActivity implements RecyclerClickViewListener  {
    ApiClient APIservice;
    String email;
    private RecyclerViewAdapterInventory adapterInventory;
    private RecyclerView recyclerViewInventory;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inventory);
        this.getEmail();

        recyclerViewInventory = findViewById(R.id.recyclerviewinventory);
        recyclerViewInventory.setLayoutManager(new LinearLayoutManager(this));

        Call<List<Item>> call = APIservice.getService().getInventory(this.email);
        try {
            adapterInventory = new RecyclerViewAdapterInventory(call.execute().body(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerViewInventory.setAdapter(adapterInventory);
    }
    public void returnFunction(View view) {
        Intent intent = new Intent(InventarioActivity.this, Perfil.class);
        startActivity(intent);
    }
    public void getEmail(){
        SharedPreferences sharedPreferences = getSharedPreferences("email", Context.MODE_PRIVATE);
        this.email = sharedPreferences.getString("email", null);
    }
    @Override
    public void recyclerViewListClicked(int position) {

    }

    @Override
    public void onClick(View view) {

    }

}
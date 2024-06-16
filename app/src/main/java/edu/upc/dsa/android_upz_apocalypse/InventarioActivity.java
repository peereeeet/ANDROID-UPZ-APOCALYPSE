package edu.upc.dsa.android_upz_apocalypse;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventarioActivity extends AppCompatActivity implements RecyclerClickViewListener {
    ApiClient APIservice;
    String email;
    int idItem;
    String name;
    String description;
    int price;
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

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Call<List<Item>> call = APIservice.getService().getInventory(email);
                try {
                    Response<List<Item>> response = call.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        List<Item> inventory = response.body();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapterInventory = new RecyclerViewAdapterInventory(inventory, InventarioActivity.this);
                                recyclerViewInventory.setAdapter(adapterInventory);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void returnFunction(View view) {
        Intent intent = new Intent(InventarioActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void getVariables(){
        SharedPreferences sharedPreferences = getSharedPreferences("Item", Context.MODE_PRIVATE);
        this.email = sharedPreferences.getString("idUser", null);
        this.idItem = sharedPreferences.getInt("idItem", 0);
        this.name = sharedPreferences.getString("name", null);
        this.description = sharedPreferences.getString("description", null);
        this.price = sharedPreferences.getInt("price", 0);
    }
    public void getEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("email", Context.MODE_PRIVATE);
        this.email = sharedPreferences.getString("email", null);
    }
    public void saveVariables(Item itemClicked, String idUser) {
        SharedPreferences sharedPreferences = getSharedPreferences("Item", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idItem", itemClicked.getID());
        editor.putString("name", itemClicked.getName());
        editor.putString("description", itemClicked.getDescription());
        editor.putInt("price", itemClicked.getPrice());
        editor.putString("idUser", idUser);
        editor.apply();
    }

    @Override
    public void recyclerViewListClicked(int position) {
        Item itemClicked = adapterInventory.getItem(position);
        saveVariables(itemClicked, this.email);

        Call<Integer> call = ApiClient.getService().cancelarCompra(this.email, itemClicked.getID());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch (response.code()) {
                    case 201:
                        Toast.makeText(InventarioActivity.this, "Cancelación exitosa", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(InventarioActivity.this, "Error inesperado", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(InventarioActivity.this, "Devolución realizada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InventarioActivity.this,InventarioActivity.class));

            }
        });
    }


    @Override
    public void onClick(View view) {
    }
}

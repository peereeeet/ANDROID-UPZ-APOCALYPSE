package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends AppCompatActivity implements RecyclerClickViewListener {

    ApiClient APIservice;
    String email;
    int idItem;
    String name;
    String description;
    int price;
    private RecyclerView recyclerViewItems;
    private RecyclerViewAdapterItems adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        this.getEmail();

        recyclerViewItems = findViewById(R.id.my_new_recycler_view);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        Call<List<Item>> call = APIservice.getService().getObjects();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    adapterItems = new RecyclerViewAdapterItems(response.body(), TiendaActivity.this);
                    recyclerViewItems.setAdapter(adapterItems);
                } else {
                    Toast.makeText(TiendaActivity.this, "Failed to fetch items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(TiendaActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void returnFunction(View view) {
        Intent intent = new Intent(TiendaActivity.this, MainActivity.class);
        startActivity(intent);
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

    public void getVariables(){
        SharedPreferences sharedPreferences = getSharedPreferences("Item", Context.MODE_PRIVATE);
        this.email = sharedPreferences.getString("idUser", null);
        this.idItem = sharedPreferences.getInt("idItem", 0);
        this.name = sharedPreferences.getString("name", null);
        this.description = sharedPreferences.getString("description", null);
        this.price = sharedPreferences.getInt("price", 0);
    }

    @Override
    public void recyclerViewListClicked(int position) {
        Item item = adapterItems.items.get(position);
        saveVariables(item, this.email);
        this.getVariables();
        Call<Integer> call = ApiClient.getService().comprarObjeto(this.email, this.idItem);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch (response.code()){
                    case 201:
                        Toast.makeText(TiendaActivity.this,"Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(TiendaActivity.this,"Insufficient money!", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(TiendaActivity.this,"Objeto ya en el inventario", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(TiendaActivity.this,"Unexpected error", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(TiendaActivity.this,"Compra realizada correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    public void getEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("email", Context.MODE_PRIVATE);
        this.email = sharedPreferences.getString("email", null);
        if (this.email != null) {
            Log.i("GETTING", this.email);
        } else {
            Log.i("GETTING", "email is null");
        }
    }
}

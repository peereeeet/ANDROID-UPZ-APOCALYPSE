package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTiendaActivity extends AppCompatActivity {
    TextView id, nombre, precio, descripcion;
    TextView monedas;
    Button bt_compra, bt_volver;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tienda);
        id = findViewById(R.id.IdOb);
        nombre = findViewById(R.id.NombreOb);
        descripcion = findViewById(R.id.ValorOb);
        precio = findViewById(R.id.PrecioOb);
        bt_compra = findViewById(R.id.bt_comprar);
        bt_volver = findViewById(R.id.bt_back);

        int idRecibido = getIntent().getExtras().getInt("Id");
        String nombreRecibido = getIntent().getExtras().getString("Nombre");
        int descripcionRecibido = getIntent().getExtras().getInt("Descripcion");
        int precioRecibido = getIntent().getExtras().getInt("Precio");

        id.setText("Id: " + idRecibido);
        nombre.setText("Nombre: " + nombreRecibido);
        descripcion.setText("Descripcion: " + descripcionRecibido);
        precio.setText("Precio : " + precioRecibido);
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        monedas.setText("Monedas :" + sharedPreferences.getInt("price",0));

        bt_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = new Object(idRecibido,nombreRecibido,precioRecibido,descripcionRecibido, sharedPreferences.getString("url", "a"));
                comprarObjeto(object,sharedPreferences.getString("email",null));
            }
        });
    }
    public void comprarObjeto(Object object,String email){
        Call<Object> BuyResponseCall = ApiClient.getService().comprarObjeto(object,email);
        BuyResponseCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()){
                    String message = "Compra completada con éxito";
                    Toast.makeText(DetailTiendaActivity.this,message,Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("monedas",sharedPreferences.getInt("price",0) - response.body().getPrecio());
                    editor.commit();

                    startActivity(new Intent(DetailTiendaActivity.this,TiendaActivity.class));
                    finish();;
                } else {
                    String message = "Ha ocurrido un error";
                    Toast.makeText(DetailTiendaActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DetailTiendaActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}
package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailTiendaActivity extends AppCompatActivity {

    TextView id, nombre, precio, valor;
    Button bt_compra, bt_volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tienda);
        id = findViewById(R.id.IdOb);
        nombre = findViewById(R.id.NombreOb);
        valor = findViewById(R.id.ValorOb);
        precio = findViewById(R.id.PrecioOb);
        bt_compra = findViewById(R.id.bt_comprar);
        bt_volver = findViewById(R.id.bt_back);

        String idRecibido = getIntent().getExtras().getString("Id");
        String nombreRecibido = getIntent().getExtras().getString("Nombre");
        String valorRecibido = getIntent().getExtras().getString("Valor");
        String precioRecibido = getIntent().getExtras().getString("Precio");

        id.setText("Name : " + idRecibido);
        nombre.setText("Description : " + nombreRecibido);
        valor.setText("Price : " + valorRecibido);
        precio.setText("Damage : " + precioRecibido);

        bt_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }
}
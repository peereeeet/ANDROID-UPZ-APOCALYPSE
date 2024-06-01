package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button bt_tienda, bt_perfil, bt_denuncia, bt_faqs, bt_consulta;
    SharedPreferences sharedPreferences;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_tienda = findViewById(R.id.bt_tienda);
        bt_perfil = findViewById(R.id.btn_perfil);
        bt_denuncia = findViewById(R.id.bt_denuncia);
        bt_faqs = findViewById(R.id.bt_faqs);
        bt_consulta = findViewById(R.id.bt_consulta);
        username = findViewById(R.id.username);

        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        username.setText("Nombre: " + sharedPreferences.getString("name",null));
        bt_tienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TiendaActivity.class));
            }
        });
        bt_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Perfil.class));
            }
        });

        bt_denuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DenunciaActivity.class));
            }
        });

        bt_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FAQsActivity.class));
            }
        });

        bt_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuestionActivity.class));
            }
        });
    }
}
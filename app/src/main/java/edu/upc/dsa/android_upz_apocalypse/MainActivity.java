package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button bt_tienda, bt_perfil, bt_denuncia, bt_faqs, bt_consulta, bt_inventario, bt_jugar;
    SharedPreferences sharedPreferences;
    String data;
    boolean respuesta = false;
    TextView username;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_tienda = findViewById(R.id.bt_tienda);
        bt_perfil = findViewById(R.id.btn_perfil);
        bt_denuncia = findViewById(R.id.bt_denuncia);
        bt_faqs = findViewById(R.id.bt_faqs);
        bt_consulta = findViewById(R.id.bt_consulta);
        bt_inventario = findViewById(R.id.bt_inventario);
        bt_jugar = findViewById(R.id.bt_jugar);
        username = findViewById(R.id.username);
        this.getEmail();

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        username.setText("Nombre: " + sharedPreferences.getString("name", null));

        bt_tienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEmail(email);
                Intent intent = new Intent(MainActivity.this, TiendaActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
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

        bt_inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmail(email);
                Intent intent = new Intent(MainActivity.this, InventarioActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        bt_jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                getMapa(1);

                i.setComponent(new ComponentName("com.upzapocalypse.UnityPlugin", "com.unity3d.player.UnityPlayerActivity"));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i.putExtra("input", data);
                        startActivity(i);
                    }
                }, 3000);
            }
        });
    }

    private void getEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("email", Context.MODE_PRIVATE);
        this.email = sharedPreferences.getString("email", null);
    }

    public void saveEmail(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("email", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();

        if (email != null) {
            Log.i("SAVING", email);
        } else {
            Log.i("SAVING", "email is null");
        }
    }

    public void getMapa(int idMapa) {
        Call<MapaResponse> mapaResponseCall = ApiClient.getService().getMapa(idMapa);
        mapaResponseCall.enqueue(new Callback<MapaResponse>() {
            @Override
            public void onResponse(Call<MapaResponse> call, Response<MapaResponse> response) {
                if (response.isSuccessful()) {
                    String message = "Éxito";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    data = response.body().getMapaF();
                    respuesta = true;
                } else {
                    String message = "Ha ocurrido un error";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MapaResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

package edu.upc.dsa.android_upz_apocalypse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQsActivity extends AppCompatActivity {

    Button bt_backFAQs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        bt_backFAQs = findViewById(R.id.bt_volverFAQs);
        bt_backFAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Regresa a la actividad anterior
            }
        });

        obtenerPreguntasFrequentes();
    }

    private void obtenerPreguntasFrequentes() {
        Call<List<FAQ>> LFAQs = ApiClient.getService().obtenerPreguntasFrequentes();

        LFAQs.enqueue(new Callback<List<FAQ>>() {
            @Override
            public void onResponse(Call<List<FAQ>> call, Response<List<FAQ>> response) {
                if (response.isSuccessful()) {
                    List<FAQ> faqs = response.body();
                    ArrayAdapter<FAQ> adapter = new ArrayAdapter<>(FAQsActivity.this, android.R.layout.simple_list_item_1, faqs);

                    ListView listViewFAQs = findViewById(R.id.listViewFAQs);
                    listViewFAQs.setAdapter(adapter);
                } else {
                    String message = "Ha ocurrido un error";
                    Toast.makeText(FAQsActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<FAQ>> call, Throwable t) {
                String message = "Error de conexión";
                Toast.makeText(FAQsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DenunciaActivity extends AppCompatActivity {

    TextInputEditText editTextFecha, editTextTitulo, editTextMensaje;
    Button button_enviar, button_backDen;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);
        editTextFecha = findViewById(R.id.fechaDen);
        editTextMensaje = findViewById(R.id.mensajeDen);
        editTextTitulo = findViewById(R.id.tituloDen);
        button_enviar = findViewById(R.id.bt_enviarDen);
        button_backDen = findViewById(R.id.bt_backDen);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        button_backDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DenunciaActivity.this,MainActivity.class));
            }
        });

        button_enviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Denuncia denuncia = new Denuncia(editTextFecha.getText().toString(),editTextTitulo.getText().toString(), editTextMensaje.getText().toString(), sharedPreferences.getString("name", null));
                sendDenuncia(denuncia);
            }
        });
    }

    public void sendDenuncia(Denuncia denuncia){
        Call<Void> denunciaResponse = ApiClient.getService().addDenuncia(denuncia);
        denunciaResponse.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    String msg = "Éxito";
                    Toast.makeText(DenunciaActivity.this,msg,Toast.LENGTH_LONG).show();
                }else {
                    String msg = "Error";
                    Toast.makeText(DenunciaActivity.this,msg,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String msg = "Error" + t.getMessage();
                Toast.makeText(DenunciaActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });
    }
}
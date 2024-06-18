package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextUtils;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizarPerfil extends AppCompatActivity {

    TextInputEditText editTextName, editTextPassword;
    Button button_save, button_volver;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_perfil);

        editTextName = findViewById(R.id.nameActualizar);
        editTextPassword = findViewById(R.id.passwordActualizar);
        button_save = findViewById(R.id.btn_save);
        button_volver = findViewById(R.id.btn_volverActualizar);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        String email = sharedPreferences.getString("email", null);

        button_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editTextName.getText()) &&
                        !TextUtils.isEmpty(editTextPassword.getText())) {
                    String newName = editTextName.getText().toString();
                    String newPassword = editTextPassword.getText().toString();

                    updateUser(email, newName, newPassword);
                } else {
                    Toast.makeText(ActualizarPerfil.this, "Algunos elementos de la vista son nulos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateUser(String email, String newName, String newPassword) {
        Call<Void> updateResponseCall = ApiClient.getService().updateUsers(email, newName, newPassword);
        updateResponseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 201:
                        String msg = "Éxito";
                        Toast.makeText(ActualizarPerfil.this, msg, Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", newName);
                        editor.putString("password", newPassword);
                        editor.apply();
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case 404:
                        Toast.makeText(ActualizarPerfil.this, "Usuario no encontrado", Toast.LENGTH_LONG).show();
                        break;
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ActualizarPerfil.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

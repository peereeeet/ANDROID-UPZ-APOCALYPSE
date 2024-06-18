package edu.upc.dsa.android_upz_apocalypse;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EliminarActivity extends AppCompatActivity  {
    TextInputEditText editTextEmail, editTextPassword;
    Button button_enviar, button_backDen;
    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_perfil);
        editTextEmail = findViewById(R.id.emailEliminar);
        editTextPassword = findViewById(R.id.passwordEliminar);
        button_enviar = findViewById(R.id.btn_save);
        button_backDen = findViewById(R.id.btn_volverEliminar);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        button_backDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EliminarActivity.this,HomeActivity.class));
            }
        });

        button_enviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showConfirmationDialog(view);
            }
        });
    }
    public void showConfirmationDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que desea elminar esta cuenta? Todos sus datos serán eliminados.")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Eliminar eliminar = new Eliminar(editTextEmail.getText().toString(),editTextPassword.getText().toString());
                        sendEliminar(eliminar);                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void sendEliminar(Eliminar eliminar){
        Call<Void> eliminarResponse = ApiClient.getService().deleteUsers(eliminar);
        eliminarResponse.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 201:
                        Toast.makeText(EliminarActivity.this, "Éxito", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(EliminarActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(EliminarActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(EliminarActivity.this, "Error inesperado", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String msg = "Error" + t.getMessage();
                Toast.makeText(EliminarActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });
    }
}

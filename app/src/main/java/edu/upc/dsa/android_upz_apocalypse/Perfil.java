package edu.upc.dsa.android_upz_apocalypse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {

    TextView editName, editMail, editPassword;
    SharedPreferences sharedPreferences;
    Button button_logout, button_borrar, button_update, button_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        editName = findViewById(R.id.namePerfil);
        editMail = findViewById(R.id.mailPerfil);
        editPassword = findViewById(R.id.passwordPerfil);
        button_logout = findViewById(R.id.btn_logOut);
        button_borrar = findViewById(R.id.btn_borrar);
        button_update = findViewById(R.id.btn_actualizar);
        button_volver = findViewById(R.id.btn_volverPerfl);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        updateProfileData();

        button_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Perfil.this, MainActivity.class));
            }
        });

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(Perfil.this, HomeActivity.class));
                finish();
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, ActualizarPerfil.class);
                startActivityForResult(intent, 1);
            }
        });

        button_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, EliminarActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            updateProfileData();
        }
    }

    private void updateProfileData() {
        editName.setText("Nombre: " + sharedPreferences.getString("name", ""));
        editMail.setText("Mail: " + sharedPreferences.getString("email", ""));
        editPassword.setText("Password: " + sharedPreferences.getString("password", ""));
    }
}

package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editTextUser, editTextPassword;
    Button bt_login, bt_registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUser = findViewById(R.id.nombre);
        editTextPassword = findViewById(R.id.contrasena);
        bt_login = findViewById(R.id.bt_login);
        bt_registrar = findViewById(R.id.bt_registrar);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextPassword.getText().toString()) || TextUtils.isEmpty(editTextUser.getText().toString())){
                    String message = "Rellene todos los campos";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }
                else {
                    loginUser(editTextUser.getText().toString(),editTextPassword.getText().toString());
                }
            }
        });

        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrarActivity.class));
            }
        });


    }

    public void loginUser(String name, String password){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUsers(name, password);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    String message = "Éxito";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                } else {
                    String message = "Ha ocurrido un error";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });

    }
}
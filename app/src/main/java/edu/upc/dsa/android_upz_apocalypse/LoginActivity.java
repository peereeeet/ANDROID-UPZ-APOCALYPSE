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
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUser = findViewById(R.id.usuario);
        editTextPassword = findViewById(R.id.contrasena);
        bt_login = findViewById(R.id.bt_login);
        bt_registrar = findViewById(R.id.bt_registrar);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextPassword.getText().toString()) || TextUtils.isEmpty(editTextUser.getText().toString())){
                    String message = "Rellene todos los campos";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }
                else {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsuario(editTextUser.getText().toString());
                    loginRequest.setPassword(editTextPassword.getText().toString());
                    loginUser(loginRequest);
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

    public void loginUser(LoginRequest loginRequest){
        Call<UsuarioResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful()){
                    String message = "Successful";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("mail", loginRequest.getEmail());
                    editor.putString("password",loginRequest.getPassword());
                    editor.putString("username", response.body().getUsername());
                    editor.putString("name", response.body().getName());
                    editor.putString("lastName", response.body().getLastname());
                    editor.putInt("bolivares",response.body().getBolivares());
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
                else {
                    String message = "An error occurred";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });

    }
}
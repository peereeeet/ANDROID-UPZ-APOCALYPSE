package edu.upc.dsa.android_upz_apocalypse;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class QuestionActivity extends AppCompatActivity{
    TextView dateVal;
    TextView messageVal;
    TextView titleVal;
    TextView senderVal;
    Button entregaButton, bt_back;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        dateVal = findViewById(R.id.date);
        messageVal = findViewById(R.id.message);
        titleVal = findViewById(R.id.title);
        senderVal = findViewById(R.id.remitente);
        entregaButton = findViewById(R.id.entregaButton);
        bt_back = findViewById(R.id.bt_backConsulta);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionActivity.this,MainActivity.class));
            }
        });

        entregaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(dateVal.getText().toString()) || TextUtils.isEmpty(messageVal.getText().toString()) || TextUtils.isEmpty(titleVal.getText().toString())|| TextUtils.isEmpty(senderVal.getText().toString())) {
                    String message = "Rellene todos los campos";
                    Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
                } else {
                    Question consulta = new Question();
                    consulta.setDate(dateVal.getText().toString());
                    consulta.setMessage(messageVal.getText().toString());
                    consulta.setTitle(titleVal.getText().toString());
                    consulta.setSender(senderVal.getText().toString());
                    realizarConsulta(consulta);
                }
            }
        });
    }

    public void realizarConsulta(Question consulta) {
        Call<Void> q = ApiClient.getService().realizarConsulta(consulta);
        q.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message = "Éxito";
                    Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                    startActivity(new Intent(QuestionActivity.this, MainActivity.class));

                } else {
                    String message = "Ha ocurrido un error";
                    Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("NO", "onFailure", t);
                String message = t.getLocalizedMessage();
                Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

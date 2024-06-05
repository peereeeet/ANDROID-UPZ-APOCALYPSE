package edu.upc.dsa.android_upz_apocalypse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.content.SharedPreferences;
import com.squareup.picasso.Picasso;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventarioActivity extends AppCompatActivity {
    RecyclerView recycle;
    TextView titulo;
    SharedPreferences sharedPreferences;
    Button bt_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        recycle = findViewById(R.id.recycle);
        titulo = findViewById(R.id.titulo);
        bt_volver = findViewById(R.id.bt_volverInventario);

        bt_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InventarioActivity.this,MainActivity.class));
            }
        });

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        titulo.setText("Inventario de " + sharedPreferences.getString("name",null));

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(llm);

        Call<List<Object>> objectlistcall = ApiClient.getService().getUserObjects(email);
        objectlistcall.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    List<Object> objectList = response.body();
                    recycleadapter adapter = new recycleadapter(InventarioActivity.this,objectList);
                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class recycleadapter extends RecyclerView.Adapter<recycleadapter.MyViewHolder> implements View.OnClickListener{
        List<Object> list;
        private Context contexto;
        private View.OnClickListener listener;
        public recycleadapter(Context contexto, List<Object> list){
            this.contexto = contexto;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,null);
            recycleadapter.MyViewHolder viewHolder = new recycleadapter.MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.id.setText("Id: " + list.get(position).getId());
            holder.nombre.setText("Nombre: " + list.get(position).getNombre());
            holder.descripcion.setText("Descripción: " + list.get(position).getDescripcion());
            holder.precio.setText("Precio: " + list.get(position).getPrecio());
            Picasso.with(contexto)
                    .load(list.get(position).getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .into(holder.image);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
                listener.onClick(view);
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView id, nombre, descripcion, precio;
            ImageView image;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                nombre = itemView.findViewById(R.id.nombre);
                precio = itemView.findViewById(R.id.precio);
                descripcion = itemView.findViewById(R.id.descripcion);
                image = itemView.findViewById(R.id.image);
            }
        }
    }
}
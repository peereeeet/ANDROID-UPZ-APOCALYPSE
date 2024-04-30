package edu.upc.dsa.android_upz_apocalypse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("usuarios/{name}&{password}")
    Call<LoginResponse> loginUsers(@Path("name") String name, @Path("password") String password);
    @POST("usuarios/")
    Call<RegistrarResponse> registrarUsers(@Body RegistrarRequest registerRequest);
    @GET("tienda/objetos")
    Call<List<Object>> getObjects();
}
package edu.upc.dsa.android_upz_apocalypse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {

    @POST("game/usuarios/login")
    Call<UsuarioResponse> loginUser(@Body LoginRequest loginRequest);
    @POST("game/usuarios/register")
    Call<RegistrarResponse> registrarUsers(@Body RegistrarRequest registerRequest);
    @GET("gametienda/objetos")
    Call<List<Object>> getObjects();
    @PUT("game/tienda/comprarObjeto/{mail}")
    Call<Object> comprarObjeto(@Body Object object,@Path("name") String name);
}
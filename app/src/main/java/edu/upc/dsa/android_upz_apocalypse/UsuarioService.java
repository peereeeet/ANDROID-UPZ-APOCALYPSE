package edu.upc.dsa.android_upz_apocalypse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {
    @POST("game/usuarios/login")
    Call<UsuarioResponse> loginUser(@Body LoginRequest loginRequest);
    @POST("game/usuarios/register")
    Call<RegistrarResponse> registrarUsers(@Body RegistrarRequest registerRequest);
    @POST("game/usuarios/delete")
    Call<Void> deleteUsers(@Body Eliminar eliminar);
    @PUT("game/usuarios/actualizar/{email}/{name}/{password}")
    Call<Void> updateUsers(@Path("email") String email, @Path("name") String newName, @Path("password") String newPassword);
    @GET("game/tienda/objetos")
    Call<List<Item>> getObjects();
    @PUT("game/tienda/comprarObjeto/{email}/{idItem}")
    Call<Integer> comprarObjeto(@Path("email") String email, @Path("idItem") int idItem);
    @PUT("game/denuncia/addDenuncia")
    Call<Void> addDenuncia(@Body Denuncia denuncia);
    @POST("game/question/addConsulta")
    Call<Void> realizarConsulta(@Body Question consulta);
    @GET("game/FAQs")
    Call<List<FAQ>> obtenerPreguntasFrequentes ();
    @GET("game/inventory/{email}")
    Call<List<Item>> getInventory(@Path("email") String email);
    @PUT("game/tienda/cancelarCompra/{email}/{idItem}")
    Call<Integer> cancelarCompra(@Path("email") String email, @Path("idItem") int idItem);
    @GET("usuarios/mapa/{idMapa}")
    Call<MapaResponse> getMapa(@Path("idMapa")int idMapa);

}
package net.ramastudio.mytens.utils.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("nik") String email,
                                    @Field("no_hp") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("nik") String idperkara,
                                       @Field("no_hp") String nama,
                                       @Field("password") String email);
}


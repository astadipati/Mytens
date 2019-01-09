package net.ramastudio.mytens.utils.api;

import net.ramastudio.mytens.model.AccessToken;
import net.ramastudio.mytens.utils.token.TokenManager;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class CustomAuth implements Authenticator {

    private TokenManager tokenManager;
    private static CustomAuth INSTANCE;

    private CustomAuth(TokenManager tokenManager){
        this.tokenManager = tokenManager;
    }

    static synchronized CustomAuth getInstance(TokenManager tokenManager){
        if(INSTANCE == null){
            INSTANCE = new CustomAuth(tokenManager);
        }

        return INSTANCE;
    }


    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        if(responseCount(response) >= 3){
            return null;
        }

        AccessToken token = tokenManager.getToken();

        ApiService service = RetrofitBuilder.createService(ApiService.class);
        Call<AccessToken> call = service.refresh(token.getRefreshToken() + "a");
        retrofit2.Response<AccessToken> res = call.execute();

        if(res.isSuccessful()){
            AccessToken newToken = res.body();
            tokenManager.saveToken(newToken);

            return response.request().newBuilder().header("Authorization", "Bearer " + res.body().getAccessToken()).build();
        }else{
            return null;
        }
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
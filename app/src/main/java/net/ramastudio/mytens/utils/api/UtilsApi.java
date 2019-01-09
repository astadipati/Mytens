package net.ramastudio.mytens.utils.api;

public class UtilsApi {

//    tuban punya
    public static final String BASE_URL_API = "http://192.168.1.155/ci-rest-jwt2/api/user/";

    // Mendeklarasikan Interface BaseApiService -> return model
    public static ApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }
//    // return string
//    public static ApiService getAPIServiceString(){
//        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
//    }
}

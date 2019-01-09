package net.ramastudio.mytens.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.facebook.login.Login;

import net.ramastudio.mytens.R;
import net.ramastudio.mytens.model.AccessToken;
import net.ramastudio.mytens.model.ApiError;
import net.ramastudio.mytens.utils.api.ApiService;
import net.ramastudio.mytens.utils.api.ApiUtils;
import net.ramastudio.mytens.utils.api.RetrofitBuilder;
import net.ramastudio.mytens.utils.token.TokenManager;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.til_loggnohp)
    TextInputLayout tilnohp;
    @BindView(R.id.til_logpassword)
    TextInputLayout tilpass;

    ApiService service;
    TokenManager tokenManager;
    AwesomeValidation validator;
    Call<AccessToken> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        service = RetrofitBuilder.createService(ApiService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("pref",MODE_PRIVATE));
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        setupRules();

        if (tokenManager.getToken().getAccessToken() != null ){
            startActivity(new Intent(LoginActivity.this, DataumumActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btn_login)
    void login(){
        String nohp = tilnohp.getEditText().getText().toString();
        String password = tilpass.getEditText().getText().toString();

        tilnohp.setError(null);
        tilpass.setError(null);

        validator.clear();

        if (validator.validate()){
//            showLoading();
            call = service.login(nohp, password);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.w(TAG, "OnResponse: "+response);
                    if (response.isSuccessful()){
                        tokenManager.saveToken(response.body());
                        startActivity(new Intent(LoginActivity.this, DataumumActivity.class));
                        finish();
                    }else {
                        if (response.code() == 422){
                            handleErrors(response.errorBody());
                        }
                        if (response.code() == 401){
                            ApiError apiError = ApiUtils.converErrors(response.errorBody());
                            Toast.makeText(LoginActivity.this, apiError.getMessage(),Toast.LENGTH_LONG).show();
                        }
//                        showForm();
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
//                    showForm();
                }
            });
        }
    }

    private void handleErrors(ResponseBody response){
        ApiError apiError = ApiUtils.converErrors(response);

        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()){
            if (error.getKey().equals("nomor handphone")){
                tilnohp.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")){
                tilpass.setError(error.getValue().get(0));
            }
        }
    }

    private void setupRules() {
        validator.addValidation(this, R.id.til_loggnohp, RegexTemplate.NOT_EMPTY, R.string.err_nohp);
        validator.addValidation(this, R.id.til_password, RegexTemplate.NOT_EMPTY, R.string.err_password);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        facebookManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
            call = null;
        }
//        facebookManager.onDestroy();
    }
}

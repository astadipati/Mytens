package net.ramastudio.mytens.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import net.ramastudio.mytens.R;
import net.ramastudio.mytens.model.AccessToken;
import net.ramastudio.mytens.model.ApiError;
import net.ramastudio.mytens.utils.api.ApiService;
import net.ramastudio.mytens.utils.api.ApiUtils;
import net.ramastudio.mytens.utils.api.RetrofitBuilder;
import net.ramastudio.mytens.utils.token.TokenManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

//    EditText ed1, ed2;
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
//    String verifikasiKode;
//    Button btn1, btn2;
    Context mContext;

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.til_nik)
    TextInputLayout tilnik;
    @BindView(R.id.til_nohp)
    TextInputLayout tilnohp;
    @BindView(R.id.til_password)
    TextInputLayout tilpassword;

    ApiService service;
    Call<AccessToken> call;
    AwesomeValidation validator;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        service = RetrofitBuilder.createService(ApiService.class);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        setupRules();

        if (tokenManager.getToken().getAccessToken() != null){
            startActivity(new Intent(RegisterActivity.this, DataumumActivity.class));
            finish();
        }
    }

//    Onclik Btn Register
    @OnClick(R.id.btn_register)
    void register(){
        String nik = tilnik.getEditText().getText().toString();
        String nohp = tilnohp.getEditText().getText().toString();
        String password = tilnohp.getEditText().getText().toString();

        validator.clear();

        if (validator.validate()){
            call = service.register(nik, nohp, password);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.w(TAG, "OnResponse: "+ response);

                    if (response.isSuccessful()){
                        Log.w(TAG, "OnResponse: "+response.body());
                        tokenManager.saveToken(response.body());
//                        Toast.makeText(mContext, "Token Tersimpan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else{
                        handleErrors(response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }
    @OnClick(R.id.go_to_login)
    void gotoRegister(){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }

    private void handleErrors(ResponseBody response){
        ApiError apiError = ApiUtils.converErrors(response);

            for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()){
                if (error.getKey().equals("NIK")){
                    tilnik.setError(error.getValue().get(0));
                }
                if (error.getKey().equals("nohp")){
                    tilnohp.setError(error.getValue().get(0));
                }
                if (error.getKey().equals("password")){
                    tilpassword.setError(error.getValue().get(0));
                }
            }
    }
    private void setupRules() {
        validator.addValidation(this, R.id.til_nik, RegexTemplate.NOT_EMPTY, R.string.err_nik);
//        validator.addValidation(this, R.id.til_nohp, Patterns.EMAIL_ADDRESS, R.string.err_nohp);
        validator.addValidation(this, R.id.til_nohp, RegexTemplate.NOT_EMPTY, R.string.err_nohp);
        validator.addValidation(this, R.id.til_password, "[a-zA-Z0-9]{6,}", R.string.err_password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null) {
            call.cancel();
            call = null;
        }
    }

}

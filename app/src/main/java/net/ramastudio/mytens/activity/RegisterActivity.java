package net.ramastudio.mytens.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import net.ramastudio.mytens.R;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    EditText ed1, ed2;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verifikasiKode;
    Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        ed1 = (EditText) findViewById(R.id.EdtNo);
//        ed2 = (EditText) findViewById(R.id.EdtOtp);
//        btn1 = (Button) findViewById(R.id.button);
//        btn2 = (Button) findViewById(R.id.button2);

    }


}

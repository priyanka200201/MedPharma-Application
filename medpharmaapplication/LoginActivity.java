package com.example.medpharmaapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public static String PREFS_NAME = "MyPrefsFile";

    EditText Email, Password;
    Button Login;
    FirebaseAuth Auth;
    TextView forgotpass;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = (EditText) findViewById(R.id.email1);
        Password = (EditText) findViewById(R.id.pass1);
        Login = (Button) findViewById(R.id.login_btn);
        forgotpass = (TextView) findViewById(R.id.forgotPass);
        DB = new DBHelper(this);

        Auth = FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("hasLoggedIn", true);
                editor.commit();

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Email.getText().toString();
                String password = Password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email is required");
                    Email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("Password is required");
                    Password.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    Password.setError("Password must have 6 or more characters");
                    Password.requestFocus();
                    return;
                }

                Auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast toast = new Toast(getApplicationContext());
                            TextView tv = new TextView(LoginActivity.this);
                            tv.setBackgroundResource(R.drawable.toast_bg);
                            tv.setTextColor(Color.WHITE);
                            tv.setTextSize(20);
                            tv.setText("Logged in successfully");
                            toast.setView(tv);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast toast = new Toast(getApplicationContext());
                            TextView tv = new TextView(LoginActivity.this);
                            tv.setBackgroundResource(R.drawable.toast_bg);
                            tv.setTextColor(Color.WHITE);
                            tv.setTextSize(20);
                            tv.setText("Error! " + task.getException().getMessage());
                            toast.setView(tv);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
            }
        });

        TextView btn = findViewById(R.id.textViewSignUp);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });
    }

    public void open_google(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.Google)));
        startActivity(browserIntent);
    }

    public void open_fb(View view){
        Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.Facebook)));
        startActivity(browserIntent2);
    }
}
package com.example.medpharmaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText Username, Password, Email, Phone, Repassword;
    Button Signup;
    FirebaseAuth Auth;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        DB = new DBHelper(this);
        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.pass);
        Email = (EditText) findViewById(R.id.email);
        Phone = (EditText) findViewById(R.id.phone);
        Repassword = (EditText) findViewById(R.id.ConfirmPass);
        Signup = (Button) findViewById(R.id.register_btn);

        Auth = FirebaseAuth.getInstance();

        if (Auth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Username.getText().toString();
                String email = Email.getText().toString();
                String phone = Phone.getText().toString();
                String password = Password.getText().toString();
                String repassword = Repassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Username.setError("Username is required!");
                    Username.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email is required!");
                    Email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Phone.setError("Mobile no is required!");
                    Phone.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("Password is required!");
                    Password.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(repassword)) {
                    Repassword.setError("Password is required!");
                    Repassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    Password.setError("Password must have 6 or more characters");
                    Password.requestFocus();
                    return;
                }

                if (password.equals(repassword)) {

                    Auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(username, email, phone);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast toast = new Toast(getApplicationContext());
                                            TextView tv = new TextView(RegisterActivity.this);
                                            tv.setBackgroundResource(R.drawable.toast_bg);
                                            tv.setTextColor(Color.WHITE);
                                            tv.setTextSize(20);
                                            tv.setText("Registration successful");
                                            toast.setView(tv);
                                            toast.setDuration(Toast.LENGTH_LONG);
                                            toast.show();

                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });

                            } else {
                                Toast toast = new Toast(getApplicationContext());
                                TextView tv = new TextView(RegisterActivity.this);
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
                else {
                    Toast toast = new Toast(getApplicationContext());
                    TextView tv = new TextView(RegisterActivity.this);
                    tv.setBackgroundResource(R.drawable.toast_bg);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(20);
                    tv.setText("Passwords are not matching");
                    toast.setView(tv);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        });

        TextView btn = findViewById(R.id.alreadyAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
package com.example.medpharmaapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email_reset;
    Button resetbtn;
    DBHelper DB;

    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_reset = (EditText) findViewById(R.id.email_reset_text);
        resetbtn = (Button) findViewById(R.id.reset_btn);
        DB = new DBHelper(this);

        Auth = FirebaseAuth.getInstance();

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    public void resetPassword() {
        String email = email_reset.getText().toString().trim();

        if(email.isEmpty()){
            email_reset.setError("Email is required!");
            email_reset.requestFocus();
            return;
        }

        Auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast toast = new Toast(getApplicationContext());
                    TextView tv = new TextView(ForgotPasswordActivity.this);
                    tv.setBackgroundResource(R.drawable.toast_bg);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(20);
                    tv.setText("Check your Email to reset your password!");
                    toast.setView(tv);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Toast toast = new Toast(getApplicationContext());
                    TextView tv = new TextView(ForgotPasswordActivity.this);
                    tv.setBackgroundResource(R.drawable.toast_bg);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(20);
                    tv.setText("Try Again! "+ task.getException().getMessage());
                    toast.setView(tv);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
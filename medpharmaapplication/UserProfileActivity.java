package com.example.medpharmaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    TextView username, phone_number, email_id;
    Toolbar toolbar;

    DatabaseReference userRef;
    FirebaseAuth mAuth;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        username = findViewById(R.id.username);
        phone_number = findViewById(R.id.phone_number);
        email_id = findViewById(R.id.email_id);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String myUsername = snapshot.child("username").getValue().toString();
                    String myPhone_number = snapshot.child("phone").getValue().toString();
                    String myEmail_id = snapshot.child("email").getValue().toString();

                    username.setText(myUsername);
                    phone_number.setText(myPhone_number);
                    email_id.setText(myEmail_id);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        else if(id == R.id.main_cart_icon){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


/*
<LinearLayout
        android:id="@+id/linearLayout4"
        android:layout_width="340dp"
        android:layout_height="50dp"
        android:layout_marginTop="20dp"
        android:orientation="horizontal"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/linearLayout3"
        tools:ignore="MissingConstraints">

        <ImageView
            android:id="@+id/iv_address"
            android:layout_width="37dp"
            android:layout_height="match_parent"
            android:layout_marginEnd="20dp"
            android:src="@drawable/ic_location" />

        <EditText
            android:id="@+id/address"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:hint="Enter your Address"
            android:textColor="@color/black"
            android:textSize="20dp" />
    </LinearLayout>

    <androidx.appcompat.widget.AppCompatButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="56dp"
        android:background="@color/black"
        android:text="Update"
        android:textAllCaps="false"
        android:textColor="@color/white"
        android:textSize="20dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/linearLayout4"
        tools:ignore="MissingConstraints" />
 */
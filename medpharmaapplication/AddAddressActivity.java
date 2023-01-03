package com.example.medpharmaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddAddressActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner;
    Button add_address;
    Dialog dialog;
    EditText fullname, pincode, house, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        fullname = findViewById(R.id.fullnametext);
        pincode = findViewById(R.id.pincodetext);
        house = findViewById(R.id.flatnotext);
        city = findViewById(R.id.citytext);

        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.states);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dialog = new Dialog(AddAddressActivity.this);
        dialog.setContentView(R.layout.activity_payy);
        dialog.setCancelable(false);

        add_address = findViewById(R.id.add_address);

        Button cash = dialog.findViewById(R.id.cod);
        Button pay = dialog.findViewById(R.id.paytm);
        ImageButton cancel = dialog.findViewById(R.id.cancel);

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), cod.class);
                startActivity(intent);
                finish();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), paytm.class);
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fname = fullname.getText().toString();
                String pin = pincode.getText().toString();
                String flat = house.getText().toString();
                String cityy = city.getText().toString();

                if (TextUtils.isEmpty(fname)) {
                    fullname.setError("Required field");
                    fullname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pin)) {
                    pincode.setError("Required field");
                    pincode.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(flat)) {
                    house.setError("Required field");
                    house.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cityy)) {
                    city.setError("Required field");
                    city.requestFocus();
                    return;
                }

                Toast.makeText(AddAddressActivity.this, "Address saved!", Toast.LENGTH_SHORT).show();
                dialog.show();
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
        return super.onOptionsItemSelected(item);
    }
}
package com.example.medpharmaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class ConsultationActivity extends AppCompatActivity  implements View.OnClickListener{

    ImageView gen,dentist,gyn,ent_1,eye_care,gas,ped,dermat;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gen=findViewById(R.id.general);
        dentist=(ImageView)findViewById(R.id.dental);
        gyn=(ImageView)findViewById(R.id.ob);
        ent_1=(ImageView)findViewById(R.id.ent_doc);
        eye_care=(ImageView)findViewById(R.id.eyecare);
        gas=(ImageView)findViewById(R.id.gastro);
        ped=(ImageView)findViewById(R.id.kid);
        dermat=(ImageView)findViewById(R.id.derm);
        gen.setOnClickListener(this);
        dentist.setOnClickListener(this);
        gyn.setOnClickListener(this);
        ent_1.setOnClickListener(this);
        eye_care.setOnClickListener(this);
        gas.setOnClickListener(this);
        ped.setOnClickListener(this);
        dermat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.general:
            case R.id.dental:
            case R.id.ob:
            case R.id.ent_doc:
            case R.id.eyecare:
            case R.id.gastro:
            case R.id.kid:
            case R.id.derm:
                doc();
                break;
        }
    }
    public void doc(){
        Intent menu=new Intent(this, DoctorActivity.class);
        startActivity(menu);
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
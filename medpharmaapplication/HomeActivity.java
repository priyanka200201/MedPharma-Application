package com.example.medpharmaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telecom.CallRedirectionService;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    CardView thermometer_layout, diapers_layout, pcm_layout, mask_layout;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    FirebaseAuth Auth;

    LinearLayout linearLayoutShop, linearLayoutProduct;
    ImageView medicine, equipments, mombaby, supplements, covid;
    TextView tvmedicine, tvequipments, tvmombaby, tvsupplements, tvcovid;

    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY = 2000;
    final private long PERIOD = 2000;

    int[] imageShop = {R.drawable.farmco, R.drawable.equipments, R.drawable.momandbaby, R.drawable.supplements, R.drawable.covid};
    String[] namesItemShop = {"Medicines","Equipments","Mom-Baby","Supplements","Covid Essentials"};

    public HomeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        thermometer_layout = (CardView) findViewById(R.id.thermometer_layout);
        diapers_layout = (CardView) findViewById(R.id.diapers_layout);
        pcm_layout = (CardView) findViewById(R.id.pcm_layout);
        mask_layout = (CardView) findViewById(R.id.mask_layout);

        thermometer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ThermometerDescActivity.class);
                startActivity(intent);
            }
        });

        diapers_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DiapersDescActivity.class);
                startActivity(intent);
            }
        });

        mask_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MaskDescActivity.class);
                startActivity(intent);
            }
        });

        pcm_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PCM_DescActivity.class);
                startActivity(intent);
            }
        });

        bannerSliderViewPager = findViewById(R.id.banner_viewPager);

        sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.ad4));
        sliderModelList.add(new SliderModel(R.drawable.ad5));

        sliderModelList.add(new SliderModel(R.drawable.ad1));
        sliderModelList.add(new SliderModel(R.drawable.ad2));
        sliderModelList.add(new SliderModel(R.drawable.ad3));
        sliderModelList.add(new SliderModel(R.drawable.ad4));
        sliderModelList.add(new SliderModel(R.drawable.ad5));

        sliderModelList.add(new SliderModel(R.drawable.ad1));
        sliderModelList.add(new SliderModel(R.drawable.ad2));

        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        linearLayoutShop = findViewById(R.id.linear_layout_shop);
        linearLayoutProduct = findViewById(R.id.linear_layout_products);
        loadShop();

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.bringToFront();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setHomeAsUpIndicator(R.drawable.ic_navigation_drawer_icon);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast toast = new Toast(getApplicationContext());
                TextView tv = new TextView(HomeActivity.this);
                switch (item.getItemId()){
                    case R.id.home :
                        tv.setBackgroundResource(R.drawable.toast_bg);
                        tv.setTextColor(Color.WHITE);
                        tv.setTextSize(20);
                        tv.setText("Home page");
                        toast.setView(tv);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.userprofile:
                        tv.setBackgroundResource(R.drawable.toast_bg);
                        tv.setTextColor(Color.WHITE);
                        tv.setTextSize(20);
                        tv.setText("User Profile");
                        toast.setView(tv);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();

                        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.feedback:
                        
                        showDialog( );
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.consultation:
                        tv.setBackgroundResource(R.drawable.toast_bg);
                        tv.setTextColor(Color.WHITE);
                        tv.setTextSize(20);
                        tv.setText("Online Consultation");
                        toast.setView(tv);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();

                        startActivity(new Intent(HomeActivity.this, ConsultationActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.firstaid:
                        tv.setBackgroundResource(R.drawable.toast_bg);
                        tv.setTextColor(Color.WHITE);
                        tv.setTextSize(20);
                        tv.setText("First Aid");
                        toast.setView(tv);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();

                        startActivity(new Intent(HomeActivity.this, FirstAidActivity.class));

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.logout:

                        AlertDialog.Builder builder3 = new AlertDialog.Builder(HomeActivity.this);
                        builder3.setMessage("Are you sure you want to Logout?")
                                .setTitle("Confirm Logout")
                                .setCancelable(false)
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        finish();
                                    }
                                });
                        AlertDialog alertDialog3 = builder3.create();
                        alertDialog3.show();

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

        Auth = FirebaseAuth.getInstance();

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };
        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
        startBannerSlideShow();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pageLooper();
                stopBannerSlideShow();
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    startBannerSlideShow();
                }
                return false;
            }
        });
    }

    private void pageLooper(){
        if(currentPage == sliderModelList.size() - 2){
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage, false);
        }

        if(currentPage == 1){
            currentPage = sliderModelList.size() - 3;
            bannerSliderViewPager.setCurrentItem(currentPage);
        }
    }

    private void startBannerSlideShow(){
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage >= sliderModelList.size()){
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY, PERIOD);
    }

    private void stopBannerSlideShow(){
        timer.cancel();
    }

    private void showDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Feedback Form!!!")
                .setCancelable(false)
                .setMessage("Provide your valuable feedback.");

        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.send_feedback, null);

        MaterialEditText editName = reg_layout.findViewById(R.id.editName);
        MaterialEditText editEmail = reg_layout.findViewById(R.id.editEmail);
        MaterialEditText editFeedback = reg_layout.findViewById(R.id.editFeedback);

        dialog.setView(reg_layout);

        dialog.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(editName.getText().toString())){
                    Toast.makeText(HomeActivity.this, "Enter your Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editEmail.getText().toString())){
                    Toast.makeText(HomeActivity.this, "Enter your Email id!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editFeedback.getText().toString())){
                    Toast.makeText(HomeActivity.this, "Please write a feedback!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String feedback = editFeedback.getText().toString().trim();

                Feedback obj = new Feedback(name, email, feedback);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("FeedbackForm");

                myRef.child(name).setValue(obj);
                editName.setText("");
                editEmail.setText("");
                editFeedback.setText("");

                Toast.makeText(HomeActivity.this, "Thanks for your feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialog.show();
    }

    /* public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    } */

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("Are you sure you want to Exit?")
                .setTitle("Confirm Exit")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HomeActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog2 = builder2.create();
        alertDialog2.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = Auth.getCurrentUser();
        if(user != null){
            //logged in
        }
        else{
            //no one logged in
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    public void loadShop() {
        LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.item_shop, linearLayoutShop,false);

        medicine = (ImageView) view.findViewById(R.id.medicine_category);
        tvmedicine = (TextView) view.findViewById(R.id.tv_medicine_category);
        equipments = (ImageView) view.findViewById(R.id.equipments_category);
        tvequipments = (TextView) view.findViewById(R.id.tv_equipments_category);
        mombaby = (ImageView) view.findViewById(R.id.mom_baby_category);
        tvmombaby = (TextView) view.findViewById(R.id.tv_mom_baby_category);
        supplements = (ImageView) view.findViewById(R.id.supplements_category);
        tvsupplements = (TextView) view.findViewById(R.id.tv_supplements_category);
        covid = (ImageView) view.findViewById(R.id.covid_category);
        tvcovid = (TextView) view.findViewById(R.id.tv_covid_category);

            medicine.setImageResource(imageShop[0]);
            tvmedicine.setText(namesItemShop[0]);

            equipments.setImageResource(imageShop[1]);
            tvequipments.setText(namesItemShop[1]);

            mombaby.setImageResource(imageShop[2]);
            tvmombaby.setText(namesItemShop[2]);

            supplements.setImageResource(imageShop[3]);
            tvsupplements.setText(namesItemShop[3]);

            covid.setImageResource(imageShop[4]);
            tvcovid.setText(namesItemShop[4]);

            medicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String itemSelected = namesItemShop[0];
                    Intent medicine = new Intent(getApplicationContext(), shop.class);
                    startActivity(medicine);
                }
            });
            equipments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String itemSelected = namesItemShop[1];
                    Intent equip = new Intent(getApplicationContext(), shop_equipments.class);
                    startActivity(equip);
                }
            });
            mombaby.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String itemSelected = namesItemShop[2];
                    Intent mb = new Intent(getApplicationContext(), shop_mombaby.class);
                    startActivity(mb);
                }
            });
            supplements.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String itemSelected = namesItemShop[3];
                    Intent supp = new Intent(getApplicationContext(), shop_supplements.class);
                    startActivity(supp);
                }
            });
            covid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String itemSelected = namesItemShop[4];
                    Intent covid = new Intent(getApplicationContext(), shop_covid_essentials.class);
                    startActivity(covid);
                }
            });
            linearLayoutShop.addView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_cart_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.main_cart_icon){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



/*
<com.google.android.material.navigation.NavigationView
        android:id="@+id/navigationView"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:headerLayout="@layout/headerfile"
        app:menu="@menu/sidemenu"
        android:layout_gravity="start"/>

 */


/*
<TextView
                android:id="@+id/tv_popular_products"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="18dp"
                android:layout_marginTop="5dp"
                android:text="@string/popular_products"
                android:textColor="@color/black"
                android:textSize="15dp"
                android:textStyle="bold" />

            <HorizontalScrollView
                android:id="@+id/horizontal_products"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_margin="12dp">

                <LinearLayout
                    android:id="@+id/linear_layout_products"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal" />
            </HorizontalScrollView>
 */
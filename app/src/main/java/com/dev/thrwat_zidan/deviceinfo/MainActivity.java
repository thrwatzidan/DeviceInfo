package com.dev.thrwat_zidan.deviceinfo;

import android.Manifest;
import android.os.Build;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    MyAdapter myAdapter;
    TextView Manufacatuertxt, androidVersiontxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Manifest
        StringBuilder sb = new StringBuilder(" \n");
        final Field[] manifestFields = Manifest.permission.class.getDeclaredFields();
        for (final Field field : manifestFields) {
            sb.append("<uses-permission android:name=\"android.permission."
                    + field.getName() + "\"/>");
            sb.append("\n");
            if (sb.length() > 1000) {
              Log.v("Permission", sb.toString());
//                sb = new StringBuilder();
            }
        }

        //init
        initViews();
//collapsing ToolBar
        initCollapsingToolBar();


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.GET_TASKS,
                        Manifest.permission.PACKAGE_USAGE_STATS,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                        Manifest.permission.GET_ACCOUNTS

                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

                //myAdapter
                myAdapter = new MyAdapter(MainActivity.this, getmodels());
                recycler.setAdapter(myAdapter);

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();


        //dispaly android version logo/icon
        try {

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN){
                Glide.with(this).load(R.drawable.android41).into((ImageView) findViewById(R.id.backdrop));
            }
//jellybean
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1){
                Glide.with(this).load(R.drawable.android41).into((ImageView) findViewById(R.id.backdrop));
            }
            //jellybean
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2){
                Glide.with(this).load(R.drawable.android41).into((ImageView) findViewById(R.id.backdrop));
            }
            //KITKAT
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
                Glide.with(this).load(R.drawable.android44).into((ImageView) findViewById(R.id.backdrop));
            }
            //LOLLIPOP
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP){
                Glide.with(this).load(R.drawable.android50).into((ImageView) findViewById(R.id.backdrop));
            }
            //LOLLIPOP
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1){
                Glide.with(this).load(R.drawable.android50).into((ImageView) findViewById(R.id.backdrop));
            }
            //Marshimelo
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
                Glide.with(this).load(R.drawable.android60).into((ImageView) findViewById(R.id.backdrop));
            }
            //NOugats
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N){
                Glide.with(this).load(R.drawable.android70).into((ImageView) findViewById(R.id.backdrop));
            }
            //Nougats
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1){
                Glide.with(this).load(R.drawable.android70).into((ImageView) findViewById(R.id.backdrop));
            }
            //Orea
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O){
                Glide.with(this).load(R.drawable.android80).into((ImageView) findViewById(R.id.backdrop));
            }
            //Orea
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1){
                Glide.with(this).load(R.drawable.android80).into((ImageView) findViewById(R.id.backdrop));
            }
            //Pie
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P){
                Glide.with(this).load(R.drawable.android90).into((ImageView) findViewById(R.id.backdrop));
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initViews() {

        //ToolBar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//initRecycler
        recycler = findViewById(R.id.myRecycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        recycler.setItemAnimator(new DefaultItemAnimator());




        //getDevice manufcatuer
        String manufactuer = Build.MANUFACTURER;
        //getting device model
        String model = Build.MODEL;
        //getting Device android version
        String version = Build.VERSION.RELEASE;
        //getting android version name
        String verName = Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();

        Manufacatuertxt = findViewById(R.id.name_model);
        androidVersiontxt = findViewById(R.id.andro_version);

        Manufacatuertxt.setText(manufactuer.toUpperCase()+""+model);
        androidVersiontxt.setText(version+""+verName);

    }

    private void initCollapsingToolBar() {
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);


        //set Collapsing toolbar tiltle

        collapsingToolbarLayout.setTitle("Device Info");
    }
//add Models To ArrayList
    private ArrayList<Model>getmodels(){
        ArrayList<Model> models = new ArrayList<>();
        Model m = new Model();
        m = new Model();
        m.setName("General");
        m.setImg(R.drawable.general);
        models.add(m);

        m = new Model();
        m.setName("Device ID");
        m.setImg(R.drawable.devid);
        models.add(m);

        m = new Model();
        m.setName("Display");
        m.setImg(R.drawable.display);
        models.add(m);

        m = new Model();
        m.setName("Battery");
        m.setImg(R.drawable.battery);
        models.add(m);

        m = new Model();
        m.setName("User Apps");
        m.setImg(R.drawable.userapps);
        models.add(m);

        m = new Model();
        m.setName("System Apps");
        m.setImg(R.drawable.systemapps);
        models.add(m);

        m = new Model();
        m.setName("Memory");
        m.setImg(R.drawable.memory);
        models.add(m);

        m = new Model();
        m.setName("CPU");
        m.setImg(R.drawable.cpu);
        models.add(m);

        m = new Model();
        m.setName("Sensors");
        m.setImg(R.drawable.sensor);
        models.add(m);

        m = new Model();
        m.setName("SIM");
        m.setImg(R.drawable.sim);
        models.add(m);

        return models;
    }

    //menu.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //this function is called when Search Butto In kB is Pressed
            @Override
            public boolean onQueryTextSubmit(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            //this function calls whenever you type in searchview
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //handel other mwnu item clicks
        if (id== R.id.action_settings){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

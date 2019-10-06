package com.dev.thrwat_zidan.deviceinfo;

import android.content.Context;
import android.os.Build;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.uptimeMillis;
import static java.lang.System.getProperty;

public class GeneralActivity extends AppCompatActivity {
  private   String titles[];
   private String[] descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        //ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("General Information");
            //set back buttonin actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        //calculat device up time

        long miliSec = uptimeMillis();
        String upTime = String.format("%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(miliSec),
                TimeUnit.MILLISECONDS.toMinutes(miliSec) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(miliSec)),
                TimeUnit.MILLISECONDS.toSeconds(miliSec) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(miliSec)));
        //array of containing Information
        titles = new String[]{"Model", "Manufacture", "Device", "Board", "Hardware", "Brand", "Android Version", "OS Name", "API Level", "Bootloader", "Build Number", "Kernel", "Android RunTime", "Up Time"};
        descriptions = new String[]{Build.MODEL, Build.MANUFACTURER, Build.DEVICE, Build.BOARD, Build.HARDWARE, Build.BRAND, Build.VERSION.RELEASE, Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName(), Build.VERSION.SDK_INT + "", Build.BOOTLOADER, Build.FINGERPRINT, Build.getRadioVersion(), getProperty("os.arch"), "ART" + getProperty("java.vm.version"), upTime};

        ListView listView = findViewById(R.id.generalList);

        //Set adapter to our listview

        MyAdapter myAdapter = new MyAdapter(this, titles, descriptions);
        listView.setAdapter(myAdapter);
    }
    public class  MyAdapter extends ArrayAdapter<String> {
        Context context;
        String MyTitle[];
        String MyDescreptions[];

        MyAdapter(Context context ,String[] titles, String[] descriptions){
            super(context,R.layout.listview,R.id.title,titles  );
            this.context = context;
            this.MyTitle = titles;
            this.MyDescreptions = descriptions;
        }

        public View getView(int position , View convertview, ViewGroup parent)  {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.listview, parent, false);
            //view in listview xml
            TextView titel_txt = view.findViewById(R.id.titel_txt);
            TextView desc_txt = view.findViewById(R.id.desc_txt);

            //set Text
            titel_txt.setText(titles[position]);
            desc_txt.setText(descriptions[position]);
            return view;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.action_search).setVisible(false);


        return true;
    }
}

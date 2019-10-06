package com.dev.thrwat_zidan.deviceinfo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;

public class DisplayActivity extends AppCompatActivity {

    private String titles [];
    private String descriptions [];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("Display");
            // setback option
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }
        //Screensize In pixels widthxHight
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int hieght = dm.heightPixels;
        String resolution = width + "x" + hieght + " Pixel";

        //Physical size in inc
        double x = Math.pow(width / dm.xdpi, 2);
        double y = Math.pow(hieght / dm.xdpi, 2);
        //return float value
        double screenInch = Math.sqrt(x + y);
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        String screenInchesOutPut = format.format(screenInch);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refrishingRate = display.getRefreshRate();
        NumberFormat form1 = NumberFormat.getNumberInstance();
        form1.setMinimumFractionDigits(1);
        form1.setMaximumFractionDigits(1);
        String outputRefreshing = form1.format(refrishingRate);

        String orint = String.valueOf(this.getResources().getConfiguration().orientation);
        if (orint=="1"){
            orint = "Portrait";
        }
        else orint = "Landscape";

        titles = new String[]{"Resolution", "Density", "Physical Size", "Refresh Rate", "Orientation"};
        descriptions = new String[]{resolution, DisplayMetrics.DENSITY_XHIGH + " dpi  (xhdpi)", screenInchesOutPut + "inch", outputRefreshing + " Hz", orint};

            ListView listView = findViewById(R.id.displayList);
        DisplayAdapter adaptr = new DisplayAdapter(this, titles, descriptions);
        listView.setAdapter(adaptr);
    }
    private class DisplayAdapter extends ArrayAdapter<String>{
        public DisplayAdapter(@NonNull Context context, String[] myTitlee, String[] myDescriptions) {
            super(context, R.layout.listview,R.id.titel_txt,titles);
            this.context = context;
            this.myTitlee = myTitlee;
            this.myDescriptions = myDescriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview, parent, false);

            //Views
            TextView MyT = view.findViewById(R.id.titel_txt);
            TextView MyD = view.findViewById(R.id.desc_txt);

            MyT.setText(titles[position]);
            MyD.setText(descriptions[position]);

            return view;
        }

        Context context;
        String myTitlee[];
        String myDescriptions[];

        //constractors


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

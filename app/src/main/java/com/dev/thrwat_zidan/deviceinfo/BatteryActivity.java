package com.dev.thrwat_zidan.deviceinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryActivity extends AppCompatActivity {
TextView txt_view1,txt_view2,txt_percentage, txt_bat_percent;
    private double batteryCapicty;
    private ProgressBar progressBar;
    private int ProgressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("Battery");
            // setback option
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);}

            //get application Context
        android.content.Context context = getApplicationContext();
        //init INTENT
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //Register broadcast reciver
        context.registerReceiver(broadCastReciver, filter);

        //init
        txt_bat_percent = findViewById(R.id.txt_bat_percent);
        txt_view1 = findViewById(R.id.txt_view1);
        txt_view2 = findViewById(R.id.txt_view2);
        txt_percentage = findViewById(R.id.txt_percentage);
        progressBar = findViewById(R.id.pd);

        Object mPowerProfile = null;
        String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try{
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            batteryCapicty = (Double) Class.forName(POWER_PROFILE_CLASS).getMethod("getAveragePower",
                    java.lang.String.class).invoke(mPowerProfile, "battery.capacity");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private BroadcastReceiver broadCastReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String charging_status = "", battery_condition = "", power_source = "Unplugged";
            //Get Battery Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            //get bat Condition
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

            if (health == BatteryManager.BATTERY_HEALTH_COLD) {
                battery_condition = "Cold";
            }
            if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
                battery_condition = "Good";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                battery_condition = "Over Heat";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                battery_condition = "Over Voltage";
            }
            if (health == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                battery_condition = "UNKNOWN";
            }
            if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                battery_condition = "FAILURE";
            }
            //getBat Tempratuer in celcius
            int temp_c = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
            //celecius to fahrenthit
            int temp_f = (int) (temp_c * 1.8 + 32);
            int chargerPlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            if (chargerPlug == BatteryManager.BATTERY_PLUGGED_USB) {
                power_source = "USB";
            }
            if (chargerPlug == BatteryManager.BATTERY_PLUGGED_AC) {
                power_source = "AC";
            }
            if (chargerPlug == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                power_source = "Wireless";
            }
            //get bat status in charging /discharging
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                charging_status = "Charging";
            }
            if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                charging_status = "Discharging";
            }
            if (status == BatteryManager.BATTERY_STATUS_FULL) {
                charging_status = "Battery Full";
            }
            if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                charging_status = "Unknown";
            }
            if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                charging_status = "Not Charging";
            }
            //get Bat tech
            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            //get bat VOLTAGE
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            //Display the outPut
            txt_bat_percent.setText("Battery Percent: " + level + "%");
            txt_view1.setText("Condition: \n" +
                    "Temperature: \n" +
                    "Power Source:\n" +
                    "Charging Status:\n" +
                    "Type:\n" +
                    "Voltage:\n" +
                    "Capacity:");
            txt_view2.setText(battery_condition + "\n" +
                    " " + temp_c + " " + (char) 0x00B0 + "C/" + temp_f + " " + (char) 0x00B0 + "F\n" +
                    " " + power_source + "\n" +
                    " " + charging_status + "\n" +
                    "" + technology + "\n" +
                    " " + voltage + "mV\n" +
                    " " + batteryCapicty + " mAh");

            int levels = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int Scal = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float percentage = levels / (float) Scal;

            //update the Progress bar to display curentBAt Charging percentage

            ProgressStatus = (int) ((percentage) * 100);
            txt_percentage.setText("" + ProgressStatus + "%");

            progressBar.setProgress(ProgressStatus);

        }
    };


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

package com.dev.thrwat_zidan.deviceinfo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import java.util.List;

public class SensorActivity extends AppCompatActivity {
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        //title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("Sensors");
            actionBar.setSubtitle("Detail list of available sensor");
            // setback option
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true); }

        mListView = findViewById(R.id.sensor_Lv);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        //set adapter to listView
        mListView.setAdapter(new MySensorAdapter(this, R.layout.sensor_row, sensors));
        //Total nummber of avilable sensors in the device
        String total = mListView.getCount() + "";
        TextView totalSensors = findViewById(R.id.countSens);
        totalSensors.setText("Total Sensors: " + total);
        Toast.makeText(this, total+" Sesors detected....", Toast.LENGTH_SHORT).show();



    }

    public static class MySensorAdapter extends ArrayAdapter<Sensor>{

        private int textViewResourceId;
        private static class ViewHolder{
            TextView itemView;
        }
        //constructor
        MySensorAdapter(Context context, int textViewResourceId, List<Sensor> items){
            super(context, textViewResourceId, items);
            this.textViewResourceId = textViewResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){

                convertView = LayoutInflater.from(this.getContext()).inflate(textViewResourceId, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.itemView = (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Sensor items = getItem(position);
            //get All Sensors list
            if (items !=null ){
                viewHolder.itemView.setText("Name: " + items.getName()
                        + "\nInt Type: " + items.getType()
                        + "\nString Type: " + sensorTypeToString(items.getType())
                        +"\nVendor: "+items.getVendor()
                        +"\nVersion: "+ items.getVersion()
                        +"\nResolution: "+items.getResolution()
                        +"\nPower: "+ items.getPower()+"mAh"
                        +"\nMaxinum Range: "+items.getMaximumRange());

            }

            return  convertView;
        }
    }

public static String sensorTypeToString(int sensorType){
        switch (sensorType){
            case Sensor.TYPE_ACCELEROMETER:
                return "Accelerometer";
            case Sensor.TYPE_TEMPERATURE:
                return "Temperature";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return "Ambient Temp";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "Game Rotaion Vector";
                case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "Geomagentic Rotaion Vector";
            case Sensor.TYPE_GRAVITY:
                return "Gravity";
                case Sensor.TYPE_GYROSCOPE:
                return "Gyroscope";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "Gyoscope Uncalibrated";
                case Sensor.TYPE_HEART_BEAT:
                return "Heater Monitor";
            case Sensor.TYPE_LIGHT:
                return "Light";
                case Sensor.TYPE_LINEAR_ACCELERATION:
                return "Liner Acceleration";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "Magnatic Field";
                case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "Magnatic Field Uncalibrated";
            case Sensor.TYPE_ORIENTATION:
                return "Oriantaion";
            case Sensor.TYPE_PRESSURE:
                return "Pressure";
                case Sensor.TYPE_PROXIMITY:
                return "Proximity";
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Relative Humidity";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "Rotaion Vector";
                case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "Significant Motion";
            case Sensor.TYPE_STEP_COUNTER:
                return "step Counter";
            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Director";
            default:
                return "UnKnown";
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

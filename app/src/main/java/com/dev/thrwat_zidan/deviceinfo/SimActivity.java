package com.dev.thrwat_zidan.deviceinfo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SimActivity extends AppCompatActivity {
    //code for runTime permission
    private static final int RED_PHONE_STATE_CODE = 1;
    //array to contain to display to display in listView
    private String title[];
    private String description[];
    //listView
    ListView mListView;
    //class to get SIM info
    TelephonyManager tm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("SIM");
            actionBar.setSubtitle("SIM 1");
            // setback option
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
//telephony Manger
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        mListView = findViewById(R.id.simListView);
//handling runTime Permission for marshmelo or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_GRANTED) {
                //this will called when permission was not allowed
                String[] permisions = {Manifest.permission.READ_PHONE_STATE};
                //show popup for runtime permission
                requestPermissions(permisions, RED_PHONE_STATE_CODE);
            } else {
                getphoneInfo();
                //this will be called when permission was allowed

            }

        } else {
            getphoneInfo();
            //this will be called when system os is <marshmallow

        }


    }

    //fun to get sim info
    private void getphoneInfo() {
        int ss = tm.getSimState();
        String simState = "";
        switch (ss) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simState = "Absent";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simState = "NetWork Locked";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simState = "PIN Required";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simState = "PUK Required";
                break;
            case TelephonyManager.SIM_STATE_READY:
                simState = "Ready";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                simState = "Unknown";
                break;
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                simState = "Card IO error";
                break;
            case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                simState = "Card Restricted";
                break;
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                simState = "PERM Disabled";
                break;
        }
        //Service provider e.g Orange ,Vodafone
        String serviceProvider = tm.getSimOperatorName();
        //Mobenil Operator name
        String mobNAme = tm.getNetworkOperatorName();
        //Intgered cicut card Identfier
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        String simID = tm.getSimSerialNumber();
        //Unique device id ()
        String imei = tm.getDeviceId();
        //IMSI
        String tmSubscriberId = tm.getSubscriberId();
        //Version
        String softwareVersion = tm.getDeviceSoftwareVersion();
        //MCC
        String countryIso = tm.getNetworkCountryIso();
        //Mobile network code
        String mcc_mnc = tm.getSimOperator();
        //mail Tag
        String voiceMailTag = tm.getVoiceMailAlphaTag();
        //Roaming
        boolean roamingStatues = tm.isNetworkRoaming();
        //simNum
        String simNum = tm.getLine1Number();
        //adding this info to array
        title=new String[]{
                "SIM State",
                "Service Provider",
                "Mobile Operator Name",
                "Integrated Circuit Card Identifier (ICCID)",
                "Unique Device ID (IMEI)",
                "International Mobile Subscribe ID (IMSI)",
                "Device Software Version",
                "Mobile Country Code (MCC)",
                "Mobile Country Code (MCC) + Mobile Network Code (MNC)",
                "VoiceMail",
                "Roaming",
                "SIM Number"
        };
        description=new String[]{
                ""+simState,
                ""+serviceProvider,
                ""+mobNAme,
                ""+simID,
                ""+imei,
                ""+tmSubscriberId,
                ""+softwareVersion,
                ""+countryIso,
                ""+mcc_mnc,
                ""+voiceMailTag,
                ""+roamingStatues,
                ""+simNum
        };
        //setting Adapter to ListView
        mListView.setAdapter(new MyAdapter(this,title,description));
    }

    //creating custom Adapter class for listView
    private  class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String MyTitles[];
        String MyDescriptions[];
        //cons
        MyAdapter(Context context,String[]titles,String[]descriptions){
            super(context, R.layout.listview, titles);
            this.context = context;
            this.MyTitles = titles;
            this.MyDescriptions = descriptions;
        }

        @NonNull
        @Override

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Inflater layout xml
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.listview, parent, false);
            //TextView from listView xml
            TextView txt_titele = view.findViewById(R.id.titel_txt);
            TextView txt_description = view.findViewById(R.id.desc_txt);
            //set text to this views
            txt_titele.setText(title[position]);
            txt_description.setText(description[position]);

            return view;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RED_PHONE_STATE_CODE:{
                if (grantResults.length>0 && grantResults[0] == 
                        PackageManager.PERMISSION_GRANTED){
                    //permission was allowed
                    getphoneInfo();
                }
                else{
                    //permission was deniad
                    Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
                }
            }
                
        }
        
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }
}

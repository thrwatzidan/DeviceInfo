package com.dev.thrwat_zidan.deviceinfo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class DeviceActivity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];
    private static final int READ_PHONE_STATE_CODE = 1;

    private TelephonyManager tm;
    private String imei, simCardSerial, simSubscriberID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Device ID");
            // setback option
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        //Android Device ID
       // String deviceid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceid = UniqueID.getDeviceId(this);

//IMEI NUMBER
        tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                String[] permission = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permission, READ_PHONE_STATE_CODE);
            } else {//permission was granted
                imei = deviceid;
                simCardSerial = tm.getSimSerialNumber();
                simSubscriberID = tm.getSubscriberId();

            }
        } else {//System os is <marshmallow no need runtime permission
            if (tm!= null){

            imei = tm.getDeviceId();
            simCardSerial = tm.getSimSerialNumber();
            simSubscriberID = tm.getSubscriberId();
            }
        }
        //IP Address
        String ipAddress = "NO INTERNET Connection";
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3GEnabled = false;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Network[] networks = manager.getAllNetworks();
            for (Network network:networks){
                NetworkInfo info = manager.getNetworkInfo(network);
                if (info != null){
                    if (info.getType()==ConnectivityManager.TYPE_MOBILE){
                        ipAddress = getMobileIPAddress();
                    }
                }
            }
        }else{
            NetworkInfo mMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobile != null){
                ipAddress = is3GEnabled + "";
            }
        }
        //WIFI MAC Address

        String wifiAddress = "NO WIFI Connection";

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Network[] networks = manager.getAllNetworks();
            for (Network network:networks){
                NetworkInfo info = manager.getNetworkInfo(network);
                if (info != null){
                    if (info.getType()==ConnectivityManager.TYPE_WIFI){
                        wifiAddress = getWIFIAddress();
                    }
                }
            }
        }else{
            NetworkInfo mMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mMobile != null){
                ipAddress = is3GEnabled + "";
            }
        }
        //BlueToth Mac Address
        String bt=android.provider.
        Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");
// array containg data
        titles = new String[]{"Android Device ID", "IMEI", "MEID or ESN", "Hardware Serial Number", "SIM Subscriber ID", "IP Address", "WI-FI Mac Address", "Bluetooth Mac Address", "Build Fingerprints"};
        descriptions = new String[]{deviceid, imei, Build.SERIAL, simCardSerial, simSubscriberID, ipAddress, wifiAddress, bt, Build.FINGERPRINT};


        ListView listView = findViewById(R.id.device_list);
        MyAdapter adapter = new MyAdapter(this, titles, descriptions);
        listView.setAdapter(adapter);


    }

    private String getWIFIAddress() {

        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }

    private String getMobileIPAddress() {
        try{
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface interf :interfaces){
                List<InetAddress> addrs = Collections.list(interf.getInetAddresses());
                for (InetAddress addr: addrs){
                    if (!addr.isLoopbackAddress()){
                        return addr.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";

    }

    private class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String MyTitels[];
        String MyDesceptions[];

        //constractors
        public MyAdapter(Context context, String[] titles, String[] descreptions) {

            super(context, R.layout.listview, R.id.titel_txt, titles);
            this.context = context;
            this.MyTitels = titles;
            this.MyDesceptions = descreptions;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview, parent, false);



            TextView myTilte = view.findViewById(R.id.titel_txt);
            TextView myDescription = view.findViewById(R.id.desc_txt);

            myTilte.setText(titles[position]);
            myDescription.setText(descriptions[position]);

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
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was allowed
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    recreate();//restart activity on permission granted
                    imei = tm.getDeviceId();
                    simCardSerial = tm.getSimSerialNumber();
                    simSubscriberID = tm.getSubscriberId();
                }
                else{
                    //permission was deniad
                    Toast.makeText(this, "Requerd Permission", Toast.LENGTH_SHORT).show();
                }
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

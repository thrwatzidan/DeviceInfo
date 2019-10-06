package com.dev.thrwat_zidan.deviceinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class CPUActivity extends AppCompatActivity {

    ProcessBuilder mProcessBuilder;
    String holder = "";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream inputStream;
    Process mProcess;
    byte[] mByteArray;
    ListView mlistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("CPU");
            // setback option
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);}
        mlistview = findViewById(R.id.cpuListView);
        //getting information of cpu
        mByteArray = new byte[1024];

        try{
            mProcessBuilder = new ProcessBuilder(DATA);
            mProcess = mProcessBuilder.start();
            inputStream = mProcess.getInputStream();
            while (inputStream.read(mByteArray)!= -1){
                holder = holder + new String(mByteArray);
            }
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
//ADAPTER
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
                , android.R.id.text1,
                Collections.singletonList(holder));
        //set adapter listView
        mlistview.setAdapter(adapter);

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

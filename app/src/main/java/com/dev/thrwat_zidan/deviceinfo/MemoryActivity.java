package com.dev.thrwat_zidan.deviceinfo;

import android.app.ActivityManager;
import android.os.Environment;
import android.os.StatFs;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.text.NumberFormat;

public class MemoryActivity extends AppCompatActivity {

    TextView mTvTotalRam, mTvFreeRam, mTvUseRam;
    TextView mTvTotalRom, mTvFreeRom, mTvUsedRom;
    TextView mTvTotalHeap;
    TextView mTvPercnRam,mTvPercenRom;
    ProgressBar mPBRam, mPBRom;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Memory");
            // setback option
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);}
            //init
        //Ram
        mTvFreeRam = findViewById(R.id.freeRam);
        mTvTotalRam = findViewById(R.id.totalRam);
        mTvUseRam = findViewById(R.id.usedRam);
        //Rom
        mTvTotalRom = findViewById(R.id.totalRom);
        mTvFreeRom = findViewById(R.id.freeRom);
        mTvUsedRom = findViewById(R.id.usedRom);
        //heap
        mTvTotalHeap = findViewById(R.id.totalHeap);
        //ProgressBar Of Ram &Rom
        mPBRam = findViewById(R.id.pbRam);
        mPBRom = findViewById(R.id.pbRom);
        mTvPercnRam = findViewById(R.id.tv_perc_ram);
        mTvPercenRom = findViewById(R.id.tv_perc_rom);
//geting Ram info
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        //Total Ram
        float totalMem = memoryInfo.totalMem / (1024 * 1024);
        //Free Ram
        float freeMem = memoryInfo.availMem / (1024 * 1024);
        //used ram
        float usedMem = totalMem - freeMem;

        //Percentage of free Ram
        float freeMemOerc = freeMem / totalMem * 100;
        //Percentage of Used Ram
        float usedMemPerc = usedMem / totalMem * 100;
        //Free Ram Percentage decimal point conversion

        NumberFormat numFormatFreePerc = NumberFormat.getNumberInstance();
        numFormatFreePerc.setMinimumFractionDigits(1);
        numFormatFreePerc.setMaximumFractionDigits(1);
        String mFreeMemPerc = numFormatFreePerc.format(freeMemOerc);
        //Used Ram Percentage deciml point Conversation

        NumberFormat numberFormatUsedPerc = NumberFormat.getNumberInstance();
        numberFormatUsedPerc.setMinimumFractionDigits(1);
        numberFormatUsedPerc.setMaximumFractionDigits(1);
        String mUsedMemPerc = numberFormatUsedPerc.format(usedMemPerc);

        //Rom:getting information
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blockSize = stat.getBlockSize();
        float totalBlock = stat.getBlockCount();//for Total Rom
        float avilableBlock = stat.getAvailableBlocks();//free Rom
        float totalRom = (totalBlock * blockSize) / (1024 * 1024);//value of total Rom in mb
        float freeRom = (avilableBlock * blockSize) / (1024 * 1024);//val of free rom
        float usedRom = totalRom - freeRom;//val of used rom in MB

        //ROM Percentage
        float freeRomPerc = (freeRom / totalRom) * 100;
        float usedRomPerc = (usedRom / totalRom) * 100;

        //total Rom Decimal poin
        NumberFormat numFormatTotalRom = NumberFormat.getNumberInstance();
        numFormatTotalRom.setMinimumFractionDigits(1);
        numFormatTotalRom.setMaximumFractionDigits(1);
        String mTotalRom = numberFormatUsedPerc.format(totalRom);

        //free Rom Decimal poin
        NumberFormat numFormatFreeRom = NumberFormat.getNumberInstance();
        numFormatFreeRom.setMinimumFractionDigits(1);
        numFormatFreeRom.setMaximumFractionDigits(1);
        String mFreeRom = numberFormatUsedPerc.format(freeRom);

        //used Rom Decimal poin
        NumberFormat numFormatUsedRom = NumberFormat.getNumberInstance();
        numFormatFreeRom.setMinimumFractionDigits(1);
        numFormatFreeRom.setMaximumFractionDigits(1);
        String mUsedRom = numberFormatUsedPerc.format(usedRom);
        //Free Rom  percentage Decimal poin
        NumberFormat numFormatfREErOMpERCENT = NumberFormat.getNumberInstance();
        numFormatfREErOMpERCENT.setMinimumFractionDigits(1);
        numFormatfREErOMpERCENT.setMaximumFractionDigits(1);
        String mFreeRomPerc = numberFormatUsedPerc.format(freeRomPerc);
        //Used Rom  percentage Decimal poin
        NumberFormat numFormatUSEDROMPerc = NumberFormat.getNumberInstance();
        numFormatUSEDROMPerc.setMinimumFractionDigits(1);
        numFormatUSEDROMPerc.setMaximumFractionDigits(1);
        String mUsedRomPerc = numberFormatUsedPerc.format(usedRomPerc);



        //Setting Ram Info
        mTvTotalRam.setText(" " + totalMem + "MB");
        mTvFreeRam.setText(" " + freeMem + "MB" + "(" + mFreeMemPerc + "%)");
        mTvUseRam.setText(" " + usedMem + "MB" + "(" + mUsedMemPerc + "%)");
        //Setting ROm
        mTvTotalRom.setText(" " + mTotalRom + "MB");
        mTvFreeRom.setText(" " + mFreeRom + "MB" + "(" + mFreeRomPerc + "%");
        mTvUsedRom.setText(" " + mUsedRom + "MB" + "(" + mUsedRomPerc + "%)");
        //java Heap

        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        //setting javaheap info
        mTvTotalHeap.setText(" "+maxMemory / (1024 * 1024) + "MB");

        //Setting Ram info to ProgressBa
        mTvPercnRam.setText(mUsedMemPerc + "% Used");
        mPBRam.setProgress((int)usedMemPerc);

        //Setting Rom info to ProgressBa
        mTvPercenRom.setText(mUsedRomPerc + "% Used");
        mPBRom.setProgress((int)(usedRom/totalRom*100));
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

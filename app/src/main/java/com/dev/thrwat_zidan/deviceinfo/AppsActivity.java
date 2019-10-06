package com.dev.thrwat_zidan.deviceinfo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppsActivity extends AppCompatActivity {

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean mIncludeSystemApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Apps");
            // setback option
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        listView = (ListView) findViewById(R.id.install_apps_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipRefresh);
        listView.setTextFilterEnabled(true);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();


            }
        });

        //getting Total no of installed apps
        String size = listView.getCount() + "";
        //show in text View above list view
        TextView countApps = findViewById(R.id.countApps);
        countApps.setText("Total System Installed Apps:" +size);

    }

    private void refresh() {
        LoadAppInfoTask loadAppInfoTask = new LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadAppInfoTask loadAppInfoTask = new LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }

    class LoadAppInfoTask extends AsyncTask<Integer,Integer,List<AppInfo>>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }


        @Override
        protected List<AppInfo> doInBackground(Integer... params) {
            List<AppInfo> apps = new ArrayList<>();
            PackageManager manager = getPackageManager();
            List<ApplicationInfo> infos = manager.getInstalledApplications(params[0]);

            for (ApplicationInfo info :infos)
            {
                if (mIncludeSystemApp && (info.flags &ApplicationInfo.FLAG_SYSTEM)==1){
                    continue;
                }
                AppInfo app = new AppInfo();
                app.info = info;
                app.label = (String) info.loadLabel(manager);
                apps.add(app);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<AppInfo> appInfos) {
            super.onPostExecute(appInfos);
            listView.setAdapter(new AppAdapter(AppsActivity.this, appInfos));
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(listView,appInfos.size()+"application loaded",Snackbar.LENGTH_LONG).show();
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

package com.dev.thrwat_zidan.deviceinfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserAppActivity extends AppCompatActivity {


    private List<AppList> installedApps;
    private AppAdapter installAppAdapter;

    ListView install_apps_list;

    List<PackageInfo> packs;
    List<AppList> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("User App");
            // setback option
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);}



        install_apps_list = findViewById(R.id.install_apps_list);

        //call installed apps
        installedApps = getInstalledApps();
        //adapter
        installAppAdapter = new AppAdapter(UserAppActivity.this, installedApps);
        //setadapter
        install_apps_list.setAdapter(installAppAdapter);
//list item click listener

        install_apps_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                String[] options = {"Open App", "App Info", "Uninstall"};
                //Alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(UserAppActivity.this);
                //set Titlle
                builder.setTitle("Choose Action");
                //set options
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which==0){
                            Intent intent = getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packages);
                            if (intent != null){
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(UserAppActivity.this, "Error,Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (which==1){
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + installedApps.get(i).packages));
                            Toast.makeText(UserAppActivity.this, "" + installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        if (which==2){
                            String packages = installedApps.get(i).packages;
                            Intent intent = new Intent(Intent.ACTION_DELETE);
                            intent.setData(Uri.parse("package:" + packages));
                            startActivity(intent);
                            recreate();
                        }
                    }
                });
                builder.show();
            }
        });

        //getting Total no of installed apps
        String size = install_apps_list.getCount() + "";
        //show in text View above list view
        TextView countApps = findViewById(R.id.countApps);
        countApps.setText("Total Installed Apps:" +size);
    }

    public class AppList {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getPackages() {
            return packages;
        }

        public void setPackages(String packages) {
            this.packages = packages;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public AppList() {

        }

        public AppList(String name, Drawable icon, String packages, String version) {

            this.name = name;
            this.icon = icon;
            this.packages = packages;
            this.version = version;
        }

        String name;
        Drawable icon;
        String packages;
        String version;



    }

    public class AppAdapter extends BaseAdapter{
        LayoutInflater layoutInflater;
        List<AppList> listStorage;

        //cons
        AppAdapter(Context context,List<AppList>customizedListView){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;

        }
@Override
        public int getCount(){
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertview, ViewGroup parent ) {

        ViewHolder listViewHolder;
    if (convertview == null){
    listViewHolder = new ViewHolder();
    convertview = layoutInflater.inflate(R.layout.modelapps, parent, false);

    listViewHolder.list_app_name = convertview.findViewById(R.id.txt_app_name);
    listViewHolder.app_package = convertview.findViewById(R.id.app_package);
    listViewHolder.version = convertview.findViewById(R.id.version);
    listViewHolder.app_icon = convertview.findViewById(R.id.app_icon);

    convertview.setTag(listViewHolder);
}else{
    listViewHolder = (ViewHolder) convertview.getTag();
}
//set data to our Views
            listViewHolder.list_app_name.setText(listStorage.get(position).getName());
            listViewHolder.app_package.setText(listStorage.get(position).getPackages());
            listViewHolder.version.setText(listStorage.get(position).getVersion());
            listViewHolder.app_icon.setImageDrawable(listStorage.get(position).getIcon());

            return convertview;
        }
        class ViewHolder{
            TextView list_app_name,app_package, version;
            ImageView app_icon;

        }


    }

    private List<AppList>getInstalledApps(){

        apps = new ArrayList<AppList>();

        packs = getPackageManager().getInstalledPackages(0);

        for (int i =0; i <packs.size() ; i++) {
            PackageInfo p = packs.get(i);
            //validate if app not sys app
            if ((!isSystemPackage(p))){
                //get app name
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                //get app icon
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                //get app package name
                String packages = p.applicationInfo.packageName;
                //getapp vesion
                String version = p.versionName;

                //add data
                apps.add(new AppList(appName, icon,packages,version));
            }
        }
        return apps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        //fun to check if app is not system app we will display only user instaled apps
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
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

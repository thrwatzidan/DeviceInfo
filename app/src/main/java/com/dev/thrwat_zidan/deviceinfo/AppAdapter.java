package com.dev.thrwat_zidan.deviceinfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppAdapter extends ArrayAdapter<AppInfo> {
    private List<AppInfo> apps;
    private Context context;
    private PackageManager packageManager;
    LayoutInflater layoutInflater;

    public AppAdapter(@NonNull Context context,  List<AppInfo> apps) {
        super(context, R.layout.modelapps,apps);
        layoutInflater = LayoutInflater.from(context);
        packageManager = context.getPackageManager();
        this.apps = apps;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AppInfo current = apps.get(position);
        View view = convertView;
        if (null == view){
            view = layoutInflater.inflate(R.layout.modelapps, parent,false);

        }

            TextView appName = (TextView) view.findViewById(R.id.txt_app_name);
            appName.setText(current.label);

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(current.info.packageName, 0);
if (!TextUtils.isEmpty(packageInfo.versionName))
{
    String versionInfo = String.format("%s", packageInfo.versionName);
    TextView textversion = (TextView) view.findViewById(R.id.version);
    textversion.setText(versionInfo);
}
if (!TextUtils.isEmpty(current.info.packageName)){
    TextView textSubTitle = (TextView) view.findViewById(R.id.app_package);
    textSubTitle.setText(current.info.packageName);
}


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.app_icon);
        Drawable background = current.info.loadIcon(packageManager);
        imageView.setBackgroundDrawable(background);

        return view;
    }
}



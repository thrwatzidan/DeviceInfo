package com.dev.thrwat_zidan.deviceinfo;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable{

    public MyAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
        this.filterList = models;
    }

    Context context;
    ArrayList<Model> models ,filterList;
    CustomFilter filter;



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_model, null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.nameText.setText(models.get(position).getName());
        holder.img.setImageResource(models.get(position).getImg());
//implement itemckcliklistener
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //General
                if (models.get(pos).getName().equals("General")){
                    Intent intent = new Intent(context, GeneralActivity.class);
                    context.startActivity(intent);
                }
                //Device ID
               else if (models.get(pos).getName().equals("Device ID")){
                    Intent intent = new Intent(context, DeviceActivity.class);
                    context.startActivity(intent);
                }
                //Display
              else  if (models.get(pos).getName().equals("Display")){
                    Intent intent = new Intent(context, DisplayActivity.class);
                    context.startActivity(intent);
                }
                //Battery
                else  if (models.get(pos).getName().equals("Battery")){
                    Intent intent = new Intent(context, BatteryActivity.class);
                    context.startActivity(intent);
                }
                //User Apps
                else if (models.get(pos).getName().equals("User Apps")){
                    Intent intent = new Intent(context, UserAppActivity.class);
                    context.startActivity(intent);
                }
                //System Apps
                else  if (models.get(pos).getName().equals("System Apps")){
                    Intent intent = new Intent(context, SystemAppActivity.class);
                    context.startActivity(intent);
                }
                //Memory
                else if (models.get(pos).getName().equals("Memory")){
                    Intent intent = new Intent(context, MemoryActivity.class);
                    context.startActivity(intent);                }
                //CPU
                else if (models.get(pos).getName().equals("CPU")){
                    Intent intent = new Intent(context, CPUActivity.class);
                    context.startActivity(intent);
                }
                //Sensors
                else  if (models.get(pos).getName().equals("Sensors")){
                    Intent intent = new Intent(context, SensorActivity.class);
                    context.startActivity(intent);
                }
                //SIM
                else  if (models.get(pos).getName().equals("SIM")){
                    Intent intent = new Intent(context, SimActivity.class);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public Filter getFilter() {

        if (filter==null){
            filter = new CustomFilter(filterList, this);
        }

        return filter;
    }
}

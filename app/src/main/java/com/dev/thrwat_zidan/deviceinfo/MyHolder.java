package com.dev.thrwat_zidan.deviceinfo;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img;
    TextView nameText;
    ItemClickListener itemClickListener;
    public MyHolder(View itemview){

        super(itemview);
        this.img = itemview.findViewById(R.id.model_Image);
        this.nameText = itemview.findViewById(R.id.model_txt);
        itemview.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){

        this.itemClickListener = ic;
    }
}

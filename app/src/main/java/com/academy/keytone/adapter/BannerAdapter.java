package com.academy.keytone.adapter;



import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.academy.keytone.R;
import com.academy.keytone.activity.PageLink;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class BannerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<HashMap<String,String>> images;
    private LayoutInflater layoutInflater;


    public BannerAdapter(Context context,ArrayList<HashMap<String,String>> images) {
        this.context = context;
        this.images = images;
        this.layoutInflater = LayoutInflater.from(context);

        //layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.custom_layout,
                container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        TextView textView = itemView.findViewById(R.id.txt);
        Picasso.get().load(images.get(position).get("image")).into(imageView);


        if(!(images.get(position).get("link").equals(""))){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, PageLink.class);
                    intent.putExtra("link",images.get(position).get("link"));
                    context.startActivity(intent);

                }
            });
        }

        /*Glide.with(context).load(images.get(position).get("image"))
                .placeholder(R.mipmap.homebanner)
                .into(imageView);*/
         textView.setText(images.get(position).get("name"));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
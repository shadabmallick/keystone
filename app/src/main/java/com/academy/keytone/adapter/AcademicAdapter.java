package com.academy.keytone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AcademicAdapter extends RecyclerView.Adapter<AcademicAdapter.GroceryViewHolder>{
   // public List<News> horizontalGrocderyList;
    ArrayList<HashMap<String,String>> horizontalGrocderyList;
    ArrayList<HashMap<String,String>> Array_category;
    Context context;

    public AcademicAdapter(ArrayList<HashMap<String,String>> horizontalGrocderyList, ArrayList<HashMap<String,String>> Array_category, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.Array_category= Array_category;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {

        if (Array_category.get(position).get("name").equals("Academics")) {
            //  holder.imageView.setImageResource(horizontalGrocderyList.get(position).get("image"));
            Picasso.get().load(horizontalGrocderyList.get(position).get("image")).error(R.mipmap.index).into(holder.imageView);
            Log.d(TAG, "onBindViewHolder: " + horizontalGrocderyList.get(position).get("image"));
            //  holder.time.setText(horizontalGrocderyList.get(position).getTime());
            holder.title1.setText(horizontalGrocderyList.get(position).get("title"));
            holder.title2.setText(horizontalGrocderyList.get(position).get("description"));
            //   holder.title4.setText(horizontalGrocderyList.get(position).getTitle1());
            holder.title5.setText(horizontalGrocderyList.get(position).get("datetime"));
        }
    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
       RelativeLayout rl_main;
        TextView title,subtitle,time,title1,title2,title4,title5;
        ImageView imageView;
        public GroceryViewHolder(View view) {
            super(view);
            subtitle=view.findViewById(R.id.title1);
            title=view.findViewById(R.id.title2);
            time=view.findViewById(R.id.title3);

            rl_main=view.findViewById(R.id.rl_main);
            title1=view.findViewById(R.id.title1);
            title2=view.findViewById(R.id.title2);
            imageView=view.findViewById(R.id.title);
            title4=view.findViewById(R.id.title4);
            title5=view.findViewById(R.id.title22);
        }
    }
}
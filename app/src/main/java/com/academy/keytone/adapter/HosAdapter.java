package com.academy.keytone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.academy.keytone.model.News;

import java.util.List;

public class HosAdapter extends RecyclerView.Adapter<HosAdapter.GroceryViewHolder>{
    private List<News> horizontalGrocderyList;
    Context context;

    public HosAdapter(List<News> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hos, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
      //  holder.imageView.setImageResource(horizontalGrocderyList.get(position).getProductImage());

      //  holder.time.setText(horizontalGrocderyList.get(position).getTime());
        holder.parents.setText(horizontalGrocderyList.get(position).getTitle());
        holder.date.setText(horizontalGrocderyList.get(position).getSubtitle());
     //   holder.title4.setText(horizontalGrocderyList.get(position).getTitle1());
        holder.title1.setText(horizontalGrocderyList.get(position).getTitle1());
        holder.txt_month.setText(horizontalGrocderyList.get(position).getTitle2());
    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
       RelativeLayout rl_main;
        TextView txt_month,date,parents,title1;
        ImageView imageView;
        public GroceryViewHolder(View view) {
            super(view);
            date=view.findViewById(R.id.txt_1);
            txt_month=view.findViewById(R.id.txt_month);
            parents=view.findViewById(R.id.txt_2);

            rl_main=view.findViewById(R.id.rl_main);
            title1=view.findViewById(R.id.txt_3);
            imageView=view.findViewById(R.id.title);
        }
    }
}
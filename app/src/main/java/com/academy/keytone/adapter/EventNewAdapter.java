package com.academy.keytone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
//import com.fish.keytone.fragment.EventsDetails;
import com.academy.keytone.fragment.EventsDetails;
import com.academy.keytone.model.EventOne;

import java.util.List;

public class EventNewAdapter extends RecyclerView.Adapter<EventNewAdapter.GroceryViewHolder>{
    private List<EventOne> horizontalGrocderyList;
    Context context;

    public EventNewAdapter(List<EventOne> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
        holder.imageView.setImageResource(horizontalGrocderyList.get(position).getProductImage());

     //   holder.time.setText(horizontalGrocderyList.get(position).getTime());
        holder.title1.setText(horizontalGrocderyList.get(position).getTitle());
        holder.title2.setText(horizontalGrocderyList.get(position).getTitle2());
        holder.title4.setText(horizontalGrocderyList.get(position).getTitle1());
     //   holder.title5.setText(horizontalGrocderyList.get(position).getTitle2());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AppCompatActivity activity4 = (AppCompatActivity)context;
        Fragment myFragment4= new EventsDetails();
        activity4.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment4).addToBackStack(null).commit();
    }
});
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
          /*  title=view.findViewById(R.id.title2);
            time=view.findViewById(R.id.title3);*/

            rl_main=view.findViewById(R.id.rl_main);
            title1=view.findViewById(R.id.txt_one);
            title2=view.findViewById(R.id.txt_two);
            imageView=view.findViewById(R.id.img_top);
            title4=view.findViewById(R.id.txt_four);
          //  title5=view.findViewById(R.id.title5);
        }
    }
}
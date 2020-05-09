package com.academy.keytone.fragment;

import android.app.ProgressDialog;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;

import com.academy.keytone.Slider.SwipeController;
import com.academy.keytone.Slider.SwipeControllerActions;
import com.academy.keytone.adapter.MessageAdapter;
import com.academy.keytone.model.notice;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MessageFragment extends Fragment {
    View view;
    TextView textView5;

    SwipeController swipeController = null;
    private List<notice> groceryList1 ;
    private RecyclerView groceryRecyclerView,recyclerView;
    private MessageAdapter groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog dialog;
    ImageView back;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
   RelativeLayout rel_tool;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_fragment, container, false);
        Log.d(TAG, "onCreateView: ");
        // dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        rel_tool=getActivity().findViewById(R.id.rel_tool);
        rel_tool.setVisibility(View.VISIBLE);
       //groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler_message);
        //textView5 = view.findViewById(R.id.textView5);
        groceryList1 = new ArrayList<>();
        populategroceryList();

        back  =getActivity().findViewById(R.id.back);

        back.setVisibility(View.GONE);

        groceryAdapter = new MessageAdapter(groceryList1, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryAdapter);
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                groceryAdapter.horizontalGrocderyList.remove(position);
                groceryAdapter.notifyItemRemoved(position);
                groceryAdapter.notifyItemRangeChanged(position, groceryAdapter.getItemCount());
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });



        return view;
    }
    private void populategroceryList(){
        groceryList1.clear();
        notice notice1 = new notice("Message Title","Lorem Ipsum is simply dummy text of the printing "
                   ,"3 hours ago");
        notice notice2 = new notice("Message Title","Lorem Ipsum is simply dummy text of the printing",
                "3 hours ago");
        notice notice3 = new notice("Message Title","Junior High school assembly has been cancelled today due to flu",
                "3 hours ago");
        notice notice4 = new notice("Message Title","Junior High school assembly has been cancelled today due to flu",
                "3 hours ago");
        notice notice5 = new notice("Message Title","Junior High school assembly has been cancelled today due to flu",
                "3 hours ago");
        notice notice6 = new notice("Message Title","Junior High school assembly has been cancelled today due to flu",
                "3 hours ago");

        groceryList1.add(notice1);
        groceryList1.add(notice2);
        groceryList1.add(notice3);
        groceryList1.add(notice4);
        groceryList1.add(notice5);
        groceryList1.add(notice6);
        // groceryAdapter.notifyDataSetChanged();
    }
}
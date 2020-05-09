package com.academy.keytone.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.academy.keytone.R;
import com.academy.keytone.util.GlobalClass;

import static com.android.volley.VolleyLog.TAG;

public class CustomGridViewActivity extends BaseAdapter {

    private Context mContext;
    private final String[] gridViewString;
    private final int[] gridViewImageId;
    GlobalClass globalClass;

    public CustomGridViewActivity(Context context, String[] gridViewString, int[] gridViewImageId,
    CustomGridViewActivity.onItemClickListner mListner) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
        this.mListner=mListner;
        globalClass = (GlobalClass)mContext.getApplicationContext();

    }

    @Override
    public int getCount() {
        return gridViewString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.grid_item, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            LinearLayout ll_main =  gridViewAndroid.findViewById(R.id.android_custom_gridview_layout);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);

            textViewAndroid.setText(gridViewString[i]);
            imageViewAndroid.setImageResource(gridViewImageId[i]);
            gridViewAndroid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListner.onItemClick(i);


                }
            });
        } else {
            gridViewAndroid = (View) convertView;
            Log.d(TAG, "getView: "+gridViewImageId[3]);
            Log.d(TAG, "getView: "+gridViewString[3]);
        }

        return gridViewAndroid;
    }
    private CustomGridViewActivity.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int i);

        boolean onBackPressed();

        //   void onClickForPay(HashMap<String, String> hashMap);
    }
}
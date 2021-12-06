package com.example.weatherapplication.hw9_2.ui.main;

import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.weatherapplication.R;
import com.example.weatherapplication.hw9_2.Statics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GridListAdapter implements ListAdapter {

    GridListAdapter() {

    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONArray jsonArray = null;
        View v = null;
        try {
            jsonArray = new JSONArray(Statics.weatherDetails);
            JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());


            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_tab1_grid, parent, false);
            if(position != 4) ((ImageView)(v.findViewById(R.id.image))).setImageTintList(ColorStateList.valueOf(Color.BLACK));
            switch (position) {
                case 0:
                    ((ImageView)(v.findViewById(R.id.image))).setImageResource(R.drawable.weather_windy);
                    ((TextView)(v.findViewById(R.id.textview))).setText(jsonObject.get("wind") + " mph\n Wind Speed");
                    break;
                case 1:
                    ((ImageView)(v.findViewById(R.id.image))).setImageResource(R.drawable.gauge);
                    ((TextView)(v.findViewById(R.id.textview))).setText(jsonObject.get("pressure") + " inHg\n Pressure");

                    break;
                case 2:
                    ((ImageView)(v.findViewById(R.id.image))).setImageResource(R.drawable.weather_pouring);
                    ((TextView)(v.findViewById(R.id.textview))).setText(jsonObject.get("humidity") + "%\n Precipitation");

                    break;
                case 3:
                    ((ImageView)(v.findViewById(R.id.image))).setImageResource(R.drawable.thermometer_low);
                    ((TextView)(v.findViewById(R.id.textview))).setText(jsonObject.get("temp") + " °F\n Temperature");
                    break;
                case 4:
                    ((ImageView)(v.findViewById(R.id.image))).setImageResource(Statics.drawableMap.get("" + jsonObject.get("status")));
                    ((TextView)(v.findViewById(R.id.textview))).setText(Statics.stateMap.get("" + jsonObject.get("status")));
                    break;
                case 5:
                    ((ImageView)(v.findViewById(R.id.image))).setImageResource(R.drawable.water_percent);
                    ((TextView)(v.findViewById(R.id.textview))).setText(jsonObject.get("humidity") + " %\n Humidity");
                    break;
                case 6:
                    ((ImageView)(v.findViewById(R.id.image))).setImageResource(R.drawable.eye_outline);
                    ((TextView)(v.findViewById(R.id.textview))).setText(jsonObject.get("visibility") + " mi\n Visibility");
                    break;
                case 7:
                    ((ImageView)(v.findViewById(R.id.image))).setImageResource(R.drawable.weather_fog);
                    ((TextView)(v.findViewById(R.id.textview))).setText(jsonObject.get("cloud") + " %\n Cloud Cover");
                    break;
                case 8:
                    ((ImageView)(v.findViewById(R.id.image))).setImageResource(R.drawable.earth);
                    ((TextView)(v.findViewById(R.id.textview))).setText("1.00 Ozone");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

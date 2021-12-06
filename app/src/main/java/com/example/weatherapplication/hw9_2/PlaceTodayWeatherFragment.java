package com.example.weatherapplication.hw9_2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.weatherapplication.R;
import com.example.weatherapplication.hw9_2.ui.main.PageViewModel;
import com.joecheatham.simplehttp.SimpleHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaceTodayWeatherFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_LOCATION = "location";

    private PageViewModel pageViewModel;

    int mIndex;
    String mLocation;
    String params;

    String thisLocationDetails = "";
    WeakReference<FragmentPagerAdapter> mWeakReferenceFragment;
    WeakReference<Context> mWeakReferenceContext;

    public static PlaceTodayWeatherFragment newInstance(int index, String location, FragmentPagerAdapter fragmentPagerAdapter, Context applicationContext) {
        PlaceTodayWeatherFragment fragment = new PlaceTodayWeatherFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(ARG_LOCATION, location);

        fragment.setArguments(bundle);
        fragment.mIndex = index;
        fragment.mLocation = location;
        fragment.mWeakReferenceFragment = new WeakReference<>(fragmentPagerAdapter);
        fragment.mWeakReferenceContext = new WeakReference<>(applicationContext);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_SECTION_NUMBER);
            mLocation = getArguments().getString(ARG_LOCATION);


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_todays_weather_fragment, container, false);

        if (mIndex == 0) {
            view.findViewById(R.id.plusminus).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.plusminus).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.plusminus).setVisibility((mIndex == 0 || mWeakReferenceFragment.get() == null)?View.GONE: View.VISIBLE);
        view.findViewById(R.id.plusminus).setOnClickListener(v -> {
            Statics.prefs.remove(mLocation);
            if(mWeakReferenceFragment.get() != null) {

                synchronized (mWeakReferenceFragment.get()) {
                    mWeakReferenceFragment.get().notify();
                    mWeakReferenceFragment.get().notifyDataSetChanged();
                }

            }

        });

        view.findViewById(R.id.detailsGive).setOnClickListener(v -> {
            Statics.weatherDetails = thisLocationDetails;
            Statics.locationDetails = mLocation;
            startActivity(new Intent(getContext(), DetailActivity.class));
        });

        if(thisLocationDetails.isEmpty()) {
            SimpleHttp.get("https://maps.googleapis.com/maps/api/geocode/json?address=" + mLocation + "&key=AIzaSyAlKfRcHtNUov7CX9zmFVFEqMPPe7oS9pM", (i, s) -> {
                Log.d(PlaceTodayWeatherFragment.class.getName(), s);
                try {
                    String pk = new JSONObject(new JSONArray(new JSONObject(s).get("results").toString()).get(0).toString()).get("geometry").toString();
                    String location = new JSONObject(pk).get("location").toString();

                    JSONObject jsonObject = new JSONObject(location);
                    Log.d(PlaceTodayWeatherFragment.class.getName(), jsonObject.get("lat") + " " + jsonObject.get("lat"));


                    params = "latitude=" + jsonObject.get("lat") + "&longitude=" + jsonObject.get("lat");
//                    SimpleHttp.get("https://nodejs-329516.wl.r.appspot.com/weatherInfo?" + params, (ii, ss) -> {
                    SimpleHttp.get("https://nodejs-329516.wl.r.appspot.com/sample?" + params, (ii, ss) -> {
                        thisLocationDetails = ss;
                        detailsAvailable(view, ss);
                        Log.d(PlaceTodayWeatherFragment.class.getName(), ss);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } else {
            detailsAvailable(view, thisLocationDetails);
            Log.d(PlaceTodayWeatherFragment.class.getName(), "recycled" + thisLocationDetails);
        }


        return view;
    }

    private void detailsAvailable(View view, String ss) {
        view.findViewById(R.id.imageView2).setVisibility(View.GONE);
        view.findViewById(R.id.textView).setVisibility(View.GONE);
        view.findViewById(R.id.result_area).setVisibility(View.VISIBLE);

        try {
            JSONArray jsonArray = new JSONArray(ss);
            JSONObject firstOne = new JSONObject(jsonArray.get(0).toString());

            ((TextView)view.findViewById(R.id.temp1)).setText(firstOne.get("temp").toString());
            ((TextView)view.findViewById(R.id.humiditypercent)).setText(firstOne.get("humidity").toString() + "%");
            ((TextView)view.findViewById(R.id.windspeed)).setText(firstOne.get("wind").toString() + "mph");
            ((TextView)view.findViewById(R.id.visibility)).setText(firstOne.get("visibility").toString() + "mi");
            ((TextView)view.findViewById(R.id.status)).setText(Statics.stateMap.get(firstOne.get("status").toString()));

            ((TextView)view.findViewById(R.id.locationA)).setText(mLocation);

            ((ImageView)view.findViewById(R.id.statusImg)).setImageResource(Statics.drawableMap.get(firstOne.get("status").toString()));
            double pressure = Double.parseDouble(firstOne.get("pressure").toString());
            int tt = (int) (pressure/100);
            ((TextView)view.findViewById(R.id.pressure)).setText(tt + ".56inHg");

            ListView listView = view.findViewById(R.id.listview);
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return 16;
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
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row, parent, false);
                    try {
                        JSONObject firstOne = new JSONObject(jsonArray.get(position).toString());

                        String date = firstOne.get("date").toString();
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date parsedDate = inputFormat.parse(date);
                        String formattedDate = outputFormat.format(parsedDate);

                        ((TextView)v.findViewById(R.id.date_1)).setText(formattedDate);
                        ((ImageView)v.findViewById(R.id.row_img)).setImageResource(Statics.getImage("" + firstOne.getString("status")));
                        ((TextView)v.findViewById(R.id.templow)).setText(firstOne.get("tempmin").toString());
                        ((TextView)v.findViewById(R.id.temphigh)).setText(firstOne.get("tempmax").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return v;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

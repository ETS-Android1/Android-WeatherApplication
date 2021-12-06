package com.example.weatherapplication.hw9_2.pojo;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Suggestion{
    private final ArrayList <String> _0 = new ArrayList <String> ();
    private final ArrayList <String> _1 = new ArrayList<String>();
    public ArrayList <String> suggestion = new ArrayList<String>();

    static List<String> states = Arrays.asList("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming");
    static List<String> abbrev = Arrays.asList("AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY");

    public static Suggestion deserialize(String json) {
        Suggestion s = new Suggestion();
        try {
            JSONArray _2String;
            _2String = new JSONArray(json).getJSONArray(0);
            for (int i = 0; i < _2String.length(); i++) {
                s._0.add((String) _2String.get(i));
            }

            _2String = new JSONArray(json).getJSONArray(1);
            for (int i = 0; i < _2String.length(); i++) {
                int i1 = abbrev.indexOf((String) _2String.get(i));
                s._1.add(states.get(i1));
            }

            for (int i = 0; i < s._0.size(); i++) {
                s.suggestion.add(s._0.get(i) + ", " + s._1.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(Suggestion.class.getName(), "JSON:" + s);
        return s;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                suggestion +
                '}';
    }
}

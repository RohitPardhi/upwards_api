package com.euphoric.upwards_api;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.support.v4.widget.AutoSizeableTextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    static RecyclerView recyclerView;
    String api_url = "https://www.datakick.org/api/items";
    static  JSONArray  jsn;
    static List<data_list> data;
    static  Adpter recyclerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        data = new ArrayList<>();
        fetchdata();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        recyclerAdpter = new Adpter(this,data);



    }


    void fetchdata() {
        StringRequest request = new StringRequest(api_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsn  = new JSONArray(response);
                    Data_parse(0 );
                    recyclerView.setAdapter(recyclerAdpter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    public static void Data_parse(int n) throws JSONException {

        if(n <= 50){
            String key="";
            for(int i =n; i< n+50;i++){
                String apidata = "";
                JSONObject JO = (JSONObject) jsn.get(i);
                Iterator<String> itr = JO.keys();
                while (itr.hasNext()){
                    key = itr.next();
                    apidata +="\n"+ key.toString().toUpperCase()+": "+JO.getString(key.toString());
                }
                data.add(new data_list(apidata));
            }
            recyclerAdpter.notifyDataSetChanged();
        }
    }

}

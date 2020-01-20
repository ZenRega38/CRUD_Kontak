package com.regadeveloper.crudkontak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RefreshInterface {
    public SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestKontak();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.red)
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
        requestKontak();
    }

    public void requestKontak(){
        AndroidNetworking.get("https://seno.idn.sch.id/index.php/kontak")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        swipeRefreshLayout.setRefreshing(false);
                        ArrayList<Kontak> listKontak = new ArrayList<>();
                        for (int i = 0; i<response.length();i++) {
                            try {
                                JSONObject jsonKontak = response.getJSONObject(i);
                                Kontak kontak = new Kontak(jsonKontak.getString("nama"),
                                        jsonKontak.getString("id"),
                                        jsonKontak.getString("nomor"));
                                listKontak.add(kontak);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        KontakAdapter kontakAdapter = new KontakAdapter(listKontak, MainActivity.this);
                        recyclerView.setAdapter(kontakAdapter);
                        Log.d("IDN",response.toString());

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, "Req Error : "+anError.getErrorBody(),
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }


    public void configData (View view) {
        Intent intent = new Intent(MainActivity.this,DataConfig.class);
        startActivity(intent);
    }
}

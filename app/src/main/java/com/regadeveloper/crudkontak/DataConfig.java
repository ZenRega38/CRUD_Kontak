package com.regadeveloper.crudkontak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class DataConfig extends AppCompatActivity {
    private EditText tambahnam, tambahno;
    private ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_config);
        tambahnam = findViewById(R.id.tambahnama);
        tambahno = findViewById(R.id.tambahnomor);
        Button simPan = findViewById(R.id.simpan);
        progBar = findViewById(R.id.probar);

        simPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progBar.setVisibility(View.VISIBLE);
                AndroidNetworking.post("https://seno.idn.sch.id/index.php/kontak")
                        .setPriority(Priority.MEDIUM)
                        .addUrlEncodeFormBodyParameter("nama",tambahnam.getText().toString())
                        .addUrlEncodeFormBodyParameter("nomor", tambahno.getText().toString())
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progBar.setVisibility(View.GONE);
                                Toast.makeText(DataConfig.this,"Sukses Menambahkan Kontak",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(DataConfig.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Exit",true);
                                startActivity(intent);
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(DataConfig.this,  "Gagal Menambahkan Kontak",Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }
}

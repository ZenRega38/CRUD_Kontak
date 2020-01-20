package com.regadeveloper.crudkontak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class KontakDetail extends AppCompatActivity {
    private EditText etNama, etNomor;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak_detail);
        etNama = findViewById(R.id.namaawal);
        etNomor = findViewById(R.id.nomorawal);
        Button btnEdit = findViewById(R.id.edit);
        progressBar = findViewById(R.id.probar2);
        ImageButton btnDel = findViewById(R.id.hapus);

        etNama.setText(getIntent().getStringExtra("nama"));
        etNomor.setText(getIntent().getStringExtra("nomor"));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                AndroidNetworking.put("https://seno.idn.sch.id/index.php/kontak")
                        .setPriority(Priority.MEDIUM)
                        .addUrlEncodeFormBodyParameter("id", getIntent().getStringExtra("id"))
                        .addUrlEncodeFormBodyParameter("nama", etNama.getText().toString())
                        .addUrlEncodeFormBodyParameter("nomor", etNomor.getText().toString())
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(KontakDetail.this,"Sukses Memperbaharui Kontak",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KontakDetail.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Exit",true);
                                startActivity(intent);
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(KontakDetail.this,  "Gagal Memperbaharui Kontak",Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                AndroidNetworking.delete("https://seno.idn.sch.id/index.php/kontak")
                        .setPriority(Priority.MEDIUM)
                        .addUrlEncodeFormBodyParameter("id", getIntent().getStringExtra("id"))
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(KontakDetail.this, "Sukses Menghapus Kontak", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KontakDetail.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Exit", true);
                                startActivity(intent);
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(KontakDetail.this, "Gagal Menghapus Kontak", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}

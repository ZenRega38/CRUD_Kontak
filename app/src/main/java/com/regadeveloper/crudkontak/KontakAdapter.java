package com.regadeveloper.crudkontak;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class KontakAdapter extends RecyclerView.Adapter<KontakAdapter.KontakViewHolder> {
    private RefreshInterface refreshInterface;
    private ArrayList<Kontak> listKontak;

    KontakAdapter(ArrayList<Kontak> listKontak, RefreshInterface refreshInterface) {
        this.listKontak = listKontak;
        this.refreshInterface = refreshInterface;
    }

    @NonNull
    @Override
    public KontakViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kontak, parent, false);
        return new KontakViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KontakViewHolder holder, final int position) {

        final Kontak kontak = listKontak.get(position);

        holder.tvNama.setText(kontak.getNamaKontak());
        holder.tvId.setText(kontak.getIdKontak());
        holder.tvNomor.setText(kontak.getNoKontak());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveEdit = new Intent(view.getContext(), KontakDetail.class);
                moveEdit.putExtra("nama", kontak.getNamaKontak());
                moveEdit.putExtra("nomor", kontak.getNoKontak());
                moveEdit.putExtra("id", kontak.getIdKontak());
                view.getContext().startActivity(moveEdit);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alertDialogBulider = new AlertDialog.Builder(view.getContext());
                alertDialogBulider.setTitle("Yakin, ingin menghapus?");
                alertDialogBulider
                        .setMessage("Serah sih..")
                        .setIcon(R.drawable.ic_delete_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton("Y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AndroidNetworking.delete("https://seno.idn.sch.id/index.php/kontak")
                                        .setPriority(Priority.MEDIUM)
                                        .addUrlEncodeFormBodyParameter("id", kontak.getIdKontak())
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Toast.makeText(view.getContext(), "Succes",Toast.LENGTH_SHORT).show();
                                                refreshInterface.requestKontak();
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                Toast.makeText(view.getContext(), "Gagal",Toast.LENGTH_SHORT).show();
                                                refreshInterface.requestKontak();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Gk", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBulider.create();
                alertDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return listKontak.size();
    }

    class KontakViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvId, tvNomor;
        private ImageButton btnDelete;

        KontakViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.deletbtn);
            tvNama = itemView.findViewById(R.id.namaKontak);
            tvId = itemView.findViewById(R.id.idKontak);
            tvNomor = itemView.findViewById(R.id.noKontak);

        }
    }
}

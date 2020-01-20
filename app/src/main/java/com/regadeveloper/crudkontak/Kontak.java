package com.regadeveloper.crudkontak;

import android.os.Parcel;
import android.os.Parcelable;

public class Kontak implements Parcelable {
    private String namaKontak, idKontak, noKontak;

    Kontak(String namaKontak, String idKontak, String noKontak) {
        this.namaKontak = namaKontak;
        this.idKontak = idKontak;
        this.noKontak = noKontak;
    }

    String getNamaKontak() {
        return namaKontak;
    }

    String getIdKontak() {
        return idKontak;
    }

    String getNoKontak() {
        return noKontak;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namaKontak);
        dest.writeString(this.idKontak);
        dest.writeString(this.noKontak);
    }

    private Kontak(Parcel in) {
        this.namaKontak = in.readString();
        this.idKontak = in.readString();
        this.noKontak = in.readString();
    }

    public static final Parcelable.Creator<Kontak> CREATOR = new Parcelable.Creator<Kontak>() {
        @Override
        public Kontak createFromParcel(Parcel source) {
            return new Kontak(source);
        }

        @Override
        public Kontak[] newArray(int size) {
            return new Kontak[size];
        }
    };
}

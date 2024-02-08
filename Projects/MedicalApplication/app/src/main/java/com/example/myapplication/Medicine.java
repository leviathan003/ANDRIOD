package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Medicine implements Parcelable {
    private String medName;
    private int medCost,medQty;

    public Medicine(String medName, int medCost,int medQty) {
        this.medName = medName;
        this.medCost = medCost;
        this.medQty = medQty;
    }

    public void setMedQty(int medQty) {
        this.medQty = medQty;
    }

    public String getMedName() {
        return medName;
    }

    public int getMedCost() {
        return medCost;
    }

    public int getMedQty() {
        return medQty;
    }

    private Medicine(Parcel in) {
        medName = in.readString();
        medCost = in.readInt();
        medQty = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(medName);
        dest.writeInt(medCost);
        dest.writeInt(medQty);
    }

    public static final Parcelable.Creator<Medicine> CREATOR = new Parcelable.Creator<Medicine>() {
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        public Medicine[] newArray(int size) {
            return new Medicine[size];

        }
    };
}

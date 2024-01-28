package com.example.myapplication;

import android.os.Parcelable;

import java.util.ArrayList;

public class medsList {
    private ArrayList<Medicine> medicineArrayList = new ArrayList<>();
    private ArrayList<Medicine> transferList = new ArrayList<>();
    public medsList() {
        medicineArrayList.add(new Medicine("Paracetamol 650",10,0));
        medicineArrayList.add(new Medicine("Aciloc ",20,0));
        medicineArrayList.add(new Medicine("Vitamin A-Z",30,0));
        medicineArrayList.add(new Medicine("Azithromycin",40,0));
        medicineArrayList.add(new Medicine("Albuterol",50,0));
        medicineArrayList.add(new Medicine("Ciprofloxacin",60,0));
        medicineArrayList.add(new Medicine("Amoxicillin",70,0));
        medicineArrayList.add(new Medicine("Cephalin",80,0));
        medicineArrayList.add(new Medicine("Acetaminophen",90,0));
        medicineArrayList.add(new Medicine("Advil",100,0));
        medicineArrayList.add(new Medicine("Doxycycline",110,0));
        medicineArrayList.add(new Medicine("Augmentin",120,0));
        medicineArrayList.add(new Medicine("Trimethoprim",130,0));
        medicineArrayList.add(new Medicine("Tazloc AM",140,0));
        medicineArrayList.add(new Medicine("Novomix 30(Insulin)",150,0));
    }

    public ArrayList<Medicine> getMedicineArrayList() {
        return medicineArrayList;
    }

}

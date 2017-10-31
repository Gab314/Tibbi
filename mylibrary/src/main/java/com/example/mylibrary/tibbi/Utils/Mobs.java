package com.example.mylibrary.tibbi.Utils;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Mobs implements Serializable, Parcelable{
    private String name = "Bear";
    private final double initial_HP = 100;

    private final double initial_WIL = 10; //WILL
    private final double initial_AGI = 10; //AGILITY
    private final double initial_DEX = 10; //DEXTERITY
    //Defensive stats
    private final double initial_DET = 10; //DETERMINATION
    private final double initial_REF = 10; //REFLECTION
    private final double initial_LUC = 10; //LUCK

    private int char_lvl = 0;

    private double total_HP = 0;

    private double total_WIL = 0;
    private double total_AGI = 0;
    private double total_DEX = 0;
    private double total_DET = 0;
    private double total_REF = 0;
    private double total_LUC = 0;


    public Mobs(){
        total_HP = initial_HP;
        total_WIL = initial_WIL;
        total_AGI = initial_AGI;
        total_DEX = initial_DEX;
        total_DET = initial_DET;
        total_REF = initial_REF;
        total_LUC = initial_LUC;
    }

    public Mobs(String mName, double[] stats){
        name = mName;
        total_HP = initial_HP + stats[0];
        total_WIL = initial_WIL + stats[1];
        total_AGI = initial_AGI + stats[2];
        total_DEX = initial_DEX + stats[3];

        total_DET = initial_DET + stats[4];
        total_REF = initial_REF + stats[5];
        total_LUC = initial_LUC + stats[6];
    }

    private Mobs(Parcel in) {
        name = in.readString();
        total_HP = in.readDouble();
        total_WIL = in.readDouble();
        total_AGI = in.readDouble();
        total_DEX = in.readDouble();
        total_DET = in.readDouble();
        total_REF = in.readDouble();
        total_LUC = in.readDouble();
    }

    public static final Creator<Mobs> CREATOR = new Creator<Mobs>() {
        @Override
        public Mobs createFromParcel(Parcel in) {
            return new Mobs(in);
        }

        @Override
        public Mobs[] newArray(int size) {
            return new Mobs[size];
        }
    };

    public double getTotal_HP(){
        return total_HP;
    }
    public double getTotal_WIL(){
        return total_WIL;
    }
    public double getTotal_AGI(){return total_AGI;}
    public double getTotal_DEX(){return total_DEX;}
    public double getTotal_DET(){return total_DET;}
    public double getTotal_REF(){return total_REF;}
    public double getTotal_LUC(){return total_LUC;}
    public String getName(){return name;}
    public void decreaseHP(double attack){
        total_HP = total_HP - attack;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(total_HP);
        parcel.writeDouble(total_WIL);
        parcel.writeDouble(total_AGI);
        parcel.writeDouble(total_DEX);
        parcel.writeDouble(total_DET);
        parcel.writeDouble(total_REF);
        parcel.writeDouble(total_LUC);
    }
}

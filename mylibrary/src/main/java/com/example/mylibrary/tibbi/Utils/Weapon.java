package com.example.mylibrary.tibbi.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Weapon implements Parcelable{
    private String name;
    private String type;
    private double HP = 0;

    private double WIL = 0;
    private double AGI = 0;
    private double DEX = 0;
    private double DET = 0;
    private double REF = 0;
    private double LUC = 0;

    public Weapon(String wName,String t, double[] s){
        name = wName;
        type = t;
        HP = s[0];
        WIL = s[1];
        AGI = s[2];
        DEX = s[3];
        DET = s[4];
        REF = s[5];
        LUC = s[6];
    }

    private Weapon(Parcel in) {
        name = in.readString();
        type = in.readString();
        HP = in.readDouble();
        WIL = in.readDouble();
        AGI = in.readDouble();
        DEX = in.readDouble();
        DET = in.readDouble();
        REF = in.readDouble();
        LUC = in.readDouble();
    }

    public static final Creator<Weapon> CREATOR = new Creator<Weapon>() {
        @Override
        public Weapon createFromParcel(Parcel in) {
            return new Weapon(in);
        }

        @Override
        public Weapon[] newArray(int size) {
            return new Weapon[size];
        }
    };

    public String getName(){return name;}
    public String getType(){return type;}
    public double getHP(){return HP;}
    public double getWIL(){return WIL;}
    public double getAGI(){return AGI;}
    public double getDEX(){return DEX;}
    public double getDET(){return DET;}
    public double getREF(){return REF;}
    public double getLUC(){return LUC;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(name);
        dest.writeDouble(HP);
        dest.writeDouble(WIL);
        dest.writeDouble(AGI);
        dest.writeDouble(DEX);
        dest.writeDouble(DET);
        dest.writeDouble(REF);
        dest.writeDouble(LUC);
    }
}

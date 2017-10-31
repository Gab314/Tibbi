package com.example.mylibrary.tibbi.Utils;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MainCharacter implements Serializable, Parcelable{
    private String name = "Char";
    private String c_class;
    private String flavor;
    private final double initial_HP = 100;
    //Offensive stats
    private double initial_WIL = 10; //WILL
    private double initial_AGI = 10; //AGILITY
    private double initial_DEX = 10; //DEXTERITY
    //Defensive stats
    private final double initial_DET = 10; //DETERMINATION
    private final double initial_REF = 10; //REFLECTION
    private final double initial_LUC = 10; //LUCK
    //weapon
    //multipliers
    private final double offensive_m = 5.0;
    private final double defensive_m = 5.0;

    private int char_lvl = 0;
    private double total_HP = 0;
    private double total_WIL = 0;
    private double total_AGI = 0;
    private double total_DEX = 0;
    private double total_DET = 0;
    private double total_REF = 0;
    private double total_LUC = 0;

    //HP function when lvl up


    public MainCharacter(String c, String f){
        flavor = f;
        c_class = c;
        switch (c){
            case "MER":{initial_WIL+= 10;
            name = "Mercenary";    break;}
            case "ASS":{initial_AGI+= 10;
                name = "Assassin"; break;}
            case "ARC":{initial_DEX+= 10;
                name = "Archer"; break;}
        }

        total_HP = initial_HP;
        total_WIL = initial_WIL;
        total_AGI = initial_AGI;
        total_DEX = initial_DEX;
        total_DET = initial_DET;
        total_REF = initial_REF;
        total_LUC = initial_LUC;
    }

    private MainCharacter(Parcel in) {
        flavor = in.readString();
        c_class = in.readString();
        total_HP = in.readDouble();
        total_WIL = in.readDouble();
        total_AGI = in.readDouble();
        total_DEX = in.readDouble();
        total_DET = in.readDouble();
        total_REF = in.readDouble();
        total_LUC = in.readDouble();
    }

    public static final Creator<MainCharacter> CREATOR = new Creator<MainCharacter>() {
        @Override
        public MainCharacter createFromParcel(Parcel in) {
            return new MainCharacter(in);
        }

        @Override
        public MainCharacter[] newArray(int size) {
            return new MainCharacter[size];
        }
    };

    public void addWeapon(Weapon weapon){
        total_HP = initial_HP + weapon.getHP();
        total_WIL = initial_WIL + weapon.getWIL();
        total_AGI = initial_AGI + weapon.getAGI();
        total_DEX = initial_DEX + weapon.getDEX();
        total_DET = initial_DET + weapon.getDET();
        total_REF = initial_REF + weapon.getREF();
        total_LUC = initial_LUC + weapon.getLUC();
    }

    public void addArmor(Integer[] integers){

    }

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
    public double getOffensive_m(){return offensive_m;}
    public double getDefensive_m(){return defensive_m;}
    public String getName(){return name;}
    public String getC_class(){return c_class;}
    public String getFlavor(){return flavor;}
    public double getChar_lvl(){
        return char_lvl;
    }

    public void decreaseHP(double attack){
        total_HP = total_HP - attack;
    }

    public void increase_lvl(double lvl){
        char_lvl++;
    }
    public void increase_HP(){
        total_HP = total_HP + 10;
    }
    public void increase_WIL(){
        total_WIL = total_WIL + 10;
    }
    public void increase_AGI(){
        total_AGI = total_AGI + 10;
    }
    public void increase_DEX(){
        total_DEX = total_DEX + 10;
    }
    public void increase_DET(){
        total_DET = total_DET + 10;
    }
    public void increase_REF(){
        total_REF = total_REF + 10;
    }
    public void increase_LUC(){
        total_LUC = total_LUC + 10;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(flavor);
        parcel.writeString(c_class);
        parcel.writeDouble(total_HP);
        parcel.writeDouble(total_WIL);
        parcel.writeDouble(total_AGI);
        parcel.writeDouble(total_DEX);
        parcel.writeDouble(total_DET);
        parcel.writeDouble(total_REF);
        parcel.writeDouble(total_LUC);
    }
}

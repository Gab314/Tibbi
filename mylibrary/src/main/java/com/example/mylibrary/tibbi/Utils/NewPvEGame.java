package com.example.mylibrary.tibbi.Utils;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class NewPvEGame implements Parcelable{
    private MainCharacter char1;
    private Mobs mobs1;
    private AI ai;
    private double[] weights;
    private String[][] outcome_matrix = {{"T","W","L"},{"L","T","W"},{"W","L","T"}};


    public NewPvEGame(MainCharacter c_1, Mobs m_1){
        char1 = c_1;
        mobs1 = m_1;
        int ai_lvl = 2;
        ai = new AI(ai_lvl);
    }

    protected NewPvEGame(Parcel in) {
        char1 = in.readParcelable(MainCharacter.class.getClassLoader());
        mobs1 = in.readParcelable(Mobs.class.getClassLoader());
        weights = in.createDoubleArray();
    }

    public static final Creator<NewPvEGame> CREATOR = new Creator<NewPvEGame>() {
        @Override
        public NewPvEGame createFromParcel(Parcel in) {
            return new NewPvEGame(in);
        }

        @Override
        public NewPvEGame[] newArray(int size) {
            return new NewPvEGame[size];
        }
    };

    public String[]  newRound(int i){
        String gResult;
        String gText[];

        gResult = gameOutcome(i);
        gText = netResult(gResult, i);
        return gText;
    }

    private String gameOutcome(int in){
        double[] mods;
        mods = ai.getModifiers();
        weights = mods;
        ai.addInt(in);
        Random rdn = new Random();
        int cpu_move = rdn.nextInt(100);

        if (cpu_move < mods[0]){
            cpu_move =2;
            return outcome_matrix[in][cpu_move];
        }if (( mods[0] < cpu_move && cpu_move < mods[0] + mods[1])){
            cpu_move = 0;
            return outcome_matrix[in][cpu_move];
        }else cpu_move =1;


        return outcome_matrix[in][cpu_move];
    }



    private String[] netResult(String s, int in){
        Random r = new Random();
        int rd = r.nextInt(5);
         double dmg;
         String[] outText = new String[3];
         outText[2] = s;
         outText[0] = "Hit!";
        double base_dmg = 10  + rd;
        if (s == "W"){
            switch (in){
                case 0:{
                    //Heavy > Fast
                    double ATK = char1.getTotal_WIL();
                    double DEF = mobs1.getTotal_DET();
                    if (!checkHit(ATK, DEF)){
                        outText[0] = "Enemy Parried!";
                        break;
                    }
                    if (ATK > DEF){
                        dmg = base_dmg + ATK - DEF;
                    }else dmg = base_dmg;
                    mobs1.decreaseHP(dmg);
                    outText[1] = checkResult();
                    break;}
                case 1:{
                    // Fast > Throw
                    double ATK = char1.getTotal_AGI();
                    double DEF = mobs1.getTotal_REF();
                    if (!checkHit(ATK, DEF)){
                        outText[0] = "Enemy Dodged!";
                        break;
                    }
                    if (ATK > DEF){
                        dmg = base_dmg + ATK - DEF;
                    }else dmg = base_dmg;
                    mobs1.decreaseHP(dmg);
                    outText[1] = checkResult();
                    break;}
                case 2:{
                    // Throw > Heavy
                    double ATK = char1.getTotal_DEX();
                    double DEF = mobs1.getTotal_LUC();
                    if (!checkHit(ATK, DEF)){
                        outText[0] = "Enemy Blocked!";
                        break;
                    }
                    if (ATK > DEF){
                        dmg = base_dmg + ATK - DEF;
                    }else dmg = base_dmg;
                    mobs1.decreaseHP(dmg);
                    outText[1] = checkResult();
                    break;}
            }
        }if (s == "L"){
            switch (in){
                case 0:{
                    //Heavy < Throw
                    double ATK = mobs1.getTotal_DEX();
                    double DEF = char1.getTotal_LUC();
                    if (!checkHit(ATK, DEF)){
                        outText[0] = "You Blocked!";
                        break;
                    }
                    if (ATK > DEF){
                        dmg = base_dmg + ATK - DEF;
                    }else dmg = base_dmg;
                    char1.decreaseHP(dmg);
                    outText[1] = checkResult();
                    break;}
                case 1:{
                    // Fast < Heavy
                    double ATK = mobs1.getTotal_WIL();
                    double DEF = char1.getTotal_DET();
                    if (!checkHit(ATK, DEF)){
                        outText[0] = "You Parried!";
                        break;
                    }
                    if (ATK > DEF){
                        dmg = base_dmg + ATK - DEF;
                    }else dmg = base_dmg;
                    char1.decreaseHP(dmg);
                    outText[1] = checkResult();
                    break;}
                case 2:{
                // Throw < Fast
                    double ATK = mobs1.getTotal_AGI();
                    double DEF = char1.getTotal_REF();
                    if (!checkHit(ATK, DEF)){
                        outText[0] = "You Dodged!";
                        break;
                    }
                    if (ATK > DEF){
                        dmg = base_dmg + ATK - DEF;
                    }else dmg = base_dmg;
                    char1.decreaseHP(dmg);
                    outText[1] = checkResult();
                    break;}
            }

        }
        if (s == "T"){
            outText[0] = "Tie!";
        }
        return outText;
    }

    private Boolean checkHit(double off, double def){
        Boolean hit_out;
        //chance multiplier
        double m = 2.0;
        double base = 80;
        double c_off_m = char1.getOffensive_m();
        double c_def_m = char1.getDefensive_m();
        //levels the stats
        double o = off / c_off_m ;
        double d = def / c_def_m;
        //equation to get chance
        double hit_chance;
        if ( o > d){
            hit_chance = ((o - d) * m) + base;
        }else hit_chance = base;

        Random r = new Random();
        int hit_random = r.nextInt(100);

        hit_out = hit_chance > hit_random;

        return hit_out;
    }
    private String checkResult(){
        String result = "NO";
        if ( char1.getTotal_HP() <= 0) {
            result = "DEFEAT!";
            return result;

        }
        if ( mobs1.getTotal_HP() <= 0) {
            result = "VICTORY!";
            return result;

        }
        return result;
    }
    public double[] getWeights(){
        return weights;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(char1, flags);
        dest.writeParcelable(mobs1, flags);
        dest.writeDoubleArray(weights);
    }
}

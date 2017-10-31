package com.example.mylibrary.tibbi.Utils;


import android.util.Log;

class AI {

    private int[] array;
    private int position, x, lvl;

    AI(int i){
        lvl = i;
        position = 0;
        x = 0;
        array = new int[i * 1000 ];
    }

    void addInt(int j){
        array[position] = j;
        position++;
        x = 1;
    }

    private int getLast1(){
        int c = position -x;
        return array[c];
    }
    private int[] getLast2(){
        int[] c = new int[2];
        c[0] = 3;
        c[1] = 3;
        if (position > 1){
            c[1] = array[position - 2*x];
        }
              c[0] = array[position - x];
        return c;
    }
    private int[] getLast3(){
        int[] c = new int[3];
        c[0] = 3;
        c[1] = 3;
        c[2] = 3;
        if (position > 2){
            c[2] = array[position - 3*x];
        }
        if (position > 1){
            c[1] = array[position - 2*x];
        }
        c[0] = array[position - x];
        return c;
    }
    double[] getModifiers(){
        double[] modifiers = new double[3];
        double[] m = new double[3];
        int wX = 3;
        int wY = 2;
        double rx,ry,rz, div;
        double total = 100.0;

        switch (lvl){
            case 1:{
                m[getLast1()] = total;
                break;
            }
            case 2:{
                // text
                int[] b;
                b = getLast2();
                m[b[0]] += total;
                if (b[1] != 3){
                    m[b[1]] += total;
                }


                break;
            }
            case 3:{
                // test
                int[] c;
                c = getLast3();
                m[c[0]] += total;
                if (c[1] != 3){
                    m[c[1]] += total;
                }
                if (c[2] != 3) {
                    m[c[2]] += total;
                }
                break;
            }
        }


        rx = ((33.3 * wX) + (wY * m[0]));
        ry = ((33.3 * wX) + (wY * m[1]));
        rz = ((33.3 * wX) + (wY * m[2]));

        div = rx + ry + rz;

        modifiers[0] =100* rx/div;
        modifiers[1] =100* ry/div;
        modifiers[2] =100* rz/div;
        Log.v(getClass().getName(),"0 = " + String.valueOf(modifiers[0]));
        Log.v(getClass().getName(),"1 = " + String.valueOf(modifiers[1]));
        Log.v(getClass().getName(),"2 = " + String.valueOf(modifiers[2]));
        return modifiers;
    }
}

package com.gym.GoldenGym.utils;

public class ObjectHelper {
    public static String getString(Object obj){
        if(obj instanceof String){
            return (String) obj;
        }else{
            return String.valueOf(obj);
        }
    }

    public static int getInt(Object obj){
        if(obj instanceof Float){
            return ((Float) obj).intValue();
        }else if(obj instanceof Double){
            return ((Double) obj).intValue();
        }else if(obj instanceof Integer){
            return (Integer) obj;
        }else{
            return Integer.parseInt(String.valueOf(obj));
        }
    }

    public static float getFloat(Object obj){
        if(obj instanceof Float){
            return (Float) obj;
        }else{
            return Float.parseFloat(String.valueOf(obj));
        }
    }

    public static double getDouble(Object obj){
        if(obj instanceof Double){
            return (Double) obj;
        }else{
            return Double.parseDouble(String.valueOf(obj));
        }
    }
}

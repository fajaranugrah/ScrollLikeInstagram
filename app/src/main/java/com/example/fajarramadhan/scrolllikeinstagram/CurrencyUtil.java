package com.example.fajarramadhan.scrolllikeinstagram;

import android.content.Context;

public class CurrencyUtil {
    public static String offerAmountDefault(String amount){
        float money = Float.parseFloat(amount);
        if(money > 0){
            return amount + "";
        }
        return money+"";
    }
}

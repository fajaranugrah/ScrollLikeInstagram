package com.example.fajarramadhan.scrolllikeinstagram;

import android.text.Html;

import org.apache.commons.lang3.StringEscapeUtils;

public class EmojiUtil2 {
    public static String encode(String text){
        return StringEscapeUtils.escapeHtml4(text);
    }

    public static String decode(String text){
        if(text.contains("&#")||text.contains("&")){
            if (text.toString().contains("\n")){
                String ToHtml = text.toString().replaceAll("\n", "<br/>");
                return Html.fromHtml(StringEscapeUtils.unescapeHtml4(ToHtml)).toString();
            } else {
                return Html.fromHtml(StringEscapeUtils.unescapeHtml4(text)).toString();
            }
        }
        return StringEscapeUtils.unescapeJava(text);
    }
}

package com.example.fajarramadhan.scrolllikeinstagram;

import android.text.Html;

import org.apache.commons.lang3.StringEscapeUtils;

public class EmojiUtil {
    public static String encode(String text){
        return StringEscapeUtils.escapeHtml3(text);
    }

    public static String decode(String text){
        if(text.contains("&#")||text.contains("&")){
            return Html.fromHtml(StringEscapeUtils.unescapeHtml3(text)).toString();
        }
        return StringEscapeUtils.unescapeJava(text);
    }

    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}

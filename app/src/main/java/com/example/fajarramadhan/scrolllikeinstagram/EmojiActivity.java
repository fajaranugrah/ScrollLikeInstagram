package com.example.fajarramadhan.scrolllikeinstagram;

import android.os.Bundle;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.text.emoji.widget.EmojiAppCompatTextView;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiActivity extends AppCompatActivity {

    EmojiAppCompatTextView money, pets, support;
    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);

        EmojiCompat.Config config = new BundledEmojiCompatConfig(EmojiActivity.this);
        EmojiCompat.init(config);

        //CharSequence processed = EmojiCompat.get().process("\uD83D\uDC69\u200D\uD83D\uDCBB");

        support = (EmojiAppCompatTextView) findViewById(R.id.support);
        money = (EmojiAppCompatTextView) findViewById(R.id.money);
        pets = (EmojiAppCompatTextView) findViewById(R.id.pets);
        test = (TextView) findViewById(R.id.test);

        String smiley = "\ud83d\ude03";
        test.setText(EmojiUtil.getEmojiByUnicode(0x1F62D));
        support.setText("\uD83D\uDC69\u200D\uD83D\uDCBB");
        money.setText("0x1F4B0");
        pets.setText("\uD83D\uDC15");

        Pattern pattern = Pattern.compile("\\\\u([0-9A-Fa-f]{4,5})\\b");
        StringBuffer sb = new StringBuffer();
        Matcher m = pattern.matcher("\uD83D\uDC69\u200D\uD83D\uDCBB");
        while (m.find()) {
            int cp = Integer.parseInt(m.group(1), 16);
            String added = cp < 0x10000
                    ? String.valueOf((char) cp)
                    : new String(new int[] { cp }, 0, 1);
            m.appendReplacement(sb, added);
        }
        m.appendTail(sb);
        pets.setText(sb.toString());

        hani.momanii.supernova_emoji_library.Helper.EmojiconTextView supernov = (hani.momanii.supernova_emoji_library.Helper.EmojiconTextView) findViewById(R.id.supernova);
        supernov.setText("\uD83D\uDC69\u200D\uD83D\uDCBB");

    }
}
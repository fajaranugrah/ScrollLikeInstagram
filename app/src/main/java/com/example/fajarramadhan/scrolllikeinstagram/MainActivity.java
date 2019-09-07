package com.example.fajarramadhan.scrolllikeinstagram;

import android.content.Intent;
import android.graphics.Rect;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout;
    LinearLayout button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Button button = (Button) findViewById(R.id.chat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatMessage.class);
                startActivity(intent);
            }
        });

        Button button1 = (Button) findViewById(R.id.chat_short);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatMessageCustome.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.chat_user);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatUser.class);
                startActivity(intent);
            }
        });

        Button emoji = (Button) findViewById(R.id.emoji);
        emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmojiActivity.class);
                startActivity(intent);
            }
        });

        final EditText editText = (EditText) findViewById(R.id.input_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //Handle search key click

                    Intent intent = new Intent(MainActivity.this, ChatMessage.class);
                    startActivity(intent);
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    //Handle go key click

                    Intent intent = new Intent(MainActivity.this, ChatMessageCustome.class);
                    startActivity(intent);
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    //Handle done key click

                    Intent intent = new Intent(MainActivity.this, ChatUser.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        button3 = (LinearLayout) findViewById(R.id.linear);

        layout = (RelativeLayout) findViewById(R.id.rootLayout);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                layout.getWindowVisibleDisplayFrame(r);
                int screenHeight = layout.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {
                    Toast.makeText(MainActivity.this,"Keyboard is showing",Toast.LENGTH_SHORT).show();
                    button3.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this,"keyboard closed",Toast.LENGTH_SHORT).show();
                    button3.setVisibility(View.GONE);
                }
            }
        });

        final Button button4 = (Button) findViewById(R.id.username_btn);
        final Button button5 = (Button) findViewById(R.id.job_reference_btn);
        final Button button6 = (Button) findViewById(R.id.button_search);
        final Button button7 = (Button) findViewById(R.id.button_close);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button5.setBackground(getResources().getDrawable(R.drawable.button_white_left));
                button5.setTextColor(getResources().getColor(R.color.petbacker_accent_color));
                button4.setBackground(getResources().getDrawable(R.drawable.button_white_right));
                button4.setTextColor(getResources().getColor(R.color.white));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button4.setBackground(getResources().getDrawable(R.drawable.button_white_left));
                button4.setTextColor(getResources().getColor(R.color.petbacker_accent_color));
                button5.setBackground(getResources().getDrawable(R.drawable.button_white_right));
                button5.setTextColor(getResources().getColor(R.color.white));
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                editText.clearFocus();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                editText.clearFocus();
            }
        });

    }
}

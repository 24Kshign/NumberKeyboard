package com.share.jack.numberkeyboard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.share.jack.numberkeyboard.widget.FullScreenNumberKeyboardView;
import com.share.jack.numberkeyboard.widget.NumberKeyboardView;

public class MainActivity extends Activity implements FullScreenNumberKeyboardView.OnNumberClickListener {

    private FullScreenNumberKeyboardView mNkvKeyboard;
    private TextView mTvText;
    private String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mTvText = (TextView) findViewById(R.id.am_tv_text);
        mNkvKeyboard = (FullScreenNumberKeyboardView) findViewById(R.id.am_nkv_keyboard);
        mNkvKeyboard.setOnNumberClickListener(this);
    }

    @Override
    public void onNumberReturn(String number) {
        str += number;
        setTextContent(str);
    }

    @Override
    public void onNumberDelete() {
        if (str.length() <= 1) {
            str = "";
        } else {
            str = str.substring(0, str.length() - 1);
        }
        setTextContent(str);
    }

    private void setTextContent(String content) {
        mTvText.setText(content);
    }
}

package com.example.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void onClickNumber(View view) {
        String input = ((TextView)view).getText().toString();
        String oldText = textView.getText().toString();
        String newText = oldText.equals("0") ? input : oldText + input;
        textView.setText(newText);
    }
}

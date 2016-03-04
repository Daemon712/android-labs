package com.example.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.myapp.operations.Operation;

public class MyActivity extends Activity {
    private TextView textView;
    private Double displayNumber;
    private Double memoryNumber;
    private Operation operation;

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
        displayNumber = Double.parseDouble(newText);
    }

    public void onClickSeparator(View view){
        String oldText = textView.getText().toString();
        if (!oldText.contains(".")){
            textView.setText(oldText + ".");
        }
    }

    public void onClickClear(View view){
        displayNumber = null;
        memoryNumber = null;
        operation = null;
        textView.setText("0");
    }

    public void onClickSqrt(View view){
        if (displayNumber == null) return;
        displayNumber = Math.sqrt(displayNumber);
        textView.setText(displayNumber.toString());
    }
}

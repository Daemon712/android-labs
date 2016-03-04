package com.example.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        String newText = displayNumber == null ? input : oldText + input;
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
        if (displayNumber < 0){
            Toast.makeText(getApplicationContext(), "Нельзя извлечь корень из отрицательного числа", Toast.LENGTH_SHORT).show();
            return;
        }
        displayNumber = Math.sqrt(displayNumber);
        textView.setText(doubleToString(displayNumber));
    }

    public void onClick1DivX(View view){
        if (displayNumber == null) return;
        if (displayNumber == 0){
            Toast.makeText(getApplicationContext(), "Нельзя делить на 0", Toast.LENGTH_SHORT).show();
            return;
        }
        displayNumber = 1/displayNumber;
        textView.setText(doubleToString(displayNumber));
    }

    public void onClickSign(View view){
        if (displayNumber == null) return;
        displayNumber = -displayNumber;
        textView.setText(doubleToString(displayNumber));
    }

    public void onClickAdd(View view){
        setOperation(new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a + b;
            }
        });
    }

    public void onClickSub(View view){
        setOperation(new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a - b;
            }
        });
    }

    public void onClickMulti(View view){
        setOperation(new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a * b;
            }
        });
    }

    public void onClickDiv(View view){
        setOperation(new Operation() {
            @Override
            public double calculate(double a, double b) {
                if (b != 0) return a / b;
                Toast.makeText(getApplicationContext(), "Нельзя делить на ноль", Toast.LENGTH_SHORT).show();
                return 0;
            }
        });
    }

    public void onClickCalc(View view){
        if (memoryNumber == null) return;
        if (displayNumber == null) displayNumber = 0d;
        displayNumber = operation.calculate(memoryNumber, displayNumber);
        textView.setText(doubleToString(displayNumber));
        memoryNumber = null;
    }

    private static String doubleToString(Double number){
        return number.longValue() == number ?
                Long.toString(number.longValue()) :
                number.toString();
    }

    private void setOperation(Operation operation){
        this.operation = operation;
        if (memoryNumber == null) memoryNumber = 0d;
        if (displayNumber == null) return;
        memoryNumber = displayNumber;
        displayNumber = null;
    }

    public interface Operation {
        double calculate(double a, double b);
    }
}

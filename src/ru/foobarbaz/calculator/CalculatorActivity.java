package ru.foobarbaz.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import ru.foobarbaz.R;

public class CalculatorActivity extends Activity {
    private TextView textViewMain;
    private TextView textViewAdditional;
    private Double displayNumber;
    private Double memoryNumber;
    private Operation operation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        textViewMain = (TextView) findViewById(R.id.textViewMain);
        textViewAdditional = (TextView) findViewById(R.id.textViewAdditional);
    }

    public void onClickNumber(View view) {
        String input = ((TextView)view).getText().toString();
        String oldText = textViewMain.getText().toString();
        String newText = oldText.equals("0") ? input : oldText + input;
        textViewMain.setText(newText);
        displayNumber = Double.parseDouble(newText);
    }

    public void onClickSeparator(View view){
        String oldText = textViewMain.getText().toString();
        if (!oldText.contains(".")){
            textViewMain.setText(oldText + ".");
        }
    }

    public void onClickClear(View view){
        displayNumber = null;
        memoryNumber = null;
        operation = null;
        textViewMain.setText("0");
        textViewAdditional.setText("");
    }

    public void onClickSqrt(View view){
        if (displayNumber == null) return;
        if (displayNumber < 0){
            Toast.makeText(getApplicationContext(), "Нельзя извлечь корень из отрицательного числа", Toast.LENGTH_SHORT).show();
            return;
        }
        displayNumber = Math.sqrt(displayNumber);
        textViewMain.setText(doubleToString(displayNumber));
    }

    public void onClick1DivX(View view){
        if (displayNumber == null || displayNumber == 0){
            Toast.makeText(getApplicationContext(), "Нельзя делить на 0", Toast.LENGTH_SHORT).show();
            return;
        }
        displayNumber = 1/displayNumber;
        textViewMain.setText(doubleToString(displayNumber));
    }

    public void onClickSign(View view){
        if (displayNumber == null) return;
        displayNumber = -displayNumber;
        textViewMain.setText(doubleToString(displayNumber));
    }

    public void onClickAdd(View view){
        setOperation(new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a + b;
            }
        }, ((TextView)view).getText().toString());
    }

    public void onClickSub(View view){
        setOperation(new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a - b;
            }
        }, ((TextView)view).getText().toString());
    }

    public void onClickMulti(View view){
        setOperation(new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a * b;
            }
        }, ((TextView)view).getText().toString());
    }

    public void onClickDiv(View view){
        setOperation(new Operation() {
            @Override
            public double calculate(double a, double b) {
                if (b != 0) return a / b;
                Toast.makeText(getApplicationContext(), "Нельзя делить на ноль", Toast.LENGTH_SHORT).show();
                return 0;
            }
        }, ((TextView)view).getText().toString());
    }

    public void onClickCalc(View view){
        if (memoryNumber == null) return;
        if (displayNumber == null) displayNumber = 0d;
        displayNumber = operation.calculate(memoryNumber, displayNumber);
        textViewMain.setText(doubleToString(displayNumber));
        memoryNumber = null;
        operation = null;
        textViewAdditional.setText("");
    }

    private static String doubleToString(Double number){
        return number.longValue() == number ?
                Long.toString(number.longValue()) :
                number.toString();
    }

    private void setOperation(Operation operation, String operator){
        if (displayNumber != null){
            if (this.operation != null){
                memoryNumber = this.operation.calculate(memoryNumber, displayNumber);
            } else {
                memoryNumber = displayNumber;
            }

            displayNumber = null;
            textViewMain.setText("0");
            textViewAdditional.setText(doubleToString(memoryNumber) + "  " + operator);
        }
        this.operation = operation;
    }

    public interface Operation {
        double calculate(double a, double b);
    }
}

package ru.foobarbaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import ru.foobarbaz.calculator.CalculatorActivity;
import ru.foobarbaz.converter.ConverterActivity;

public class MainActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void openCalculator(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void openConverter(View view) {
        Intent intent = new Intent(this, ConverterActivity.class);
        startActivity(intent);
    }
}

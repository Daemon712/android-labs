package ru.foobarbaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ru.foobarbaz.calculator.CalculatorActivity;
import ru.foobarbaz.converter.ConverterActivity;
import ru.foobarbaz.notebook.activity.NoteListActivity;
import ru.foobarbaz.paint.PaintActivity;

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

    public void openNoteBook(View view) {
        Intent intent = new Intent(this, NoteListActivity.class);
        startActivity(intent);
    }

    public void openPaint(View view) {
        Intent intent = new Intent(this, PaintActivity.class);
        startActivity(intent);
    }
}

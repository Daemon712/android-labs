package ru.foobarbaz.paint;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import ru.foobarbaz.R;

import java.util.Arrays;
import java.util.List;

public class PaintActivity extends Activity implements View.OnClickListener {

    private DrawingView drawingView;
    private Button chooseLine;
    private Button chooseRectangle;
    private Button chooseCircle;
    private Button drag;
    private Button remove;
    private Button clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint);

        drawingView = (DrawingView) findViewById(R.id.drawingView);

        chooseLine = (Button) findViewById(R.id.lineButton);
        chooseLine.setOnClickListener(this);

        chooseRectangle = (Button) findViewById(R.id.rectangleButton);
        chooseRectangle.setOnClickListener(this);

        chooseCircle = (Button) findViewById(R.id.circleButton);
        chooseCircle.setOnClickListener(this);

        drag = (Button) findViewById(R.id.dragButton);
        drag.setOnClickListener(this);

        remove = (Button) findViewById(R.id.removeLastButton);
        remove.setOnClickListener(this);

        clean = (Button) findViewById(R.id.cleanButton);
        clean.setOnClickListener(this);

        createWidthPicker();
        createSpinner();
    }

    private void createWidthPicker() {
        List<Integer> values = Arrays.asList(1, 3, 5, 10, 15, 20);
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner widthPicker = (Spinner) findViewById(R.id.widthPicker);
        widthPicker.setAdapter(dataAdapter);
        widthPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drawingView.setWidth((Integer)widthPicker.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void createSpinner() {
        List<NamedColor> colors = Arrays.asList(
                new NamedColor("Red", Color.RED),
                new NamedColor("Yellow", Color.YELLOW),
                new NamedColor("Green", Color.GREEN),
                new NamedColor("Cyan", Color.CYAN),
                new NamedColor("Blue", Color.BLUE),
                new NamedColor("Magenta", Color.MAGENTA),
                new NamedColor("Black", Color.BLACK)
        );
        ArrayAdapter<NamedColor> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                colors
        );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        colorSpinner.setAdapter(dataAdapter);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NamedColor selectedColor = (NamedColor) colorSpinner.getSelectedItem();
                drawingView.setColor(selectedColor.color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.lineButton:
                drawingView.setMode(ShapeType.LINE);
                break;
            case R.id.rectangleButton:
                drawingView.setMode(ShapeType.RECTANGLE);
                break;
            case R.id.circleButton:
                drawingView.setMode(ShapeType.CIRCLE);
                break;
            case R.id.dragButton:
                drawingView.setMode(ShapeType.NO);
                break;
            case R.id.cleanButton:
                drawingView.clean();
                break;
            case R.id.removeLastButton:
                drawingView.removeLast();
                break;
        }
    }

    private static class NamedColor{
        String name;
        int color;

        public NamedColor(String name, int color) {
            this.name = name;
            this.color = color;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
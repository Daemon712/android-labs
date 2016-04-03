package ru.foobarbaz.converter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import ru.foobarbaz.R;

import java.text.DecimalFormat;
import java.util.Locale;
import static ru.foobarbaz.converter.UnitOfMeasure.*;

public class ConverterActivity extends Activity implements TextWatcher {
    private static final Locale[] LOCALES = {Locale.ENGLISH, new Locale("ru")};
    private static final DecimalFormat FORMATTER = new DecimalFormat("0.00");
    private UnitOfMeasure selectedMeasure = METER;
    private double input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter);

        initLangSpinner();
        initMeasureSpinner();
        initEditNumber();
    }

    private void initEditNumber() {
        EditText editNumber = (EditText) findViewById(R.id.editNumber);
        editNumber.addTextChangedListener(this);
    }

    private void update() {
        update(R.id.meter, METER);
        update(R.id.kilometer, KILOMETER);
        update(R.id.centimeter, CENTIMETER);
        update(R.id.foot, FOOT);
        update(R.id.mile, MILE);
        update(R.id.inch, INCH);
    }

    private void update(int viewId, UnitOfMeasure unit){
        TextView metersView = (TextView) findViewById(viewId);
        metersView.setText(FORMATTER.format(selectedMeasure.toUnit(input, unit)));
    }

    private void initLangSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.langSpinner);
        spinner.setPrompt(getString(R.string.lang_label));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lang_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                Locale locale = LOCALES[selectedItemPosition];
                if (locale.equals(Locale.getDefault())) return;
                Locale.setDefault(locale);
                getResources().getConfiguration().locale = locale;
                getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());
                recreate();
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void initMeasureSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.measureSpinner);
        spinner.setPrompt(getString(R.string.measure_label));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.measure_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                selectedMeasure = values()[selectedItemPosition];
                update();
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == null || s.length() == 0) return;
        input = Double.parseDouble(s.toString());
        update();
    }
}

package ru.foobarbaz.converter;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import ru.foobarbaz.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConverterActivity extends Activity implements TextWatcher {
    private static final Locale[] LOCALES = {Locale.ENGLISH, new Locale("ru","RU")};
    private UnitOfMeasure selectedMeasure;
    private Map<UnitOfMeasure, TextView> measureViews = new HashMap<>();
    private double input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter);

        initLangSpinner();
        initEditNumber();
        initMeasures();
    }

    private void initMeasures() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        for (UnitOfMeasure measure : UnitOfMeasure.values()){
            radioGroup.addView(createRadioButton(measure));
        }
    }

    private void initEditNumber() {
        EditText editNumber = (EditText) findViewById(R.id.editNumber);
        editNumber.addTextChangedListener(this);
    }

    private void update() {
        if (selectedMeasure == null) return;
        for (Map.Entry<UnitOfMeasure, TextView> item : measureViews.entrySet()){
            double value = selectedMeasure.toUnit(input, item.getKey());
            item.getValue().setText(generateText(item.getKey(), value));
        }
    }

    private String generateText(UnitOfMeasure unit, double value){
        String name = getResources().getString(unit.getNameId());
        return String.format(" %-10s%20.2f", name, value);
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
                Configuration config = getResources().getConfiguration();
                if (locale.equals(config.locale)) return;
                config.locale = locale;
                getResources().updateConfiguration(config, null);
                recreate();
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private RadioButton createRadioButton(UnitOfMeasure measure){
        RadioButton radioButton = new RadioButton(this);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMeasure = measure;
                ConverterActivity.this.update();
            }
        });

        radioButton.setTextSize(30);
        radioButton.setTypeface(Typeface.MONOSPACE);
        radioButton.setText(generateText(measure, 0f));
        measureViews.put(measure, radioButton);
        return radioButton;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        input = s == null || s.length() == 0 ? 0 : Double.parseDouble(s.toString());
        update();
    }
}

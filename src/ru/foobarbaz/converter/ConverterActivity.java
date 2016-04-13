package ru.foobarbaz.converter;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import ru.foobarbaz.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConverterActivity extends Activity implements TextWatcher {
    private static final Map<Locale,Integer> LOCALES;
    private UnitOfMeasure selectedMeasure;
    private Map<UnitOfMeasure, TextView> measureViews = new HashMap<>();
    private double input;

    static {
        Map<Locale, Integer> temp = new HashMap<>();
        temp.put(Locale.ENGLISH, R.string.eng);
        temp.put(new Locale("ru", "RU"), R.string.rus);
        LOCALES = Collections.unmodifiableMap(temp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter);

        initEditNumber();
        initMeasures();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        for (Map.Entry<Locale, Integer> item : LOCALES.entrySet()){

            MenuItem menuItem = menu.add(item.getValue());
            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Configuration config = getResources().getConfiguration();
                    if (item.getKey().equals(config.locale)) return true;
                    config.locale = item.getKey();
                    getResources().updateConfiguration(config, null);
                    recreate();
                    return true;
                }
            });
        }
        return true;
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
        return name + "\n" + value;
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

        radioButton.setTextSize(24);
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

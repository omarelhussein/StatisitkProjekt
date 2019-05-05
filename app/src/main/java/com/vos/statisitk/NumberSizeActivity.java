package com.vos.statisitk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NumberSizeActivity extends AppCompatActivity {

    private EditText editTextNumberSizeX;
    private EditText editTextNumberSizeY;
    private Spinner spinnerVariables;

    private String[] spinnerValues = {"Variables: X", "Variables: X, Y"};
    public static String spinnerValue = " ";

    public static final String EXTRA_ARRAY_SIZE_X = "extra_array_size_x";
    public static final String EXTRA_ARRAY_SIZE_Y = "extra_array_size_y";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_size);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        editTextNumberSizeX = findViewById(R.id.editTextNumberSizeX);
        editTextNumberSizeY = findViewById(R.id.editTextNumberSizeY);
        spinnerVariables = findViewById(R.id.spinnerVariablesChoice);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerValues);
        spinnerVariables.setAdapter(adapter);

        spinnerVariables.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue = (String) spinnerVariables.getSelectedItem();
                enableXComponentsAlone(spinnerVariables);
                enableXYComponents(spinnerVariables);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void confirmNumbers(View view) {

        int numberSizeX;
        int numberSizeY;

        if (spinnerVariables.getSelectedItem().toString().equals(spinnerValues[0])) {

            if (!(editTextNumberSizeX.length() < 1)) {

                numberSizeX = Integer.parseInt(editTextNumberSizeX.getText().toString());
                Intent intent = new Intent(this, CalculationActivity.class);
                intent.putExtra(EXTRA_ARRAY_SIZE_X, numberSizeX);
                startActivity(intent);

                editTextNumberSizeX.setText("");
                editTextNumberSizeX.clearFocus();

            } else if (editTextNumberSizeX.length() < 1 || Integer.parseInt(editTextNumberSizeX.getText().toString()) < 1) {

                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        }

        if (spinnerVariables.getSelectedItem().toString().equals(spinnerValues[1])) {

            if (editTextNumberSizeX.length() < 1 || editTextNumberSizeY.length() < 1 ||
                    Integer.parseInt(editTextNumberSizeX.getText().toString()) < 1 || Integer.parseInt(editTextNumberSizeY.getText().toString()) < 1) {

                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();

            } else {
                numberSizeX = Integer.parseInt(editTextNumberSizeX.getText().toString());
                numberSizeY = Integer.parseInt(editTextNumberSizeY.getText().toString());

                Intent intent = new Intent(this, CalculationActivity.class);
                intent.putExtra(EXTRA_ARRAY_SIZE_X, numberSizeX);
                intent.putExtra(EXTRA_ARRAY_SIZE_Y, numberSizeY);
                startActivity(intent);

                editTextNumberSizeX.clearFocus();
                editTextNumberSizeX.setText("");
                editTextNumberSizeY.clearFocus();
                editTextNumberSizeY.setText("");
            }

        }
    }

    private void enableXComponentsAlone(Spinner spinner) {
        if (spinner.getSelectedItem().toString().equals(spinnerValues[0])) {
            editTextNumberSizeY.setEnabled(false);
            editTextNumberSizeY.setVisibility(View.INVISIBLE);
        }
    }

    private void enableXYComponents(Spinner spinner) {
        if (spinner.getSelectedItem().toString().equals(spinnerValues[1])) {
            editTextNumberSizeY.setEnabled(true);
            editTextNumberSizeY.setVisibility(View.VISIBLE);
        }
    }
}

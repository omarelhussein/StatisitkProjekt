package com.vos.statisitk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CalculationActivity extends AppCompatActivity {

    private EditText editTextNumbersInputX, editTextNumbersInputY;
    private TextView textViewNumbersCountX, textViewNumbersSumX, textViewNumbersAverageX, textViewVarianceOutputX; //X components
    private TextView textViewNumbersCountY, textViewNumbersSumY, textViewNumbersAverageY, textViewVarianceOutputY; //Y components
    private Button btnSaveNumbersInputX;
    private Button btnSaveNumbersInputY;
    private Button btnMoveToTable;

    public static final String EXTRA_ARRAY_X_NUMBERS = "array_X_numbers";
    public static final String EXTRA_ARRAY_Y_NUMBERS = "array_Y_numbers";
    public static final String EXTRA_AVERAGE_X = "average_X";
    public static final String EXTRA_AVERAGE_Y = "average_Y";

    private ConstraintSet constraintSet;
    private ConstraintLayout constraintLayout;

    public static final String SHARED_PREFS = "sharedPrefs";

    public static final String ANSWER_SUM_X = "answerSum";
    public static final String ANSWER_AVERAGE_X = "answerAverage";
    public static final String ANSWER_VARIANCE_X = "answerVariance";

    public static final String ANSWER_SUM_Y = "answerSum";
    public static final String ANSWER_AVERAGE_Y = "answerAverage";
    public static final String ANSWER_VARIANCE_Y = "answerVariance";

    private int counterX;
    private int counterY;
    private int arraySizeX;
    private int arraySizeY;
    private double sumX;
    private double sumY;
    private double averageX;
    private double averageY;
    private String saveDataSumX;
    private String saveDataSumY;
    private String saveDataAverageX;
    private String saveDataAverageY;
    private String saveDataVarianceX;
    private String saveDataVarianceY;
    private double[] beobachtungenListeX;
    private double[] beobachtungenListeY;
    private double varianzX;
    private double varianzY;


    /**
    This Method creates the Activity and it also creates the layout, Methods etc... Everything
    included in this method will be created on Start
    NOTE: if there are IF-STATEMENTS the creation for the CHOSEN Methods will be delayed until the
    IF is passed from!
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getVariablesFromOtherActivities();

        constraintSet = new ConstraintSet();
        constraintLayout = findViewById(R.id.constrainLayoutCalculationActivity);


        xComponentsDeclaration();
        yComponentsDeclaration();

        componentsOfY_Enabled(arraySizeY);
        componentsOfY_Disabled(arraySizeY);

        beobachtungenListeX = new double[arraySizeX];
        beobachtungenListeY = new double[arraySizeY];
        btnMoveToTable = findViewById(R.id.btnMoveToTable);

        { //X_VariablesDeclaration
            counterX = 0; //Counts how many numbers left to complete the list
            sumX = 0;     //Sums all the X numbers of the list
            averageX = 0; //Calculates the average of the sum of the list
        }

        { //Y_VariablesDeclarations
            counterY = 0; //Counts how many numbers left to complete the list
            sumY = 0;     //Sums all the X numbers of the list
            averageY = 0; //Calculates the average of the sum of the list
        }

        //loadData();
        //changeView();
    }

    /**
    gets the variables values from other activities
     */
    private void getVariablesFromOtherActivities() {
        Intent intent = getIntent();
        arraySizeX = intent.getIntExtra(NumberSizeActivity.EXTRA_ARRAY_SIZE_X, 0);
        arraySizeY = intent.getIntExtra(NumberSizeActivity.EXTRA_ARRAY_SIZE_Y, 0);
    }

    //Declares all the non Mathematical components of X
    private void xComponentsDeclaration() {
        editTextNumbersInputX = findViewById(R.id.editTextNumbersXInput);
        textViewNumbersCountX = findViewById(R.id.textViewNumbersCounterX);
        textViewNumbersSumX = findViewById(R.id.textViewSumX);
        textViewNumbersAverageX = findViewById(R.id.textViewAverageNumbersX);
        textViewVarianceOutputX = findViewById(R.id.textViewVarianzOutputX);
        btnSaveNumbersInputX = findViewById(R.id.btnSaveNumbersXInput);

    }

    //Declares all the non Mathematical components of Y
    private void yComponentsDeclaration() {
        editTextNumbersInputY = findViewById(R.id.editTextNumbersYInput);
        textViewNumbersCountY = findViewById(R.id.textViewNumbersCounterY);
        textViewNumbersSumY = findViewById(R.id.textViewSumY);
        textViewNumbersAverageY = findViewById(R.id.textViewAverageNumbersY);
        textViewVarianceOutputY = findViewById(R.id.textViewVarianzOutputY);
        btnSaveNumbersInputY = findViewById(R.id.btnSaveNumbersYInput);

    }

    /**
    Edits and updates all the textViews on specific positions after reaching the counter on the
    max counting step of the list!
     */
    public void editTextViews_XComponents(View view) {
        //needs improvements
        if (editTextNumbersInputX.length() > 0 && !(editTextNumbersInputX.getText().equals("."))) {
            beobachtungenListeX[counterX] = Double.parseDouble(editTextNumbersInputX.getText().toString());
            counterX++;
            textViewNumbersCountX.setText("Number " + counterX + "/" + arraySizeX);
            sumX = calculateSum(Double.parseDouble(editTextNumbersInputX.getText().toString()), sumX);
            textViewNumbersSumX.setText("Sum = " + sumX);
            editTextNumbersInputX.setText("");
        } else {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
        }


        //needs improvements
        if (counterX == arraySizeX) {
            averageX = calculateAverage(sumX, arraySizeX);

            textViewNumbersAverageX.setText("The Average of the " + arraySizeX + " numbers = \n" + averageX);
            editTextNumbersInputX.setEnabled(false);
            btnSaveNumbersInputX.setEnabled(false);

            varianzX = calculateVariance(beobachtungenListeX, averageX);
            textViewVarianceOutputX.setText("The variance is = " + varianzX);
        }
    }

    /**
     Edits and updates all the textViews on specific positions after reaching the counter on the
     max counting step of the list!
     */
    public void editTextViews_YComponents(View view) {
        //needs Improvements
        if ((editTextNumbersInputY.length() > 0) && !(editTextNumbersInputY.getText().equals("."))) {
            beobachtungenListeY[counterY] = Double.parseDouble(editTextNumbersInputY.getText().toString());
            counterY++;
            textViewNumbersCountY.setText("Number " + counterY + "/" + arraySizeY);
            sumY = calculateSum(Double.parseDouble(editTextNumbersInputY.getText().toString()), sumY);
            textViewNumbersSumY.setText("Sum = " + sumY);
            editTextNumbersInputY.setText("");
        } else {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
        }

        //needs improvements
        if (counterY == arraySizeY) {
            averageY = calculateAverage(sumY, arraySizeY);

            textViewNumbersAverageY.setText("The Average of the " + arraySizeY + " numbers = \n" + averageY);
            editTextNumbersInputY.setEnabled(false);
            btnSaveNumbersInputY.setEnabled(false);

            varianzY = calculateVariance(beobachtungenListeY, averageY);
            textViewVarianceOutputY.setText("The variance is = " + varianzY);
        }
    }

    /**
    This Method Calculates the sum and updates the sum on every Calculation
     @param number needs the number that you want to add
     @param variableType needs a variable to store in it the addition or the sum
     */
    private double calculateSum(double number, double variableType) {
        variableType += number;
        return variableType;
    }

    /**
    This Method calculates the average of a given list
     @param finalSum needs the sum of the entire list to be able to calculate the average
     @param arraySize needs the arraySize to divide it with the finalSum
     */
    private double calculateAverage(double finalSum, double arraySize) {
        double average = finalSum / arraySize;
        return average;
    }

    /**
    This Method creates the calculation from the variance, but without the numberList and the
    average Method this Method can't calculate! :D
     @param numbersList needs a list of numbers to calculate the variance
     with the help of
     @param average
     */
    private double calculateVariance(@NotNull double[] numbersList, double average) {
        double variance = 0;
        for (int i = 0; i < numbersList.length; i++) {
            variance += Math.pow(numbersList[i] - average, 2);
        }
        variance /= numbersList.length;
        return variance;
    }

    /**
    This Method Saves the data into Strings so we can call them later to load them
    NOTE: This will just happen if the linked Button or CheckBox or Switch is activated or
    Clicked on!
     @param view needs it as an accessible data from the layout
     */
    public void saveData(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ANSWER_SUM_X, "Sum = " + sumX);
        editor.putString(ANSWER_AVERAGE_X, "The Average of the " + arraySizeX + " numbers = \n" + averageX);
        editor.putString(ANSWER_VARIANCE_X, "The variance is = " + varianzX);

        editor.putString(ANSWER_SUM_Y, "Sum = " + sumY);
        editor.putString(ANSWER_AVERAGE_Y, "The Average of the " + arraySizeY + " numbers = \n" + averageY);
        editor.putString(ANSWER_VARIANCE_Y, "The variance is = " + varianzY);
        editor.apply();
    }

    /**
    This method loads the saved Data so we just need to set Them into some views (TextViews etc...)
     */
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        saveDataSumX = sharedPreferences.getString(ANSWER_SUM_X, "");
        saveDataAverageX = sharedPreferences.getString(ANSWER_AVERAGE_X, "");
        saveDataVarianceX = sharedPreferences.getString(ANSWER_VARIANCE_X, "");

        saveDataSumY = sharedPreferences.getString(ANSWER_SUM_Y, "");
        saveDataAverageY = sharedPreferences.getString(ANSWER_AVERAGE_Y, "");
        saveDataVarianceY = sharedPreferences.getString(ANSWER_VARIANCE_Y, "");
    }


    /**
    This Method changes the views into the loaded data so you can see now the saved data
     */
    public void changeView() {
        textViewNumbersSumX.setText(saveDataSumX);
        textViewNumbersAverageX.setText(saveDataAverageX);
        textViewVarianceOutputX.setText(saveDataVarianceX);

        textViewNumbersSumY.setText(saveDataSumY);
        textViewNumbersAverageY.setText(saveDataAverageY);
        textViewVarianceOutputY.setText(saveDataVarianceY);
    }

    /**
    This Method makes a fading animation after clicking on the Back Button in the Phone
    NOTE: It just changes the animation of the Returning Into Activity!
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
    This Method enables all of the Y components if the Y List Size was not empty
    or == to zero, otherwise another method will be called to deactivate the components!
     */
    private void componentsOfY_Enabled(int arraySizeY) {
        if (arraySizeY > 0) {
            editTextNumbersInputY.setVisibility(View.VISIBLE);
            btnSaveNumbersInputY.setVisibility(View.VISIBLE);
            textViewNumbersSumY.setVisibility(View.VISIBLE);
            textViewVarianceOutputY.setVisibility(View.VISIBLE);
            textViewNumbersAverageY.setVisibility(View.VISIBLE);
            textViewNumbersCountY.setVisibility(View.VISIBLE);
        }
    }

    /**
    This Method disables the Y components if...
    ... the Y List is empty and makes those components invisible
     needs:
     @param arraySizeY to check if the array contains minimum a value or not
     */
    private void componentsOfY_Disabled(int arraySizeY) {
        if (arraySizeY < 1) {
            editTextNumbersInputY.setVisibility(View.INVISIBLE);
            editTextNumbersInputY.setEnabled(false);

            btnSaveNumbersInputY.setVisibility(View.INVISIBLE);
            btnSaveNumbersInputY.setEnabled(false);

            textViewNumbersSumY.setVisibility(View.INVISIBLE);
            textViewNumbersSumY.setEnabled(false);

            textViewVarianceOutputY.setVisibility(View.INVISIBLE);
            textViewVarianceOutputY.setEnabled(false);

            textViewNumbersAverageY.setVisibility(View.INVISIBLE);
            textViewNumbersAverageY.setEnabled(false);

            textViewNumbersCountY.setVisibility(View.INVISIBLE);
            textViewNumbersCountY.setEnabled(false);

            fillEmptySpaceAfterDisablingComponents();
        }
    }

    /**
    This Method fills the empty space between components and reorder them
    It will be called in the disablingComponentsMethod so after disabling the components
    the layout will be passed automatically...
     */
    private void fillEmptySpaceAfterDisablingComponents() {
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.divider, ConstraintSet.TOP, R.id.textViewSumX, ConstraintSet.BOTTOM, 64);
        constraintSet.connect(R.id.checkBoxSaveData, ConstraintSet.TOP, R.id.textViewAverageNumbersX, ConstraintSet.BOTTOM, 32);
        constraintSet.connect(R.id.textViewVarianzOutputX, ConstraintSet.TOP, R.id.checkBoxSaveData, constraintSet.BOTTOM, 48);
        constraintSet.applyTo(constraintLayout);
    }

    /**
     * opens the Table in the Table Activity and carries with it The arrays and the average numbers.
     * This will only work if both X and Y arrays are available
     * @param view already explained @Line 243
     */
    public void openTableCalculation (View view) {
        Intent intent = new Intent(this, TableCalculationActivity.class);
        intent.putExtra(EXTRA_ARRAY_X_NUMBERS, beobachtungenListeX);
        intent.putExtra(EXTRA_ARRAY_Y_NUMBERS, beobachtungenListeY);
        intent.putExtra(EXTRA_AVERAGE_X, averageX);
        intent.putExtra(EXTRA_AVERAGE_Y, averageY);
        startActivity(intent);
    }
}
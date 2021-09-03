package com.vos.statisitk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TableCalculationActivity extends AppCompatActivity {

    private double[] array_X;
    private double[] array_Y;
    private double averageX, averageY;

    TextView textViewXi, textViewYi, textViewXiMinusX_avg, textViewYiMinusY_avg, txtViewXiMinX_avgTimesYiMinY_avg
            , textViewXiMinusX_avgPow, textViewYiMinusY_avgPow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_calculation);

        declarationOfTextViews();

        getValuesFromOtherActivities();

        fillTableWithMethods();

    }

    /**
     * Declares all the TextViews of the Table
     */
    private void declarationOfTextViews() {
        textViewXi = findViewById(R.id.textViewXi);
        textViewYi = findViewById(R.id.textViewYi);
        textViewXiMinusX_avg = findViewById(R.id.textViewXMinusXiavg);
        textViewYiMinusY_avg = findViewById(R.id.textViewYiMinusYavg);
        txtViewXiMinX_avgTimesYiMinY_avg = findViewById(R.id.textViewXiMinXavgTimesYiMinYavg);
        textViewXiMinusX_avgPow = findViewById(R.id.textViewXiMinusXavgPow);
        textViewYiMinusY_avgPow = findViewById(R.id.textViewYiMinusYavgPow);
    }

    /**
     * Collects all the methods that fills the table in One method
     */
    private void fillTableWithMethods() {
        fillXNumbers();
        fillXMinusXAverage();
        fillYMinusYAverage();
        fill_XiMinusXAvgPow();
        if(array_Y.length < 1)
            return;
        fillYNumbers();
        fill_XiMinusXAvg_times_YiMinusYAvg();
        fill_YiMinusYAvgPow();
    }

    /**
     * Collects the necessary values from the other activities using the intent
     */
    private void getValuesFromOtherActivities() {
        Intent intent = getIntent();
        array_X = intent.getDoubleArrayExtra(CalculationActivity.EXTRA_ARRAY_X_NUMBERS);
        array_Y = intent.getDoubleArrayExtra(CalculationActivity.EXTRA_ARRAY_Y_NUMBERS);
        averageX = intent.getDoubleExtra(CalculationActivity.EXTRA_AVERAGE_X, 0);
        averageY = intent.getDoubleExtra(CalculationActivity.EXTRA_AVERAGE_Y, 0);
    }

    /**
     * Fills the X Column of the table
     */
    private void fillXNumbers() {
        double sum = 0;
        for (int i = 0; i < array_X.length; i++) {
            sum += array_X[i];
            textViewXi.setText(textViewXi.getText() + "\n" + array_X[i]);
        }
        textViewXi.setText(textViewXi.getText() + "\n\n" + sum);
    }

    /**
     * Fills the Y Column of the table
     */
    private void fillYNumbers() {
        double sum = 0;
        for (int i = 0; i < array_Y.length; i++) {
            sum += array_Y[i];
            textViewYi.setText(textViewYi.getText() + "\n" + array_Y[i]);
        }
        textViewYi.setText(textViewYi.getText() + "\n\n" + sum);
    }

    /**
     * Fills the (X - X_average) Column of the table
     */
    private void fillXMinusXAverage() {
        double shortResult, sum = 0;
        for (int i = 0; i < array_X.length; i++) {
            shortResult = array_X[i] - averageX;
            shortResult = Math.round(shortResult * 1000.0) / 1000.0;
            sum += shortResult;
            textViewXiMinusX_avg.setText(textViewXiMinusX_avg.getText() + "\n" + shortResult);
        }
        sum = Math.round(sum * 1000.0) / 1000.0;
        textViewXiMinusX_avg.setText(textViewXiMinusX_avg.getText() + "\n\n" + sum);
    }

    /**
     * Fills the (Y - Y_average) Column of the table
     */
    private void fillYMinusYAverage() {
        double shortResult, sum = 0;
        for (int i = 0; i < array_Y.length; i++) {
            shortResult = array_Y[i] - averageY;
            shortResult = Math.round(shortResult * 1000.0) / 1000.0;
            sum += shortResult;
            textViewYiMinusY_avg.setText(textViewYiMinusY_avg.getText() + "\n" + shortResult);
        }
        sum = Math.round(sum * 1000.0) / 1000.0;
        textViewYiMinusY_avg.setText(textViewYiMinusY_avg.getText() + "\n\n" + sum);
    }

    /**
     * Fills the (X - X_average)*(Y - Y_average) Column of the table
     */
    private void fill_XiMinusXAvg_times_YiMinusYAvg() {
        double[] shortResultX = new double[array_X.length];
        double[] shortResultY = new double[array_Y.length];
        double shortResult, sum = 0;
        for (int i = 0; i < array_X.length; i++) {
            shortResultX[i] = array_X[i] - averageX;
        }
        for (int i = 0; i < array_Y.length; i++) {
            shortResultY[i] = array_Y[i] - averageY;
        }
        for (int i = 0; i < array_X.length; i++) {
            shortResult = shortResultX[i] * shortResultY[i];
            shortResult = Math.round(shortResult * 1000.0) / 1000.0;
            sum += shortResult;
            txtViewXiMinX_avgTimesYiMinY_avg.setText(txtViewXiMinX_avgTimesYiMinY_avg.getText() + "\n" + shortResult);
        }
        sum = Math.round(sum * 1000.0) / 1000.0;
        txtViewXiMinX_avgTimesYiMinY_avg.setText(txtViewXiMinX_avgTimesYiMinY_avg.getText() + "\n\n" + sum);
    }

    /**
     * Fills the (X - X_average)^2 Column of the table
     */
    private void fill_XiMinusXAvgPow () {
        double shortResult, sum = 0;
        for (int i = 0; i < array_X.length; i++) {
            shortResult = Math.pow(array_X[i] - averageX, 2);
            shortResult = Math.round(shortResult * 1000.0) / 1000.0;
            sum += shortResult;
            textViewXiMinusX_avgPow.setText(textViewXiMinusX_avgPow.getText() + "\n" + shortResult);
        }
        sum = Math.round(sum * 1000.0) / 1000.0;
        textViewXiMinusX_avgPow.setText(textViewXiMinusX_avgPow.getText() + "\n\n" + sum);
    }

    /**
     * Fills the (Y - Y_average)^2 Column of the table
     */
    private void fill_YiMinusYAvgPow() {
        double shortResult, sum = 0;
        for (int i = 0; i < array_Y.length; i++) {
            shortResult = Math.pow(array_Y[i] - averageY, 2);
            shortResult = Math.round(shortResult * 1000.0) / 1000.0;
            sum += shortResult;
            textViewYiMinusY_avgPow.setText(textViewYiMinusY_avgPow.getText() + "\n" + shortResult);
        }
        sum = Math.round(sum * 1000.0) / 1000.0;
        textViewYiMinusY_avgPow.setText(textViewYiMinusY_avgPow.getText() + "\n\n" + sum);
    }

}
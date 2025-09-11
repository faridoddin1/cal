package com.gemini.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;

    private String operand1 = "";
    private String operand2 = "";
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
    }

    public void onDigit(View view) {
        Button button = (Button) view;
        if (operator.isEmpty()) {
            operand1 += button.getText().toString();
            resultTextView.setText(operand1);
        } else {
            operand2 += button.getText().toString();
            resultTextView.setText(operand2);
        }
    }

    public void onOperator(View view) {
        Button button = (Button) view;
        if (!operand1.isEmpty()) {
            operator = button.getText().toString();
        }
    }

    public void onEqual(View view) {
        if (!operand1.isEmpty() && !operand2.isEmpty() && !operator.isEmpty()) {
            double result = 0;
            double num1 = Double.parseDouble(operand1);
            double num2 = Double.parseDouble(operand2);

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        resultTextView.setText("Error");
                        return;
                    }
                    break;
            }
            operand1 = String.valueOf(result);
            resultTextView.setText(operand1);
            operand2 = "";
            operator = "";
        }
    }

    public void onClear(View view) {
        operand1 = "";
        operand2 = "";
        operator = "";
        resultTextView.setText("0");
    }

    public void onBackspace(View view) {
        if (operator.isEmpty()) {
            if (!operand1.isEmpty()) {
                operand1 = operand1.substring(0, operand1.length() - 1);
                resultTextView.setText(operand1);
            }
        } else {
            if (!operand2.isEmpty()) {
                operand2 = operand2.substring(0, operand2.length() - 1);
                resultTextView.setText(operand2);
            }
        }
    }

    public void onDecimalPoint(View view) {
        if (operator.isEmpty()) {
            if (!operand1.contains(".")) {
                operand1 += ".";
                resultTextView.setText(operand1);
            }
        } else {
            if (!operand2.contains(".")) {
                operand2 += ".";
                resultTextView.setText(operand2);
            }
        }
    }
}

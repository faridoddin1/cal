package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var currentNumber: String = ""
    private var currentOperator: String = ""
    private var result: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
    }

    fun onNumberClick(view: View) {
        val button = view as Button
        currentNumber += button.text.toString()
        updateResultTextView()
    }

    fun onOperatorClick(view: View) {
        val button = view as Button
        if (currentNumber.isNotEmpty()) {
            performOperation()
            currentOperator = button.text.toString()
        } else {
            currentOperator = button.text.toString()
        }
    }

    fun onEqualsClick(view: View) {
        performOperation()
        currentOperator = ""
    }

    fun onAcClick(view: View) {
        currentNumber = ""
        currentOperator = ""
        result = 0.0
        updateResultTextView()
    }

    fun onPlusMinusClick(view: View) {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDouble()
            currentNumber = (-number).toString()
            updateResultTextView()
        }
    }

    fun onPercentageClick(view: View) {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDouble()
            currentNumber = (number / 100).toString()
            updateResultTextView()
        }
    }

    private fun performOperation() {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDouble()
            when (currentOperator) {
                "+" -> result += number
                "-" -> result -= number
                "×" -> result *= number
                "÷" -> result /= number
                else -> result = number
            }
            currentNumber = ""
            updateResultTextView(result.toString())
        }
    }

    private fun updateResultTextView(text: String? = null) {
        resultTextView.text = text ?: currentNumber
    }
}

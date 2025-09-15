package com.example.geminicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.geminicalculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentInput = ""
    private var currentOperator = ""
    private var operand1: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        val numberButtons = listOf(
            binding.button0, binding.button1, binding.button2, binding.button3, binding.button4,
            binding.button5, binding.button6, binding.button7, binding.button8, binding.button9,
            binding.buttonDot
        )

        numberButtons.forEach { button ->
            button.setOnClickListener { onNumberClicked(it) }
        }

        val operatorButtons = listOf(
            binding.buttonAdd, binding.buttonSubtract, binding.buttonMultiply, binding.buttonDivide
        )

        operatorButtons.forEach { button ->
            button.setOnClickListener { onOperatorClicked(it) }
        }

        binding.buttonClear.setOnClickListener { onClearClicked() }
        binding.buttonEquals.setOnClickListener { onEqualsClicked() }
        binding.buttonNegate.setOnClickListener { onNegateClicked() }
        binding.buttonPercent.setOnClickListener { onPercentClicked() }
    }

    private fun onNumberClicked(view: View) {
        val button = view as Button
        val number = button.text.toString()

        if (number == "." && currentInput.contains(".")) {
            return // Prevent multiple dots
        }

        currentInput += number
        updateResultView()
    }

    private fun onOperatorClicked(view: View) {
        val button = view as Button
        if (currentInput.isNotEmpty()) {
            // If there's already an operand1, calculate the result first
            if (operand1 != null) {
                onEqualsClicked()
            }
            operand1 = currentInput.toDoubleOrNull() ?: 0.0
            currentInput = ""
        }
        currentOperator = button.text.toString()
        updateHistoryView()
    }

    private fun onEqualsClicked() {
        if (currentInput.isNotEmpty() && operand1 != null) {
            val operand2 = currentInput.toDouble()
            val result = performOperation(operand1!!, operand2, currentOperator)

            currentInput = formatResult(result)
            operand1 = null
            currentOperator = ""
            updateResultView()
            updateHistoryView(clear = true)
        }
    }

    private fun onClearClicked() {
        currentInput = ""
        currentOperator = ""
        operand1 = null
        updateResultView(clear = true)
        updateHistoryView(clear = true)
    }

    private fun onNegateClicked() {
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            updateResultView()
        }
    }

    private fun onPercentClicked() {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDouble() / 100
            currentInput = formatResult(value)
            updateResultView()
        }
    }

    private fun performOperation(op1: Double, op2: Double, operator: String): Double {
        return when (operator) {
            "+" -> op1 + op2
            "-" -> op1 - op2
            "×" -> op1 * op2
            "÷" -> {
                if (op2 == 0.0) {
                    Double.NaN // Represent error as Not a Number
                } else {
                    op1 / op2
                }
            }
            else -> 0.0
        }
    }

    private fun updateResultView(clear: Boolean = false) {
        val textToShow = if (clear || currentInput.isEmpty()) "0" else currentInput
        binding.resultTextView.text = textToShow
    }

    private fun updateHistoryView(clear: Boolean = false) {
        if (clear) {
            binding.historyTextView.text = ""
        } else {
            val operand1Text = operand1?.let { formatResult(it) } ?: ""
            binding.historyTextView.text = "$operand1Text $currentOperator"
        }
    }

    private fun formatResult(result: Double): String {
        val formatter = DecimalFormat("0.##########")
        return formatter.format(result)
    }
}
package com.example.geminicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.geminicalculator.databinding.ActivityMainBinding

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
            operand1 = currentInput.toDouble()
            currentInput = ""
        }
        currentOperator = button.text.toString()
    }

    private fun onEqualsClicked() {
        if (currentInput.isNotEmpty() && operand1 != null) {
            val operand2 = currentInput.toDouble()
            val result = performOperation(operand1!!, operand2, currentOperator)

            currentInput = result.toString()
            operand1 = null
            currentOperator = ""
            updateResultView()
        }
    }

    private fun onClearClicked() {
        currentInput = ""
        currentOperator = ""
        operand1 = null
        updateResultView(true)
    }

    private fun performOperation(op1: Double, op2: Double, operator: String): Double {
        return when (operator) {
            "+" -> op1 + op2
            "-" -> op1 - op2
            "*" -> op1 * op2
            "/" -> {
                if (op2 == 0.0) {
                    // Handle division by zero, maybe show an error
                    Double.NaN // Not a Number
                } else {
                    op1 / op2
                }
            }
            else -> 0.0
        }
    }

    private fun updateResultView(clear: Boolean = false) {
        if (clear) {
            binding.resultTextView.text = "0"
        } else {
            binding.resultTextView.text = currentInput
        }
    }
}

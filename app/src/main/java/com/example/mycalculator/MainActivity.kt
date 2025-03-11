package com.example.mycalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstValue = ""
    private var resetNext = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.tvResult)

        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                appendNumber((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnDot).setOnClickListener { appendNumber(".") }
        findViewById<Button>(R.id.btnPlus).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { setOperator("/") }

        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculate() }
        findViewById<Button>(R.id.btnC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.btnBS).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { toggleSign() }
    }

    private fun appendNumber(number: String) {
        if (resetNext) {
            currentInput = ""
            resetNext = false
        }
        if (number == "." && currentInput.contains(".")) return
        currentInput += number
        resultText.text = currentInput
    }

    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            firstValue = currentInput
            operator = op
            currentInput = ""
        }
    }

    private fun calculate() {
        if (firstValue.isEmpty() || currentInput.isEmpty()) return
        val result = when (operator) {
            "+" -> firstValue.toDouble() + currentInput.toDouble()
            "-" -> firstValue.toDouble() - currentInput.toDouble()
            "*" -> firstValue.toDouble() * currentInput.toDouble()
            "/" -> if (currentInput.toDouble() != 0.0)
                firstValue.toDouble() / currentInput.toDouble()
            else {
                resultText.text = "Error"
                return
            }
            else -> return
        }
        resultText.text = result.toString()
        currentInput = result.toString()
        operator = ""
        firstValue = ""
        resetNext = true
    }

    private fun clearAll() {
        currentInput = ""
        operator = ""
        firstValue = ""
        resultText.text = "0"
    }

    private fun clearEntry() {
        currentInput = ""
        resultText.text = "0"
    }

    private fun backspace() {
        currentInput = currentInput.dropLast(1)
        resultText.text = if (currentInput.isEmpty()) "0" else currentInput
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.drop(1)
            } else {
                "-$currentInput"
            }
            resultText.text = currentInput
        }
    }
}

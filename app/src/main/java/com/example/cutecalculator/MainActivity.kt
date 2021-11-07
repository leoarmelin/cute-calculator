package com.example.cutecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
  private var tvInput: TextView? = null
  private var isPreviousCharNumeric: Boolean = false
  private var hasDot: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    tvInput = findViewById(R.id.tvInput)
    val btnZero: Button = findViewById(R.id.btnZero)
    val btnOne: Button = findViewById(R.id.btnOne)
    val btnTwo: Button = findViewById(R.id.btnTwo)
    val btnThree: Button = findViewById(R.id.btnThree)
    val btnFour: Button = findViewById(R.id.btnFour)
    val btnFive: Button = findViewById(R.id.btnFive)
    val btnSix: Button = findViewById(R.id.btnSix)
    val btnSeven: Button = findViewById(R.id.btnSeven)
    val btnEight: Button = findViewById(R.id.btnEight)
    val btnNine: Button = findViewById(R.id.btnNine)
    val btnDivide: Button = findViewById(R.id.btnDivide)
    val btnMultiply: Button = findViewById(R.id.btnMultiply)
    val btnSubtract: Button = findViewById(R.id.btnSubtract)
    val btnSum: Button = findViewById(R.id.btnSum)
    val btnClr: Button = findViewById(R.id.btnClr)
    val btnDot: Button = findViewById(R.id.btnDot)
    val btnEqual: Button = findViewById(R.id.btnEqual)

    btnZero.setOnClickListener { view -> onDigit(view) }
    btnOne.setOnClickListener { view -> onDigit(view) }
    btnTwo.setOnClickListener { view -> onDigit(view) }
    btnThree.setOnClickListener { view -> onDigit(view) }
    btnFour.setOnClickListener { view -> onDigit(view) }
    btnFive.setOnClickListener { view -> onDigit(view) }
    btnSix.setOnClickListener { view -> onDigit(view) }
    btnSeven.setOnClickListener { view -> onDigit(view) }
    btnEight.setOnClickListener { view -> onDigit(view) }
    btnNine.setOnClickListener { view -> onDigit(view) }
    btnDivide.setOnClickListener { view -> onOperator(view) }
    btnMultiply.setOnClickListener { view -> onOperator(view) }
    btnSubtract.setOnClickListener { view -> onOperator(view) }
    btnSum.setOnClickListener { view -> onOperator(view) }
    btnClr.setOnClickListener { onClear() }
    btnDot.setOnClickListener { onDecimalPoint() }
    btnEqual.setOnClickListener { view -> onEqual(view) }
  }

  private fun onDigit(view: View) {
    tvInput?.append((view as Button).text)
    isPreviousCharNumeric = true
  }

  private fun onClear() {
    tvInput?.text = ""
    isPreviousCharNumeric = false
    hasDot = false
  }

  private fun onDecimalPoint() {
    if (isPreviousCharNumeric && !hasDot) {
      tvInput?.append(".")
      isPreviousCharNumeric = false
      hasDot = true
    }
  }

  private fun onOperator(view: View) {
    if ((isPreviousCharNumeric) && !isOperatorAdded(tvInput?.text.toString())) {
      tvInput?.append((view as Button).text)
      isPreviousCharNumeric = false
    }
  }

  private fun onEqual(view: View) {
    if (isPreviousCharNumeric) {
      var tvValue = tvInput?.text.toString()
      var prefix = ""

      try {
        if (tvValue.startsWith("-")) {
          prefix = "-"
          tvValue = tvValue.substring(1)
        }

        if (tvValue.contains("-")) {
          val splitValue = tvValue.split("-")

          var firstExpression = splitValue[0]
          var secondExpression = splitValue[1]

          if (prefix.isNotEmpty()) {
            firstExpression = prefix + firstExpression
          }

          tvInput?.text = (firstExpression.toDouble() - secondExpression.toDouble()).toString()
        }

        when {
            tvValue.contains("-") -> {
              val splitValue = tvValue.split("-")

              var firstExpression = splitValue[0]
              val secondExpression = splitValue[1]

              if (prefix.isNotEmpty()) {
                firstExpression = prefix + firstExpression
              }

              tvInput?.text = removeZeroAftorDot(
                (firstExpression.toDouble() - secondExpression.toDouble()).toString()
              )
            }
            tvValue.contains("+") -> {
              val splitValue = tvValue.split("+")

              var firstExpression = splitValue[0]
              val secondExpression = splitValue[1]

              if (prefix.isNotEmpty()) {
                firstExpression = prefix + firstExpression
              }

              tvInput?.text = removeZeroAftorDot(
                (firstExpression.toDouble() + secondExpression.toDouble()).toString()
              )
            }
          tvValue.contains("*") -> {
            val splitValue = tvValue.split("*")

            var firstExpression = splitValue[0]
            val secondExpression = splitValue[1]

            if (prefix.isNotEmpty()) {
              firstExpression = prefix + firstExpression
            }

            tvInput?.text = removeZeroAftorDot(
              (firstExpression.toDouble() * secondExpression.toDouble()).toString()
            )
          }
          tvValue.contains("/") -> {
            val splitValue = tvValue.split("/")

            var firstExpression = splitValue[0]
            val secondExpression = splitValue[1]

            if (prefix.isNotEmpty()) {
              firstExpression = prefix + firstExpression
            }

            tvInput?.text = removeZeroAftorDot(
              (firstExpression.toDouble() / secondExpression.toDouble()).toString()
            )
          }
        }
      } catch (e: ArithmeticException) {
        e.printStackTrace()
      }
    }
  }

  private fun removeZeroAftorDot(value: String): String {
    return if(value.contains(".0")) value.substring(0, value.length - 2) else value
  }

  private fun isOperatorAdded(value: String): Boolean {
    return if (value.startsWith("-")) {
      false
    } else {
      value.contains("/")
          || value.contains("*")
          || value.contains("+")
          || value.contains("-")
    }
  }
}
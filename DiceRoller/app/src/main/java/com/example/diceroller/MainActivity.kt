package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var strNumber = StringBuilder()
    private lateinit var working: TextView
    private lateinit var numberButtons: Array<Button>
    private lateinit var operatorButtons: List<Button>
    private var isOperatorClicked: Boolean = false
    private var firstOperand: Int = 0
    private var operator: Operator = Operator.NONE
    private var temporaryOperand: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        componentsPlace()
    }
    private fun componentsPlace(){
        working = findViewById(R.id.working)
        numberButtons = arrayOf(binding.button0,binding.button1,binding.button2,binding.button3,binding.button4,binding.button5,binding.button6,binding.button7,binding.button8,binding.button9)
        operatorButtons = listOf(binding.buttonDiv,binding.buttonTimes,binding.buttonPlus,binding.buttonMinus)
        for (i in numberButtons){
            i.setOnClickListener { (numberButtonOnClick(i)) }
        }
        for (i in operatorButtons){
            i.setOnClickListener { (operatorButtonOnClick(i)) }
        }
        binding.Equals.setOnClickListener{buttonEqualsClick()}
        binding.buttonClear.setOnClickListener{buttonClearClick()}
        binding.buttonClearEntry.setOnClickListener{buttonClearEntryClick()}
        binding.buttonBackSpace.setOnClickListener{buttonBackSpaceClick()}
        binding.buttonSign.setOnClickListener{buttonSignClick()}
    }

    private fun buttonSignClick() {
        if (strNumber.isNotEmpty()) {
            val currentValue = strNumber.toString().toInt()
            val newValue = -currentValue
            strNumber.clear()
            strNumber.append(newValue.toString())
            working.text = strNumber.toString()
        }
    }

    private fun buttonBackSpaceClick() {
        if (strNumber.isNotEmpty()) {
            strNumber.deleteCharAt(strNumber.length - 1)
            if (strNumber.isEmpty()) {
                working.text = "0"
            } else {
                working.text = strNumber.toString()
            }
        }
    }

    private fun buttonClearEntryClick() {
        strNumber.clear()
        strNumber.append("0")
        working.text = strNumber.toString()
        temporaryOperand = ""
    }

    private fun buttonClearClick() {
        strNumber.clear()
        working.text = "0"
    }


    private fun buttonEqualsClick() {
        if (strNumber.isNotEmpty()) {
            val secondOperand = if (temporaryOperand.isEmpty()) strNumber.toString().toInt() else temporaryOperand.toInt()
            val result: Int = when (operator) {
                Operator.PLUS -> firstOperand + secondOperand
                Operator.MINUS -> firstOperand - secondOperand
                Operator.TIMES -> firstOperand * secondOperand
                Operator.DIVIDE -> {
                    if (secondOperand != 0) {
                        firstOperand / secondOperand
                    } else {
                        working.text = "ERROR"
                        return
                    }
                }
                else -> 0
            }
            strNumber.clear()
            strNumber.append(result.toString())
            working.text = strNumber.toString()
            isOperatorClicked = true
            temporaryOperand = result.toString()
        }
    }

    private fun numberButtonOnClick(btn:Button){
        if(isOperatorClicked){
            firstOperand = strNumber.toString().toInt()
            strNumber.clear()
            isOperatorClicked = false
        }
        strNumber.append(btn.text)
        working.text = strNumber.toString()
    }
    private fun operatorButtonOnClick(btn:Button){
        operator = when(btn.text){
            "+" -> Operator.PLUS
            "-" -> Operator.MINUS
            "✖" -> Operator.TIMES
            "/" -> Operator.DIVIDE
            else -> Operator.NONE
        }
        isOperatorClicked = true
    }
}
enum class Operator{DIVIDE,TIMES,PLUS,MINUS,NONE}
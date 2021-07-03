package com.uzair.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.uzair.utils.SharedPrefsSettings
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class Calculator : AppCompatActivity() {
    private var display = ""
    private var currentOperator = ""
    private val result = ""
    var sharedPref: SharedPreferences? = null
    private lateinit var editor: SharedPreferences.Editor
    var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        deletevar.setOnClickListener { deleteNumber() }
        inputBox.setText(display)

    }

    private fun deleteNumber() {
        if (inputBox.text.isNotEmpty()) inputBox.text.delete(getInput().length - 1, getInput().length)
    }

    private fun getInput(): String {

        return inputBox.text.toString()
        
    }


    fun onClickNumber(v: View) {
        val b = v as Button
        display += b.text
        appendToLast(display)
        display = ""
    }


    private fun appendToLast(str: String) {
        inputBox.text.append(str)
    }

    fun onClickOperator(v: View) {
        val b = v as Button
        display += b.text
        if (endsWithOperator()) {
            replace(display)
            currentOperator = b.text.toString()
            display = ""
        } else {
            appendToLast(display)
            currentOperator = b.text.toString()
            display = ""
        }
    }

    private fun endsWithOperator(): Boolean {
        return getInput().endsWith("+")
                || getInput().endsWith("-")
                || getInput().endsWith("/")
                || getInput().endsWith("x")
                || getInput().endsWith("\u00F7")  //Unicode of division

    }

    private fun replace(str: String) {
        inputBox!!.text.replace(getInput().length - 1, getInput().length, str)
    }

    fun onClearButton(v: View?) {
        inputBox.text.clear()
        resultBox.text = ""
    }

    fun equalResult(v: View?) {

        if (getInput().isNotEmpty() && !endsWithOperator()) {
            //Got the valid expression

            var input = getInput()
            if (input.contains("x")) {
                input = input.replace("x".toRegex(), "*")
            }
            if (input.contains("\u00F7")) { //division unicode
                input = input.replace("\u00F7".toRegex(), "/")
            }
            val expression = ExpressionBuilder(input).build()

            try {
                val result = expression.evaluate()
                resultBox.text = result.toString()
                operatorCount(input)
            } catch (e: ArithmeticException) {
                Toast.makeText(this, "Exception: Don't divide a number by zero", Toast.LENGTH_SHORT).show()
            }
        } else resultBox.text = ""
        println(result)
    }

    private fun operatorCount(expression: String) {

        editor = sharedPref!!.edit()
        if (!sharedPref!!.getBoolean(SharedPrefsSettings.IS_QUIZ_FIRST, false)) {
            editor.putBoolean(SharedPrefsSettings.IS_QUIZ_FIRST, true)
        }


     //Calling Logic of Single operator for both True/ False & Equation Questions
        when {
            expression.contains("+") -> {
                additionPrefLogic()
                additionPrefLogicEquation()
            }
            expression.contains("-") -> {
                subtractionPrefLogic()
                subtractionPrefLogicEquation()
            }
            expression.contains("*") -> {
                multiplyPrefLogic()
                multiplyPrefLogicEquation()
            }
            expression.contains("/") -> {
                dividePrefLogic()
                dividePrefLogicEquation()
            }
        }

        /*     Calling Logic of Double operator for both True/ False & Equation Questions    */
        if (expression.contains("+") && expression.contains("-")) {
            additionSubLogic()
            additionSubLogicEquation()
        } else if (expression.contains("+") && expression.contains("*")) {
            additionMulLogic()
            additionMulLogicEquation()
        } else if (expression.contains("+") && expression.contains("/")) {
            additionDivLogic()
            additionDivLogicEquation()
        } else if (expression.contains("-") && expression.contains("*")) {
            subMulLogic()
            subMulLogicEquation()
        } else if (expression.contains("-") && expression.contains("/")) {
            subDivLogic()
            subDivLogicEquation()
        } else if (expression.contains("*") && expression.contains("/")) {
            mulDivLogic()
            mulDivLogicEquation()
        }

        /*     Calling Logic of Triple operator for both True/ False & Equation Questions    */
        if (expression.contains("+") && expression.contains("-") && expression.contains("*")) {
            addSubMulLogic()
            addSubMulLogicEquation()
        } else if (expression.contains("+") && expression.contains("-") && expression.contains("/")) {
            addSubDivLogic()
            addSubDivLogicEquation()
        } else if (expression.contains("+") && expression.contains("*") && expression.contains("/")) {
            addMulDivLogic()
            addMulDivLogicEquation()
        } else if (expression.contains("-") && expression.contains("*") && expression.contains("/")) {
            subMulDivLogic()
            subMulDivLogicEquation()
        }


        /*     Calling Logic of All operator for both True/ False & Equation Questions    */
        if (expression.contains("+") && expression.contains("-") && expression.contains("*") && expression.contains("/")) {
            addSubMulDivLogic()
            addSubMulDivLogicEquation()
        }
        editor.apply()
    }

    //True/ False Single operators Logic --> Total 4 functions
    private fun additionPrefLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, false)
    }

    private fun subtractionPrefLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, false)
    }

    private fun multiplyPrefLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, false)
    }

    private fun dividePrefLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, true)
    }


    //True/ False double operators Logic --> Total 6 functions
    private fun additionSubLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, false)
    }

    private fun additionMulLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, false)
    }

    private fun additionDivLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, true)
    }

    private fun subMulLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, false)
    }

    private fun subDivLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, true)
    }

    private fun mulDivLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, true)
    }


    //True/ False triple operators Logic --> Total 4 functions
    private fun addSubMulLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, false)
    }

    private fun addSubDivLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, true)
    }

    private fun addMulDivLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, true)
    }

    private fun subMulDivLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, true)
    }


    //True/ False All(4) operators Logic --> Total 1 function
    private fun addSubMulDivLogic() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, true)
    }

    //True/ False logic ends


    //Equations Single operators Logic --> Total 4 functions
    private fun additionPrefLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, false)
    }

    private fun subtractionPrefLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, false)
    }

    private fun multiplyPrefLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, false)
    }

    private fun dividePrefLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, true)
    }


    //Equations double operators Logic --> Total 6 functions
    private fun additionSubLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, false)
    }

    private fun additionMulLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, false)
    }

    private fun additionDivLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, true)
    }

    private fun subMulLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, false)
    }

    fun subDivLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, true)
    }

    fun mulDivLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, true)
    }


    //Equations triple operators Logic --> Total 4 functions
    private fun addSubMulLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, false)
    }

    private fun addSubDivLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, true)
    }

    private fun addMulDivLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, true)
    }

    private fun subMulDivLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, false)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, true)
    }


    //Equations All(4) operators Logic --> Total 1 functions
    private fun addSubMulDivLogicEquation() {
        editor.putBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, true)
        editor.putBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, true)
    }


    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
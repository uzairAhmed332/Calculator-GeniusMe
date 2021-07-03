package com.uzair.utils

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.uzair.activity.Calculator
import com.uzair.activity.EquationQuestion
import com.uzair.activity.TrueFalseQuestion

class SharedPrefsSettings : AppCompatActivity(){

    var sharedPref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        if (!sharedPref!!.getBoolean(IS_QUIZ_FIRST, false)) {
            val intent = Intent(applicationContext,
                    Calculator::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        } else {
            // Deciding which Quiz type screen will appear using sharedPrefs
            val intent: Intent
            if (sharedPref?.getBoolean(GO_TO_EQUATION_QUIZ_ACTIVITY, false) == true) {
                //Making it true when score is 3 or greater in True/ False quiz
                editor = sharedPref?.edit()
                editor?.putBoolean(GO_TO_EQUATION_QUIZ_ACTIVITY, false)
                editor?.apply()
                intent = Intent(applicationContext, EquationQuestion::class.java)
            } else {
               // intent = Intent(applicationContext, EquationQuestion::class.java)
                intent = Intent(applicationContext, TrueFalseQuestion::class.java)
            }
            startActivity(intent)
        }
    }


    companion object {
        const val KEY_ADDITION_ONLY_TRUE_FALSE = "addition_only_true_false"
        const val KEY_SUBTRACTION_ONLY_TRUE_FALSE = "subtraction_only_true_false"
        const val KEY_MULTIPLICATION_ONLY_TRUE_FALSE = "multiplication_only_true_false"
        const val KEY_DIVISION_ONLY_TRUE_FALSE = "division_only_true_false"

        const val KEY_ADDITION_ONLY_EQUATIONS = "addition_only_equations"
        const val KEY_SUBTRACTION_ONLY_EQUATIONS = "subtraction_only_equations"
        const val KEY_MULTIPLICATION_ONLY_EQUATIONS = "multiplication_only_equations"
        const val KEY_DIVISION_ONLY_EQUATIONS = "division_only_equations"

        const val GO_TO_EQUATION_QUIZ_ACTIVITY = "go_to_equation_quiz_activity"
        const val KEY_FLASHING_TEXT = "flashing_text"
        var IS_QUIZ_FIRST = "is_quiz_first"


    }
}
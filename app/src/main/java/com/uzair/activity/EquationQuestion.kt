package com.uzair.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.uzair.utils.SharedPrefsSettings
import kotlinx.android.synthetic.main.activity_equation.*
import java.util.*


class EquationQuestion : AppCompatActivity() {

    private val questions = ArrayList<String>()
    var locationOfCorrectAnswer = 0
    var score = 0
    var numberOfQuestions = 0
    var onARoll = 0
    var feedBackNum = 0
    var wrongOrCorrect = 0
    private var countDownTimer: CountDownTimer? = null
    private var correctAnimation: Animation? = null
    private var feedBackAnimation: Animation? = null
    private var flashingText: Boolean? = null
    var sharedPref: SharedPreferences? = null

    //Boolean values to check user preference.
    private var addition: Boolean? = null
    private var subtraction: Boolean? = null
    private var multiplication: Boolean? = null
    private var division: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equation)

        //Variables to hold animation values.
        correctAnimation = AnimationUtils.loadAnimation(this, R.anim.correct_animation)
        feedBackAnimation = AnimationUtils.loadAnimation(this, R.anim.flicker_animation)

        //Initial animation to generate the first question
        button0?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.from_right_0))
        button1?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.from_right_1))
        button2?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.from_right_2))
        button3?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.from_right_3))

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        addition = sharedPref?.getBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_EQUATIONS, false)
        subtraction = sharedPref?.getBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_EQUATIONS, false)
        multiplication = sharedPref?.getBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_EQUATIONS, false)
        division = sharedPref?.getBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_EQUATIONS, false)
        flashingText = sharedPref?.getBoolean(SharedPrefsSettings.KEY_FLASHING_TEXT, true)

        timer()
        generateQuestions()

    }

    private fun sumQuestion(): String {
        val rd = Random()
        val num1 = rd.nextInt(16) + 2
        val num2 = rd.nextInt(16) + 2
        return getString(R.string.sum, num1, num2, num1 + num2)
    }

    private fun subtractQuestion(): String {
        val rd = Random()
        val num1 = rd.nextInt(16) + 1
        val num2 = rd.nextInt(16) + num1
        return getString(R.string.sub, num2, num1, num2 - num1)
    }

    private fun multiplyQuestion(): String {
        val rd = Random()
        val num1 = rd.nextInt(16) + 1
        val num2 = rd.nextInt(16) + 1
        return getString(R.string.mult, num1, num2, num1 * num2)
    }

    private fun divisionQuestion(): String {
        val rd = Random()
        var num1 = rd.nextInt(16) + 10
        var num2 = rd.nextInt(16) + 10
        while (num2 % num1 != 0) {
            num1 = rd.nextInt(16) + 1
            num2 = rd.nextInt(16) + num1
        }
        return getString(R.string.div, num2, num1, num2 / num1)
    }

    private fun wrongTypeOfQuestion(): String {
        val rd = Random()
        return when (rd.nextInt(4)) {
            0 -> {
                if (addition == true) {
                    val num1 = rd.nextInt(15) + 1
                    val num2 = rd.nextInt(12) + 1
                    var num3 = rd.nextInt(40) + 1
                    while (num3 == num1 + num2) {
                        num3 = rd.nextInt(40) + 1
                    }
                    return getString(R.string.sum, num1, num2, num3)
                } else {
                    if (subtraction == true) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(10 - 5 + 1) + 5
                        var num3 = rd.nextInt(15) + 1
                        while (num3 == num1 - num2) {
                            num3 = rd.nextInt(15) + 1
                        }
                        return getString(R.string.sub, num1, num2, num3)
                    } else {
                        if (multiplication == true) {
                            val num1 = rd.nextInt(12) + 1
                            val num2 = rd.nextInt(12) + 1
                            var num3 = rd.nextInt(50) + 1
                            while (num3 == num1 * num2) {
                                num3 = rd.nextInt(50) + 1
                            }
                            return getString(R.string.mult, num1, num2, num3)
                        } else {
                            if (division == true) {
                                val num1 = rd.nextInt(25) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(12) + 1
                                while (num3 == num1 / num2) {
                                    num3 = rd.nextInt(12) + 1
                                }
                                return getString(R.string.div, num1, num2, num3)
                            }
                        }
                    }
                }
                if (subtraction == true) {
                    val num1 = rd.nextInt(15) + 1
                    val num2 = rd.nextInt(10 - 5 + 1) + 5
                    var num3 = rd.nextInt(15) + 1
                    while (num3 == num1 - num2) {
                        num3 = rd.nextInt(15) + 1
                    }
                    return getString(R.string.sub, num1, num2, num3)
                } else {
                    if (addition == true) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(12) + 1
                        var num3 = rd.nextInt(40) + 1
                        while (num3 == num1 + num2) {
                            num3 = rd.nextInt(40) + 1
                        }
                        return getString(R.string.sum, num1, num2, num3)
                    } else {
                        if (multiplication == true) {
                            val num1 = rd.nextInt(12) + 1
                            val num2 = rd.nextInt(12) + 1
                            var num3 = rd.nextInt(50) + 1
                            while (num3 == num1 * num2) {
                                num3 = rd.nextInt(50) + 1
                            }
                            return getString(R.string.mult, num1, num2, num3)
                        } else {
                            if (division == true) {
                                val num1 = rd.nextInt(25) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(12) + 1
                                while (num3 == num1 / num2) {
                                    num3 = rd.nextInt(12) + 1
                                }
                                return getString(R.string.div, num1, num2, num3)
                            }
                        }
                    }
                }
                if (multiplication == true) {
                    val num1 = rd.nextInt(12) + 1
                    val num2 = rd.nextInt(12) + 1
                    var num3 = rd.nextInt(50) + 1
                    while (num3 == num1 * num2) {
                        num3 = rd.nextInt(50) + 1
                    }
                    return getString(R.string.mult, num1, num2, num3)
                } else {
                    if (addition == true) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(12) + 1
                        var num3 = rd.nextInt(40) + 1
                        while (num3 == num1 + num2) {
                            num3 = rd.nextInt(40) + 1
                        }
                        return getString(R.string.sum, num1, num2, num3)
                    } else {
                        if (subtraction == true) {
                            val num1 = rd.nextInt(15) + 1
                            val num2 = rd.nextInt(10 - 5 + 1) + 5
                            var num3 = rd.nextInt(15) + 1
                            while (num3 == num1 - num2) {
                                num3 = rd.nextInt(15) + 1
                            }
                            return getString(R.string.sub, num1, num2, num3)
                        } else {
                            if (division == true) {
                                val num1 = rd.nextInt(25) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(12) + 1
                                while (num3 == num1 / num2) {
                                    num3 = rd.nextInt(12) + 1
                                }
                                return getString(R.string.div, num1, num2, num3)
                            }
                        }
                    }
                }
                if (division == true) {
                    val num1 = rd.nextInt(25) + 1
                    val num2 = rd.nextInt(12) + 1
                    var num3 = rd.nextInt(12) + 1
                    while (num3 == num1 / num2) {
                        num3 = rd.nextInt(12) + 1
                    }
                    return getString(R.string.div, num1, num2, num3)
                } else {
                    if (addition == true) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(12) + 1
                        var num3 = rd.nextInt(40) + 1
                        while (num3 == num1 + num2) {
                            num3 = rd.nextInt(40) + 1
                        }
                        return getString(R.string.sum, num1, num2, num3)
                    } else {
                        if (subtraction == true) {
                            val num1 = rd.nextInt(15) + 1
                            val num2 = rd.nextInt(10 - 5 + 1) + 5
                            var num3 = rd.nextInt(15) + 1
                            while (num3 == num1 - num2) {
                                num3 = rd.nextInt(15) + 1
                            }
                            return getString(R.string.sub, num1, num2, num3)
                        } else {
                            if (multiplication == true) {
                                val num1 = rd.nextInt(12) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(50) + 1
                                while (num3 == num1 * num2) {
                                    num3 = rd.nextInt(50) + 1
                                }
                                return getString(R.string.mult, num1, num2, num3)
                            }
                        }
                    }
                }
                "Enable at least one type of question."
            }
            1 -> {
                if (subtraction == true) {
                    val num1 = rd.nextInt(15) + 1
                    val num2 = rd.nextInt(10 - 5 + 1) + 5
                    var num3 = rd.nextInt(15) + 1
                    while (num3 == num1 - num2) {
                        num3 = rd.nextInt(15) + 1
                    }
                    return getString(R.string.sub, num1, num2, num3)
                } else {
                    if (addition == true) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(12) + 1
                        var num3 = rd.nextInt(40) + 1
                        while (num3 == num1 + num2) {
                            num3 = rd.nextInt(40) + 1
                        }
                        return getString(R.string.sum, num1, num2, num3)
                    } else {
                        if (multiplication == true) {
                            val num1 = rd.nextInt(12) + 1
                            val num2 = rd.nextInt(12) + 1
                            var num3 = rd.nextInt(50) + 1
                            while (num3 == num1 * num2) {
                                num3 = rd.nextInt(50) + 1
                            }
                            return getString(R.string.mult, num1, num2, num3)
                        } else {
                            if (division == true) {
                                val num1 = rd.nextInt(25) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(12) + 1
                                while (num3 == num1 / num2) {
                                    num3 = rd.nextInt(12) + 1
                                }
                                return getString(R.string.div, num1, num2, num3)
                            }
                        }
                    }
                }
                if (multiplication == true) {
                    val num1 = rd.nextInt(12) + 1
                    val num2 = rd.nextInt(12) + 1
                    var num3 = rd.nextInt(50) + 1
                    while (num3 == num1 * num2) {
                        num3 = rd.nextInt(50) + 1
                    }
                    return getString(R.string.mult, num1, num2, num3)
                } else {
                    if (addition == true) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(12) + 1
                        var num3 = rd.nextInt(40) + 1
                        while (num3 == num1 + num2) {
                            num3 = rd.nextInt(40) + 1
                        }
                        return getString(R.string.sum, num1, num2, num3)
                    } else {
                        if (subtraction == true) {
                            val num1 = rd.nextInt(15) + 1
                            val num2 = rd.nextInt(10 - 5 + 1) + 5
                            var num3 = rd.nextInt(15) + 1
                            while (num3 == num1 - num2) {
                                num3 = rd.nextInt(15) + 1
                            }
                            return getString(R.string.sub, num1, num2, num3)
                        } else {
                            if (division == true) {
                                val num1 = rd.nextInt(25) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(12) + 1
                                while (num3 == num1 / num2) {
                                    num3 = rd.nextInt(12) + 1
                                }
                                return getString(R.string.div, num1, num2, num3)
                            }
                        }
                    }
                }
                if (division == true) {
                    val num1 = rd.nextInt(25) + 1
                    val num2 = rd.nextInt(12) + 1
                    var num3 = rd.nextInt(12) + 1
                    while (num3 == num1 / num2) {
                        num3 = rd.nextInt(12) + 1
                    }
                    return getString(R.string.div, num1, num2, num3)
                } else {
                    if (addition == true) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(12) + 1
                        var num3 = rd.nextInt(40) + 1
                        while (num3 == num1 + num2) {
                            num3 = rd.nextInt(40) + 1
                        }
                        return getString(R.string.sum, num1, num2, num3)
                    } else {
                        if (subtraction == true) {
                            val num1 = rd.nextInt(15) + 1
                            val num2 = rd.nextInt(10 - 5 + 1) + 5
                            var num3 = rd.nextInt(15) + 1
                            while (num3 == num1 - num2) {
                                num3 = rd.nextInt(15) + 1
                            }
                            return getString(R.string.sub, num1, num2, num3)
                        } else {
                            if (multiplication == true) {
                                val num1 = rd.nextInt(12) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(50) + 1
                                while (num3 == num1 * num2) {
                                    num3 = rd.nextInt(50) + 1
                                }
                                return getString(R.string.mult, num1, num2, num3)
                            }
                        }
                    }
                }
                "Enable at least one type of question."
            }
            2 -> {
                if (multiplication == true) {
                    val num1 = rd.nextInt(12) + 1
                    val num2 = rd.nextInt(12) + 1
                    var num3 = rd.nextInt(50) + 1
                    while (num3 == num1 * num2) {
                        num3 = rd.nextInt(50) + 1
                    }
                    return getString(R.string.mult, num1, num2, num3)
                } else {
                    if (addition == true) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(12) + 1
                        var num3 = rd.nextInt(40) + 1
                        while (num3 == num1 + num2) {
                            num3 = rd.nextInt(40) + 1
                        }
                        return getString(R.string.sum, num1, num2, num3)
                    } else {
                        if (subtraction == true) {
                            val num1 = rd.nextInt(15) + 1
                            val num2 = rd.nextInt(10 - 5 + 1) + 5
                            var num3 = rd.nextInt(15) + 1
                            while (num3 == num1 - num2) {
                                num3 = rd.nextInt(15) + 1
                            }
                            return getString(R.string.sub, num1, num2, num3)
                        } else {
                            if (division == true) {
                                val num1 = rd.nextInt(25) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(12) + 1
                                while (num3 == num1 / num2) {
                                    num3 = rd.nextInt(12) + 1
                                }
                                return getString(R.string.div, num1, num2, num3)
                            }
                        }
                    }
                }
                if (division == true) {
                    val num1 = rd.nextInt(25) + 1
                    val num2 = rd.nextInt(12) + 1
                    var num3 = rd.nextInt(12) + 1
                    while (num3 == num1 / num2) {
                        num3 = rd.nextInt(12) + 1
                    }
                    return getString(R.string.div, num1, num2, num3)
                } else {
                    if (addition == true) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(12) + 1
                        var num3 = rd.nextInt(40) + 1
                        while (num3 == num1 + num2) {
                            num3 = rd.nextInt(40) + 1
                        }
                        return getString(R.string.sum, num1, num2, num3)
                    } else {
                        if (subtraction == true) {
                            val num1 = rd.nextInt(15) + 1
                            val num2 = rd.nextInt(10 - 5 + 1) + 5
                            var num3 = rd.nextInt(15) + 1
                            while (num3 == num1 - num2) {
                                num3 = rd.nextInt(15) + 1
                            }
                            return getString(R.string.sub, num1, num2, num3)
                        } else {
                            if (multiplication == true) {
                                val num1 = rd.nextInt(12) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(50) + 1
                                while (num3 == num1 * num2) {
                                    num3 = rd.nextInt(50) + 1
                                }
                                return getString(R.string.mult, num1, num2, num3)
                            }
                        }
                    }
                }
                "Enable at least one type of question."
            }
            3 -> {
                if (division == true) {
                    val num1 = rd.nextInt(25) + 1
                    val num2 = rd.nextInt(12) + 1
                    var num3 = rd.nextInt(12) + 1
                    while (num3 == num1 / num2) {
                        num3 = rd.nextInt(12) + 1
                    }
                    return getString(R.string.div, num1, num2, num3)
                } else {
                    if (addition!!) {
                        val num1 = rd.nextInt(15) + 1
                        val num2 = rd.nextInt(12) + 1
                        var num3 = rd.nextInt(40) + 1
                        while (num3 == num1 + num2) {
                            num3 = rd.nextInt(40) + 1
                        }
                        return getString(R.string.sum, num1, num2, num3)
                    } else {
                        if (subtraction!!) {
                            val num1 = rd.nextInt(15) + 1
                            val num2 = rd.nextInt(10 - 5 + 1) + 5
                            var num3 = rd.nextInt(15) + 1
                            while (num3 == num1 - num2) {
                                num3 = rd.nextInt(15) + 1
                            }
                            return getString(R.string.sub, num1, num2, num3)
                        } else {
                            if (multiplication!!) {
                                val num1 = rd.nextInt(12) + 1
                                val num2 = rd.nextInt(12) + 1
                                var num3 = rd.nextInt(50) + 1
                                while (num3 == num1 * num2) {
                                    num3 = rd.nextInt(50) + 1
                                }
                                return getString(R.string.mult, num1, num2, num3)
                            }
                        }
                    }
                }
                "Enable at least one type of question."
            }
            else -> "Enable at least one type of question."
        }
    }

    private fun typeOfQuestion(): String {
        val rd = Random()
        return when (rd.nextInt(4)) {
            0 -> {
                if (addition == true) {
                    return sumQuestion()
                } else {
                    if (subtraction == true) {
                        return subtractQuestion()
                    } else {
                        if (division == true) {
                            return divisionQuestion()
                        } else {
                            if (multiplication == true) {
                                return multiplyQuestion()
                            }
                        }
                    }
                }
                if (subtraction == true) {
                    return subtractQuestion()
                } else {
                    if (addition == true) {
                        return sumQuestion()
                    } else {
                        if (division == true) {
                            return divisionQuestion()
                        } else {
                            if (multiplication == true) {
                                return multiplyQuestion()
                            }
                        }
                    }
                }
                if (multiplication == true) {
                    return multiplyQuestion()
                } else {
                    if (addition == true) {
                        return sumQuestion()
                    } else {
                        if (subtraction == true) {
                            return subtractQuestion()
                        } else {
                            if (division == true) {
                                return divisionQuestion()
                            }
                        }
                    }
                }
                if (division == true) {
                    return divisionQuestion()
                } else {
                    if (addition == true) {
                        return sumQuestion()
                    } else {
                        if (subtraction == true) {
                            return subtractQuestion()
                        } else {
                            if (multiplication == true) {
                                return multiplyQuestion()
                            }
                        }
                    }
                }
                "Enable at least one type of question."
            }
            1 -> {
                if (subtraction == true) {
                    return subtractQuestion()
                } else {
                    if (addition == true) {
                        return sumQuestion()
                    } else {
                        if (division == true) {
                            return divisionQuestion()
                        } else {
                            if (multiplication == true) {
                                return multiplyQuestion()
                            }
                        }
                    }
                }
                if (multiplication == true) {
                    return multiplyQuestion()
                } else {
                    if (addition == true) {
                        return sumQuestion()
                    } else {
                        if (subtraction == true) {
                            return subtractQuestion()
                        } else {
                            if (division == true) {
                                return divisionQuestion()
                            }
                        }
                    }
                }
                if (division == true) {
                    return divisionQuestion()
                } else {
                    if (addition == true) {
                        return sumQuestion()
                    } else {
                        if (subtraction == true) {
                            return subtractQuestion()
                        } else {
                            if (multiplication == true) {
                                return multiplyQuestion()
                            }
                        }
                    }
                }
                "Enable at least one type of question."
            }
            2 -> {
                if (multiplication == true) {
                    return multiplyQuestion()
                } else {
                    if (addition == true) {
                        return sumQuestion()
                    } else {
                        if (subtraction == true) {
                            return subtractQuestion()
                        } else {
                            if (division == true) {
                                return divisionQuestion()
                            }
                        }
                    }
                }
                if (division == true) {
                    return divisionQuestion()
                } else {
                    if (addition == true) {
                        return sumQuestion()
                    } else {
                        if (subtraction == true) {
                            return subtractQuestion()
                        } else {
                            if (multiplication == true) {
                                return multiplyQuestion()
                            }
                        }
                    }
                }
                "Enable at least one type of question."
            }
            3 -> {
                if (division == true) {
                    return divisionQuestion()
                } else {
                    if (addition == true) {
                        return sumQuestion()
                    } else {
                        if (subtraction == true) {
                            return subtractQuestion()
                        } else {
                            if (multiplication == true) {
                                return multiplyQuestion()
                            }
                        }
                    }
                }
                "Enable at least one type of question."
            }
            else -> "Enable at least one type of question."
        }
    }

    private fun generateQuestions() {
        val rd = Random()
        wrongOrCorrect = rd.nextInt(2)
        locationOfCorrectAnswer = rd.nextInt(4)
        for (i in 0..3) {
            if (i == locationOfCorrectAnswer) {
                questions.add(typeOfQuestion())
            } else {
                questions.add(wrongTypeOfQuestion())
            }
        }
        feedBackNum = rd.nextInt(12)
        scoreText!!.text = getString(R.string.score, score)
        button0!!.text = questions[0]
        button1!!.text = questions[1]
        button2!!.text = questions[2]
        button3!!.text = questions[3]
        questions.clear()
    }

    fun choose(view: View) {
        if (flashingText!!) {
            userFeedback!!.startAnimation(feedBackAnimation)
        }
        if (view.tag.toString() == Integer.toString(locationOfCorrectAnswer)) {
            score++
            numberOfQuestions++
            generateQuestions()

            onARoll++
            if (feedBackNum == 0 || numberOfQuestions == 1) {
                userFeedback!!.text = getString(R.string.good_job)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 1) {
                userFeedback!!.text = getString(R.string.amazing)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 2) {
                userFeedback!!.text = getString(R.string.fantastic)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 3) {
                userFeedback!!.text = getString(R.string.damn)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 4) {
                userFeedback!!.text = getString(R.string.genius)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 5) {
                userFeedback!!.text = getString(R.string.sweet)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 6) {
                userFeedback!!.text = getString(R.string.crazy)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 7) {
                userFeedback!!.text = getString(R.string.keep)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 8) {
                userFeedback!!.text = getString(R.string.unbelievable)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 9) {
                userFeedback!!.text = getString(R.string.surprised)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 10) {
                userFeedback!!.text = getString(R.string.brilliant)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            } else if (feedBackNum == 11) {
                userFeedback!!.text = getString(R.string.brilliant)
                userFeedback!!.setTextColor(resources.getColor(R.color.correct_ans))
            }
        } else {
            onARoll = 0
            numberOfQuestions++
            if (locationOfCorrectAnswer == 0) {
                button0!!.startAnimation(correctAnimation)
            } else if (locationOfCorrectAnswer == 1) {
                button1!!.startAnimation(correctAnimation)
            } else if (locationOfCorrectAnswer == 2) {
                button2!!.startAnimation(correctAnimation)
            } else if (locationOfCorrectAnswer == 3) {
                button3!!.startAnimation(correctAnimation)
            }
            val rd = Random()
            when (rd.nextInt(3)) {
                0 -> {
                    userFeedback!!.text = getString(R.string.ohno)
                    userFeedback!!.setTextColor(resources.getColor(R.color.wrong_ans))
                }
                1 -> {
                    userFeedback!!.text = getString(R.string.next)
                    userFeedback!!.setTextColor(resources.getColor(R.color.wrong_ans))
                }
                2 -> {
                    userFeedback!!.text = getString(R.string.sad)
                    userFeedback!!.setTextColor(resources.getColor(R.color.wrong_ans))
                }
            }

        }
    }

    fun timer() {
        countDownTimer = object : CountDownTimer(11000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished > 10000) timeLeftText.text = getString(R.string.timer_true_false, millisUntilFinished.toInt() / 1000) else if (millisUntilFinished < 10000 && millisUntilFinished > 5000) {
                    //Timer flickering gets faster as time runs out
                    //Adds num1 0 before last digit
                    timeLeftText.text = getString(R.string.timer_true_false_ten_less, millisUntilFinished.toInt() / 1000)
                    //Flickers only if its enabled in the settings.
                    if (flashingText!!) {
                        timeLeftText.startAnimation(AnimationUtils.loadAnimation(this@EquationQuestion, R.anim.flicker_animation_2))
                    }
                } else if (millisUntilFinished < 5000 && millisUntilFinished > 3000) {
                    timeLeftText.text = getString(R.string.timer_true_false_ten_less, millisUntilFinished.toInt() / 1000)
                    if (flashingText!!) {
                        timeLeftText.startAnimation(AnimationUtils.loadAnimation(this@EquationQuestion, R.anim.flicker_animation_1))
                    }
                } else {
                    timeLeftText.text = getString(R.string.timer_true_false_ten_less, millisUntilFinished.toInt() / 1000)
                    if (flashingText!!) {
                        timeLeftText.startAnimation(AnimationUtils.loadAnimation(this@EquationQuestion, R.anim.flicker_animation))
                    }
                }
            }

            override fun onFinish() {
                val intent = Intent(applicationContext,
                        Calculator::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }.start()
    }

}
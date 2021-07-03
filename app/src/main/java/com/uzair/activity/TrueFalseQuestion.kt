package com.uzair.activity


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.uzair.utils.SharedPrefsSettings
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_true_false.*
import java.util.*


class TrueFalseQuestion : AppCompatActivity() {

    private var correctAnswer = 0
    private var score = 0
    private var wrongOrCorrect = 0
    private var numberOfQuestions = 0
    private var feedBackNum = 0
    private var correctAnimation: Animation? = null

    private var addition: Boolean? = null
    private var subtraction: Boolean? = null
    private var multiplication: Boolean? = null
    private var division: Boolean? = null

    private var flashingText: Boolean? = null
    private var countDownTimer: CountDownTimer? = null

    var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_false)

        correctAnimation = AnimationUtils.loadAnimation(this, R.anim.correct_animation)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        addition = sharedPref?.getBoolean(SharedPrefsSettings.KEY_ADDITION_ONLY_TRUE_FALSE, false)
        subtraction = sharedPref?.getBoolean(SharedPrefsSettings.KEY_SUBTRACTION_ONLY_TRUE_FALSE, false)
        multiplication = sharedPref?.getBoolean(SharedPrefsSettings.KEY_MULTIPLICATION_ONLY_TRUE_FALSE, false)
        division = sharedPref?.getBoolean(SharedPrefsSettings.KEY_DIVISION_ONLY_TRUE_FALSE, false)
        flashingText = sharedPref?.getBoolean(SharedPrefsSettings.KEY_FLASHING_TEXT, true)

        generateQuestion()

        //Keeps track of time
       //  timer()

    }


    //  Answer Button
    fun choose(view: View) {
        if (flashingText == true) {
            userFeedback.startAnimation(AnimationUtils.loadAnimation(this, R.anim.flicker_animation))
        }
        Log.d("test", "1: " + view.tag.toString())
        if (view.tag.toString() == wrongOrCorrect.toString()) { //If condition met --> Choice is correct
            score++
            generateQuestion()
            if (flashingText == true) {
                quickMathQuestion.startAnimation(AnimationUtils.loadAnimation(this, R.anim.question_flicker))
            }
            quickMathScore.text = getString(R.string.score, score)
            numberOfQuestions++

            if (feedBackNum == 0 || numberOfQuestions == 1) {
                userFeedback.text = getString(R.string.good_job) // good_job
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 1) {
                userFeedback.text = getString(R.string.amazing)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 2) {
                userFeedback.text = getString(R.string.fantastic)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 3) {
                userFeedback.text = getString(R.string.damn)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 4) {
                userFeedback.text = getString(R.string.genius)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 5) {
                userFeedback.text = getString(R.string.sweet)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 6) {
                userFeedback.text = getString(R.string.crazy)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 7) {
                userFeedback.text = getString(R.string.keep)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 8) {
                userFeedback.text = getString(R.string.unbelievable)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 9) {
                userFeedback.text = getString(R.string.surprised)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 10) {
                userFeedback.text = getString(R.string.brilliant)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            } else if (feedBackNum == 11) {
                userFeedback.text = getString(R.string.way_to_go)
                userFeedback.setTextColor(ContextCompat.getColor(this, R.color.correct_ans))
            }
        } else {
          //  Choice is incorrect selected

            quickMathScore!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.correct_animation))
            if (wrongOrCorrect == 1) {  //0 -> when incorrect question created!
                //When true button is Incorrect
                wrongButton!!.startAnimation(correctAnimation)
                Log.d("qwqw","a1")
            } else {  //1 -> when correct question created!
                //When True button is Correct
                Log.d("qwqw","a2")
                correctButton!!.startAnimation(correctAnimation)
            }
            val rd = Random()
            when (rd.nextInt(3)) {
                0 -> {
                    userFeedback.text = getString(R.string.better_luck_next_time)
                    userFeedback.setTextColor(ContextCompat.getColor(this, R.color.wrong_ans))
                }
                2 -> {
                    userFeedback.text = getString(R.string.ohno)
                    userFeedback.setTextColor(ContextCompat.getColor(this, R.color.wrong_ans))
                }
                3 -> {
                    userFeedback.text = "You can do Better!"
                    userFeedback.setTextColor(ContextCompat.getColor(this, R.color.wrong_ans))
                }
                4 -> {
                    userFeedback.text = getString(R.string.sad)
                    userFeedback.setTextColor(ContextCompat.getColor(this, R.color.wrong_ans))
                }
            }
            generateQuestion()
            numberOfQuestions++
        }
        view.isEnabled = false
        view.postDelayed({ view.isEnabled = true }, 500)
    }

    //Generates correct or incorrect question randomly depending on user preference
    private fun generateQuestion() {
        val rd = Random()
        val questionType = rd.nextInt(4)
        feedBackNum = rd.nextInt(12)
        if (questionType == 0) {
            if (addition == true) {
                sumQuestion()
            } else {
                if (subtraction == true) {
                    subtractQuestion()
                } else {
                    if (multiplication == true) multiplyQuestions() else {
                        if (division == true) divisionQuestion()
                    }
                }
            }
        } else if (questionType == 1) {
            if (subtraction == true) {
                subtractQuestion()
            } else {
                if (addition == true) {
                    sumQuestion()
                } else {
                    if (multiplication == true) multiplyQuestions() else {
                        if (division == true) divisionQuestion()
                    }
                }
            }
        } else if (questionType == 2) {
            if (multiplication == true) {
                multiplyQuestions()
            } else {
                if (addition == true) {
                    sumQuestion()
                } else {
                    if (subtraction == true) subtractQuestion() else {
                        if (division == true) divisionQuestion()
                    }
                }
            }
        } else if (questionType == 3) {
            if (division == true) {
                divisionQuestion()
            } else {
                if (addition == true) {
                    sumQuestion()
                } else {
                    if (subtraction == true) subtractQuestion() else {
                        if (multiplication!!) multiplyQuestions()
                    }
                }
            }
        }
    }

    //Creates a sum True/ False  questions (Done)
    private fun sumQuestion() {
        val rd = Random()

        val num1: Int = rd.nextInt(16) + 10
        val num2: Int = rd.nextInt(16) + 10

        /*
        * Decides runtime whether the question contains correct or wrong question
        * 0 -> correct question
        * 1 -> Incorrect question
        */
        wrongOrCorrect = rd.nextInt(2)
        correctAnswer = num1 + num2

        if (wrongOrCorrect == 0) {
            //Create the correct question
            quickMathQuestion.text = getString(R.string.sum, num1, num2, correctAnswer)
        } else {
            //Create the wrong question
            var incorrectAnswer = rd.nextInt(32) + 20 //40

            //If, by any chance, incorrect questions is correct. Generate question again
            while (incorrectAnswer == correctAnswer) {
                incorrectAnswer = rd.nextInt(32) + 20
            }

            quickMathQuestion.text = getString(R.string.sum, num1, num2, incorrectAnswer)
        }
    }

    //Creates a subtract True/ False question
    private fun subtractQuestion() {
        val rd = Random()
        val num1: Int
        val num2: Int

        num1 = rd.nextInt(16) + 1
        num2 = rd.nextInt(16) + num1  //(adding num1 to make the subtraction at least by 1!)

        correctAnswer = num2 - num1
        var incorrectAnswer: Int

        wrongOrCorrect = rd.nextInt(2)

        Log.d("test", "num1 $num1 num2 $num2 answer $correctAnswer")
        if (wrongOrCorrect == 0) {
            Log.d("test", "0")
            //Create the correct question
            quickMathQuestion.text = getString(R.string.sub, num2, num1, correctAnswer)

        } else {
            Log.d("test", "1")
            //Create the wrong question
            incorrectAnswer = rd.nextInt(20) + 1

            //If, by any chance, incorrect questions is correct. Generate question again
            while (incorrectAnswer == correctAnswer) {
                incorrectAnswer = rd.nextInt(20) + 1
            }
            quickMathQuestion.text = getString(R.string.sub, num2, num1, incorrectAnswer)
        }
    }

    //Creates a multiply True/ False question
    private fun multiplyQuestions() {
        val rd = Random()

        val num1: Int = rd.nextInt(16) + 1
        val num2: Int = rd.nextInt(16) + 1

        correctAnswer = num1 * num2
        wrongOrCorrect = rd.nextInt(2)

        var incorrectAnswer: Int
        Log.d("test", "num1 $num1 num2 $num2 answer $correctAnswer")
        if (wrongOrCorrect == 0) {
            Log.d("test", "0")
            //Create the correct question
            quickMathQuestion.text = getString(R.string.mult, num1, num2, correctAnswer)
        } else {
            Log.d("test", "1")
            //Create the wrong question
            incorrectAnswer = rd.nextInt(100 ) + 20

            while (incorrectAnswer == correctAnswer) {
                incorrectAnswer = rd.nextInt(100 ) + 20
            }
            quickMathQuestion.text = getString(R.string.mult, num1, num2, incorrectAnswer)
        }
    }

    //Creates a division question
    private fun divisionQuestion() {
        val rd = Random()
        var num1: Int
        var num2: Int

        num1 = rd.nextInt(16) + 10
        num2 = rd.nextInt(16) + 10

        while (num2 % num1 != 0) { //if num2 is not divisible by num1 than generate again
            num1 = rd.nextInt(10) + 1
            num2 = rd.nextInt(10) + num1
        }
        correctAnswer = num2 / num1

        Log.d("test", "num1 $num1 num2 $num2 answer $correctAnswer")
        var incorrectAnswer: Int
        wrongOrCorrect = rd.nextInt(2)
        if (wrongOrCorrect == 0) {
            //Create the correct question
            Log.d("test", "0")
            quickMathQuestion!!.text = getString(R.string.div, num2, num1, correctAnswer)
        } else {
            //Create the wrong question
            Log.d("test", "1")
            incorrectAnswer = rd.nextInt(24) + 1

            while (incorrectAnswer == correctAnswer) {
                incorrectAnswer = rd.nextInt(24) + 1
            }
            quickMathQuestion!!.text = getString(R.string.div, num2, num1, incorrectAnswer)
        }
    }

    //Timer that keeps track of time
    private fun timer() {
//        int time = Integer.parseInt(timerDuration) * 1000;
        countDownTimer = object : CountDownTimer(11000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished > 10000) timerText!!.text = getString(R.string.timer_true_false, millisUntilFinished.toInt() / 1000) else if (millisUntilFinished < 10000 && millisUntilFinished > 5000) {
                    //Timer flickering gets faster as time runs out
                    //Adds a 0 before last digit
                    timerText!!.text = getString(R.string.timer_true_false_ten_less, millisUntilFinished.toInt() / 1000)
                    //Flickers only if its enabled in the settings.
                    if (flashingText == true) {
                        timerText!!.startAnimation(AnimationUtils.loadAnimation(this@TrueFalseQuestion, R.anim.flicker_animation_2))
                    }
                } else if (millisUntilFinished < 5000 && millisUntilFinished > 3000) {
                    timerText!!.text = getString(R.string.timer_true_false_ten_less, millisUntilFinished.toInt() / 1000)
                    if (flashingText== true) {
                        timerText!!.startAnimation(AnimationUtils.loadAnimation(this@TrueFalseQuestion, R.anim.flicker_animation_1))
                    }
                } else {
                    timerText!!.text = getString(R.string.timer_true_false_ten_less, millisUntilFinished.toInt() / 1000)
                    if (flashingText!!) {
                        timerText!!.startAnimation(AnimationUtils.loadAnimation(this@TrueFalseQuestion, R.anim.flicker_animation))
                    }
                }
            }

            override fun onFinish() {
                if (score > 2) {
                    Toasty.success(this@TrueFalseQuestion, "Score = $score Wow you are Genius!", Toast.LENGTH_LONG, false).show()
                    val editor = sharedPref?.edit()
                    editor?.putBoolean(SharedPrefsSettings.GO_TO_EQUATION_QUIZ_ACTIVITY, true)
                    editor?.apply()
                } else {
                    Toasty.error(this@TrueFalseQuestion, "Score = $score You need more practise!", Toast.LENGTH_LONG, false).show()
                }

                val intent = Intent(applicationContext, Calculator::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }.start()
    }

}
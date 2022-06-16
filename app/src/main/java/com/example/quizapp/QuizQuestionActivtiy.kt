package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Reader
import com.github.kittinunf.result.*
import kotlin.random.Random

class QuizQuestionActivtiy : AppCompatActivity(), View.OnClickListener{

    lateinit var categoryi : String
    lateinit var difficulty: String

    lateinit var questionTextView : TextView
    lateinit var questionNumberTextView : TextView
    lateinit var booleanQuestionLayout : FrameLayout
    lateinit var multiChoiceLayout : FrameLayout
    lateinit var trueButton : Button
    lateinit var falseButton : Button
    lateinit var firstOption : Button
    lateinit var secondOption : Button
    lateinit var thirdOption : Button
    lateinit var fourthOption : Button

    var correctAnswer = ""
    var correctAnswerPosition : Int = 0
    var round = 0;
    var clickable : Boolean = false
    var isMultiple : Boolean = true
    var questions : List<QuizQuestion> = listOf()
    var numberOfCorrectAnswers : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question_activtiy)

        initView()

        val intent : Intent = intent;
        val settings: Bundle? = intent.extras
        categoryi = settings?.getString("CATEGORY")!!
        difficulty = settings?.getString("DIFFICULTY")!!

        Fuel.get("https://opentdb.com/api.php", listOf("amount" to "10", "category" to categoryi, "difficulty" to difficulty))
            .responseObject(QuizDataDeserializer()){ request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.i("ErrorMsg",result.getException().message!!)
                        result.getException().stackTrace
                        throw Exception(result.getException())
//                        Log.i("FAIL", questions.size.toString())
                    }

                    is Result.Success -> {
                        result.value.results.also { questions = it.toList() }
                        Log.i("SUCCESS", questions.size.toString())
                        startRound()
                    }
                }
            }
    }

    fun initView() {
        questionTextView  = findViewById(R.id.question)
        questionNumberTextView = findViewById(R.id.questionNumber)
        booleanQuestionLayout = findViewById(R.id.trueFalseLayout)
        multiChoiceLayout= findViewById(R.id.multiChoiceLayout)
        trueButton = findViewById(R.id.trueButton)
        trueButton.setOnClickListener(this)
        falseButton = findViewById(R.id.falseButton)
        falseButton.setOnClickListener(this)
        firstOption = findViewById(R.id.firstButton)
        firstOption.setOnClickListener(this)
        secondOption = findViewById(R.id.secondButton)
        secondOption.setOnClickListener(this)
        thirdOption =  findViewById(R.id.thirdButton)
        thirdOption.setOnClickListener(this)
        fourthOption = findViewById(R.id.fourthButton)
        fourthOption.setOnClickListener(this)
    }

    fun startRound() {
        questionNumberTextView.setText("" + (round+1) + "/" + questions.size.toString())
        if (questions.size == round) {
            gameEnds()
        }
        if (questions[round].type.equals("boolean")) {
            showBooleanQuestion()
        } else {
            showMultipleChoiceQuestion()
        }
        clickable = true
    }

    fun showMultipleChoiceQuestion() {
        changeToMultiple()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            questionTextView.setText(Html.fromHtml(questions[round].question, Html.FROM_HTML_MODE_LEGACY))
        }
        correctAnswerPosition = Random.nextInt(0,3)
        if (correctAnswerPosition == 0) {
            firstOptionIsTrue()
        } else if (correctAnswerPosition == 1) {
            secondOptionIsTrue()
        } else if (correctAnswerPosition == 2) {
            thirdOptionIsTrue()
        } else if (correctAnswerPosition == 3) {
            fourthOptionIsTrue()
        }

    }
    fun showBooleanQuestion() {
        changeToBoolean()
        questionTextView.setText(questions[round].question)
        trueButton.tag = "True"
        falseButton.tag = "False"
    }
    fun evaluate(button : Button) {
        correctAnswer = button.tag.toString()
        if(questions[round].correct_answer == correctAnswer) {
            numberOfCorrectAnswers++
        } else {
            showTheToast("Leider falsch!")
        }
        round++
        startRound()
    }
    fun changeToMultiple() {
        multiChoiceLayout.visibility=View.VISIBLE
        booleanQuestionLayout.visibility=View.GONE
        isMultiple = true
    }

    fun changeToBoolean() {
        multiChoiceLayout.visibility=View.GONE
        booleanQuestionLayout.visibility=View.VISIBLE
        isMultiple = false
    }

    fun gameEnds() {
        val correctAnswerNumber: Bundle = Bundle()
        correctAnswerNumber.putString("NUMBER", numberOfCorrectAnswers.toString())
        val intent: Intent = Intent(this, EvaluationActivity::class.java)
        intent.putExtras(correctAnswerNumber)
        startActivity(intent)
    }

    class QuizDataDeserializer : ResponseDeserializable<QuizResult> {
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, QuizResult::class.java)
    }

    class QuizQuestion {
        var question : String? = null
        var correct_answer : String? = null
        var incorrect_answers : ArrayList<String> = ArrayList()
        var type : String? = null
    }

    class QuizResult {
        var results = emptyArray<QuizQuestion>()
    }

    override fun onClick(p0: View?) {
        if(!clickable) {
            return
        } else if (p0 == trueButton) {
            clickable = false
            evaluate(trueButton)
        } else if (p0 == falseButton) {
            clickable = false
            evaluate(falseButton)
        } else if (p0 == firstOption) {
            clickable = false
            evaluate(firstOption)
        } else if (p0 == secondOption) {
            clickable = false
            evaluate(secondOption)
        } else if (p0 == thirdOption) {
            clickable = false
            evaluate(thirdOption)
        } else if (p0 == fourthOption) {
            clickable = false
            evaluate(fourthOption)
        }
    }
    fun firstOptionIsTrue() {
        firstOption.setText(questions[round].correct_answer)
        firstOption.tag = questions[round].correct_answer
        secondOption.setText(questions[round].incorrect_answers[0])
        secondOption.tag = questions[round].incorrect_answers[0]
        thirdOption.setText(questions[round].incorrect_answers[1])
        thirdOption.tag = questions[round].incorrect_answers[1]
        fourthOption.setText(questions[round].incorrect_answers[2])
        fourthOption.tag = questions[round].incorrect_answers[2]
    }
    fun secondOptionIsTrue() {
        firstOption.setText(questions[round].incorrect_answers[0])
        firstOption.tag = questions[round].incorrect_answers[0]
        secondOption.setText(questions[round].correct_answer)
        secondOption.tag = questions[round].correct_answer
        thirdOption.setText(questions[round].incorrect_answers[1])
        thirdOption.tag = questions[round].incorrect_answers[1]
        fourthOption.setText(questions[round].incorrect_answers[2])
        fourthOption.tag = questions[round].incorrect_answers[2]
    }
    fun thirdOptionIsTrue() {
        firstOption.setText(questions[round].incorrect_answers[0])
        firstOption.tag = questions[round].incorrect_answers[0]
        secondOption.setText(questions[round].incorrect_answers[1])
        secondOption.tag = questions[round].incorrect_answers[1]
        thirdOption.setText(questions[round].correct_answer)
        thirdOption.tag = questions[round].correct_answer
        fourthOption.setText(questions[round].incorrect_answers[2])
        fourthOption.tag = questions[round].incorrect_answers[2]
    }
    fun fourthOptionIsTrue() {
        firstOption.setText(questions[round].incorrect_answers[0])
        firstOption.tag = questions[round].incorrect_answers[0]
        secondOption.setText(questions[round].incorrect_answers[1])
        secondOption.tag = questions[round].incorrect_answers[1]
        thirdOption.setText(questions[round].incorrect_answers[2])
        thirdOption.tag = questions[round].incorrect_answers[2]
        fourthOption.setText(questions[round].correct_answer)
        fourthOption.tag = questions[round].correct_answer
    }
    fun showTheToast (message: String) {
        val context : Context = getApplicationContext()
        val text : String = message
        val duration = Toast.LENGTH_SHORT;
        val toast: Toast = Toast.makeText(context,text,duration)
        toast.show()
    }
}
package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Reader
import com.github.kittinunf.result.*

class QuizQuestionActivtiy : AppCompatActivity(), View.OnClickListener{

    lateinit var categoryi : String
    lateinit var difficulty: String

    val questionTextView : TextView = findViewById(R.id.question)
    val questionNumberTextView : TextView = findViewById(R.id.questionNumber)
    val booleanQuestionLayout : FrameLayout = findViewById(R.id.trueFalseLayout)
    val multiChoiceLayout : FrameLayout = findViewById(R.id.multiChoiceLayout)
    val trueButton : Button = findViewById(R.id.trueButton)
    val falseButton : Button = findViewById(R.id.falseButton)
    val firstOption : Button = findViewById(R.id.firstButton)
    val secondOption : Button = findViewById(R.id.secondButton)
    val thirdOption : Button = findViewById(R.id.thirdButton)
    val fourthOption : Button = findViewById(R.id.fourthButton)

    var round = 0;
    var question : String? = null
    var correct_answer : String? = null
    var incorrect_answers : ArrayList<String> = ArrayList()
    var clickable : Boolean = false
    var isMultiple : Boolean = true
    var correctAnswerMultiple = 0
    var correctAnswerBoolean = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question_activtiy)

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
                    }

                    is Result.Success -> {
                        val(data, _) = result
                    }
                }
            }
    }

    fun startRound() {
        if (round == 10) {
            gameEnds()
        }
       // if (data.results.type.equals("boolean")) {
       //     showBooleanQuestion()
      //  } else {
            showMultipleChoiceQuestion()
      //  }
      //  clickable = true
    }

    fun showMultipleChoiceQuestion() {
        changeToMultiple()
        //Buttons und Question mit den Werten verbinden. Mit randomNumber..._... Position der richtigen Antwort ermitteln.
    }
    fun randomNumber0_1() : Int {
        return 1
    }
    fun showBooleanQuestion() {
        changeToBoolean()
        //Buttons und Question mit den Werten verbinden. Mit randomNumber..._... Position der richtigen Antwort ermitteln.
    }
    fun randomNumber0_3() : Int{
        return 1
    }
    fun evaluate(button : Button) {
        //evaluate everything
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
        //make Intent, finish and go back to MainActivity
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
}
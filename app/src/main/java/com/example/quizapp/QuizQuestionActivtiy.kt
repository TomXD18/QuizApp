package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Reader
import java.net.URL
import com.github.kittinunf.result.*

class QuizQuestionActivtiy : AppCompatActivity() {

    lateinit var categoryi : String
    lateinit var difficulty: String

    val questionTextView : TextView = findViewById(R.id.question)
    val questioNumberTextView : TextView = findViewById(R.id.questionNumber)
    //val booleanQuestionLayout : FrameLayout = findViewById(R.id.)

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

    fun showMultipleChoiceQuestion(question : String, correct_answer : String, incorrect_answers : ArrayList<String> ) {

    }

    fun showBooleanQuestion(question: String, correct_answer: String, incorrect_answers: ArrayList<String>) {

    }

    fun evaluate() {

    }

    fun changeToMultiple() {

    }

    fun changeToBoolean() {

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
}
package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class QuizQuestionActivtiy : AppCompatActivity() {

    lateinit var category : String
    lateinit var difficulty: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question_activtiy)

        val intent : Intent = intent;
        val settings: Bundle? = intent.extras
        category = settings?.getString("CATEGORY")!!
        difficulty = settings?.getString("DIFFICULTY")!!

    }
}
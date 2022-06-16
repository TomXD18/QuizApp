package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class EvaluationActivity : AppCompatActivity() , View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)

        val backButton : Button = findViewById(R.id.backButton)
        backButton.setOnClickListener(this)

        val anerkennungTextView : TextView = findViewById(R.id.anerkennung)

        val intent : Intent = intent;
        val correctAnswerNumber: Bundle? = intent.extras
        anerkennungTextView.setText("Super! Du hast " + correctAnswerNumber?.getString("NUMBER")!! + " von 10 Fragen richtig beantwortet!")
    }

    override fun onClick(p0: View?) {
        finish()
    }
}
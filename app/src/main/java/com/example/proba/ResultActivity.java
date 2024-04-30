package com.example.proba;// ResultActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

       // преддыщу активность
        int correctAnswers = getIntent().getIntExtra("CORRECT_ANSWERS", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

        // из предыдущей активности
        List<QuestionModel> wrongAnswers = (List<QuestionModel>) getIntent().getSerializableExtra("wrongAnswers");

        TextView resultTextView = findViewById(R.id.resultTextView);
        String resultMessage = "Quiz finished!\n";
        resultMessage += "Correct answers: " + correctAnswers + "/" + totalQuestions;
        resultTextView.setText(resultMessage);

        Button newTestButton = findViewById(R.id.newTestButton);
        newTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, TopicSelectionActivity.class);
                Log.d("ResultActivity", "List of wrong answers sent: " + wrongAnswers.toString());
                startActivity(intent);
                finish();
            }
        });

        Button viewWrongAnswersButton = findViewById(R.id.viewWrongAnswersButton);
        viewWrongAnswersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wrongAnswers != null) {
                    Intent intent = new Intent(ResultActivity.this, WrongAnswersActivity.class);
                    intent.putExtra("wrongAnswers", (ArrayList<QuestionModel>) wrongAnswers);
                    startActivity(intent);
                }
            }
        });

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your code for logging out
            }
        });
    }
}

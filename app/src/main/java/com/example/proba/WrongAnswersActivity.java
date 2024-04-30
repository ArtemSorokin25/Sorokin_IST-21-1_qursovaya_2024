package com.example.proba;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WrongAnswersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_answers);

        ArrayList<QuestionModel> wrongAnswersList = (ArrayList<QuestionModel>) getIntent().getSerializableExtra("wrongAnswers");

        if (wrongAnswersList == null || wrongAnswersList.isEmpty()) {
            Log.d("WrongAnswersActivity", "List of wrong answers is empty or null");

            return;
        }
        Log.d("WrongAnswersActivity", "Received wrong answers: " + wrongAnswersList.toString());


        if (wrongAnswersList != null && !wrongAnswersList.isEmpty()) {

            ListView listView = findViewById(R.id.listViewWrongAnswers);

            ArrayAdapter<QuestionModel> adapter = new ArrayAdapter<QuestionModel>(this, R.layout.list_item_wrong_answer, R.id.wrongAnswersTextView, wrongAnswersList) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView wrongAnswersTextView = view.findViewById(R.id.wrongAnswersTextView);


                    QuestionModel questionModel = getItem(position);


                    if (questionModel != null) {
                        wrongAnswersTextView.setText("Question: " + questionModel.getQuestion() + "\n" +
                                "Correct Answer: " + questionModel.getAnswer());
                    }

                    return view;
                }
            };

            listView.setAdapter(adapter);
        }

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
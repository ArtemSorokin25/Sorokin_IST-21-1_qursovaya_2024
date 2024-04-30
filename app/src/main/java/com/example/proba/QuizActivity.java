package com.example.proba;// QuizActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private List<QuestionModel> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswersCount = 0;
    private RadioGroup optionsRadioGroup;
    private TextView questionCounterTextView;
    private List<QuestionModel> wrongAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        String selectedTopic = getIntent().getStringExtra("selected_topic");
        loadQuestions(selectedTopic);

        if (questions.isEmpty()) {
            Toast.makeText(this, "Failed to load questions", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        questionCounterTextView = findViewById(R.id.questionCounterTextView);

        displayQuestion();

        wrongAnswers = new ArrayList<>();

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                } else {
                    finishQuiz();
                }
            }
        });

        Button signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(QuizActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestions(String selectedTopic) {
        String fileName = getFileName(selectedTopic);
        questions = JsonUtils.loadQuestionsFromJson(this, fileName);
    }

    private String getFileName(String selectedTopic) {
        switch (selectedTopic) {
            case "Пакет в Java":
                return "paket.json";
            case "Enum в Java":
                return "enum_java.json";
            case "Обработка исключений":
                return "exception_handling.json";
            case "Наследование и полиморфизм":
                return "inheritance_and_polymorphism.json";
            case "Лямбда выражения":
                return "lambda_expressions.json";
            case "Многопоточность":
                return "multithreading.json";
            default:
                return null;
        }
    }

    private void displayQuestion() {
        QuestionModel currentQuestion = questions.get(currentQuestionIndex);

        questionCounterTextView.setText((currentQuestionIndex + 1) + "/" + questions.size());
        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(currentQuestion.getQuestion());

        RadioButton optionARadioButton = findViewById(R.id.optionARadioButton);
        RadioButton optionBRadioButton = findViewById(R.id.optionBRadioButton);
        RadioButton optionCRadioButton = findViewById(R.id.optionCRadioButton);
        RadioButton optionDRadioButton = findViewById(R.id.optionDRadioButton);
        List<String> options = currentQuestion.getOptions();
        optionARadioButton.setText(options.get(0));
        optionBRadioButton.setText(options.get(1));
        optionCRadioButton.setText(options.get(2));
        optionDRadioButton.setText(options.get(3));

        optionsRadioGroup.clearCheck();
    }

    private void checkAnswer() {
        QuestionModel currentQuestion = questions.get(currentQuestionIndex);
        int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            String selectedAnswer = selectedRadioButton.getText().toString();

            if (selectedAnswer.equals(currentQuestion.getAnswer())) {
                correctAnswersCount++;
            } else {

                wrongAnswers.add(currentQuestion); // добавляем в списко если он неправильный
            }
        }
    }

    private void finishQuiz() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("CORRECT_ANSWERS", correctAnswersCount);
        intent.putExtra("TOTAL_QUESTIONS", questions.size());
        // Передаем список неправильных ответов в активность результатов
        intent.putExtra("wrongAnswers", (ArrayList<QuestionModel>) wrongAnswers);
        startActivity(intent);

        finish();
    }
}

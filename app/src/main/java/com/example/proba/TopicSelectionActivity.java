package com.example.proba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class TopicSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);

        String[] topics = {
                "Пакет в Java",
                "Enum в Java",
                "Обработка исключений",
                "Наследование и полиморфизм",
                "Лямбда выражения",
                "Многопоточность"
        };


        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topics);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //название темы
                String selectedTopic = topics[position];

                // Создаем Intent для перехода на QuizActivity
                Intent intent = new Intent(TopicSelectionActivity.this, QuizActivity.class);
                intent.putExtra("selected_topic", selectedTopic);
                startActivity(intent);
            }
        });
    }
}

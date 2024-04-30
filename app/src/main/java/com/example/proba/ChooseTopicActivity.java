package com.example.proba;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChooseTopicActivity extends Activity {

    private ListView topicsListView;
    private String[] topics = {"Пакет в Java", "Enum в Java", "Обработка исключений",
            "Наследование и полиморфизм", "Лямбда выражения", "Многопоточность"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_topic);

        topicsListView = findViewById(R.id.topicsListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topics);
        topicsListView.setAdapter(adapter);

        topicsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранную тему
                String selectedTopic = topics[position];

                // Передаем выбранную тему в следующую активность
                Intent intent = new Intent(ChooseTopicActivity.this, QuizActivity.class);
                intent.putExtra("selected_topic", selectedTopic);
                startActivity(intent);
            }
        });
    }
}

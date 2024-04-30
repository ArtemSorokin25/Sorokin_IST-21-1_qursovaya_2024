package com.example.proba;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static List<QuestionModel> loadQuestionsFromJson(Context context, String fileName) {
        List<QuestionModel> questions = new ArrayList<>();

        AssetManager assetManager = context.getAssets();

        try {
            InputStream inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                String question = jsonArray.getJSONObject(i).getString("question");
                List<String> options = new ArrayList<>();
                JSONArray optionsArray = jsonArray.getJSONObject(i).getJSONArray("options");
                for (int j = 0; j < optionsArray.length(); j++) {
                    options.add(optionsArray.getString(j));
                }
                String answer = jsonArray.getJSONObject(i).getString("answer");

                questions.add(new QuestionModel(question, options, answer));
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error reading JSON file: " + e.getMessage());
        }

        return questions;
    }
}

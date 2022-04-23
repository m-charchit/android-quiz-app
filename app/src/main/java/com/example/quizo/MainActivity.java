package com.example.quizo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String data = "[ { \"cat\": \"Any Category\", \"value\": \"\" }, { \"cat\": \"General Knowledge\", \"value\": \"9\" }, { \"cat\": \"Entertainment: Books\", \"value\": \"10\" }, { \"cat\": \"Entertainment: Film\", \"value\": \"11\" }, { \"cat\": \"Entertainment: Music\", \"value\": \"12\" }, { \"cat\": \"Entertainment: Musicals & Theatres\", \"value\": \"13\" }, { \"cat\": \"Entertainment: Television\", \"value\": \"14\" }, { \"cat\": \"Entertainment: Video Games\", \"value\": \"15\" }, { \"cat\": \"Entertainment: Board Games\", \"value\": \"16\" }, { \"cat\": \"Science & Nature\", \"value\": \"17\" }, { \"cat\": \"Science: Computers\", \"value\": \"18\" }, { \"cat\": \"Science: Mathematics\", \"value\": \"19\" }, { \"cat\": \"Mythology\", \"value\": \"20\" }, { \"cat\": \"Sports\", \"value\": \"21\" }, { \"cat\": \"Geography\", \"value\": \"22\" }, { \"cat\": \"History\", \"value\": \"23\" }, { \"cat\": \"Politics\", \"value\": \"24\" }, { \"cat\": \"Art\", \"value\": \"25\" }, { \"cat\": \"Celebrities\", \"value\": \"26\" }, { \"cat\": \"Animals\", \"value\": \"27\" }, { \"cat\": \"Vehicles\", \"value\": \"28\" }, { \"cat\": \"Entertainment: Comics\", \"value\": \"29\" }, { \"cat\": \"Science: Gadgets\", \"value\": \"30\" }, { \"cat\": \"Entertainment: Japanese Anime & Manga\", \"value\": \"31\" }, { \"cat\": \"Entertainment: Cartoon & Animations\", \"value\": \"32\" } ]";
    private final String[] level = {"Any Difficulty","easy","medium","hard"};
    private Spinner spinner;
    private Spinner spinner2;
    private Button button;
    private JSONArray json;

    public static final String EXTRA_JSON_STR = "com.example.quizo.extra.json_str";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        button = findViewById(R.id.button);
        List<String> categories = new ArrayList<String>();
        try {
            json = new JSONArray(data);
            for (int i = 0; i < json.length(); i++) {
                categories.add(json.getJSONObject(i).getString("cat"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner.setAdapter(dataAdapter);
      ArrayAdapter<String> dataLevelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, level);
      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner2.setAdapter(dataLevelAdapter);
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              try {

                String catId = json.getJSONObject(spinner.getSelectedItemPosition()).getString("value");
                String dif = (String) spinner2.getSelectedItem();
                if (dif.equals("Any Difficulty")){
                    dif = "";
                }
                String url = "https://opentdb.com/api.php?amount=10&type=multiple&category="+catId+"&difficulty="+ dif;
                  Log.d("url", "onClick: "+url);
                MakeRequest.NetworkThread nt = new MakeRequest.NetworkThread(url);
                Thread thread = new Thread(nt);
                thread.start();
                thread.join();
                String objString = nt.getValue();
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra(EXTRA_JSON_STR,objString);
                startActivity(intent);
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      });
    }
}
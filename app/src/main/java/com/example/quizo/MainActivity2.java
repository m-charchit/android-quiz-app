package com.example.quizo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    private int sno=0 ;
    private int score=0;
    private int totalQuestion;
    private RadioGroup radioGroup;
    public RadioButton radioButton;
    private Button next;
    private Button quitBtn;
    public TextView textView;
    private TextView showAnswer;
    private TextView scoreView;
    private TextView quesNumberView;
    private String obj_str;

    public static final String EXTRA_SCORE = "com.example.quizo.extra.score";

    String answer;
    public String createQuestion(int sno){
        try {
            JSONObject obj = new JSONObject(obj_str);
            JSONArray results = obj.getJSONArray("results");
            totalQuestion = results.length();
            JSONObject res = results.getJSONObject(sno);
            String question = StringEscapeUtils.unescapeHtml4(res.getString("question"));
            String ans = StringEscapeUtils.unescapeHtml4(res.getString("correct_answer"));
            JSONArray options = res.getJSONArray("incorrect_answers");
            int max = 3;
            int min = 0;
            int r = (int)(Math.random()*(max-min+1)+min);
            for (int i = options.length(); i > r; i--){
                options.put(i, options.get(i-1));
            }

            options.put(r, ans);
            quesNumberView.setText("Q."+(sno+1));
            textView.setText(question);
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                ((RadioButton) radioGroup.getChildAt(i)).setText(StringEscapeUtils.unescapeHtml4(options.getString(i)));
            }
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity2.this, "An error occurred", Toast.LENGTH_SHORT).show();
            return "Error";
        }
    }
    public void NewActivity(){
        Intent intent = new Intent(MainActivity2.this,QuizComplete.class);
        Log.d("Score", "NewActivity: "+ score);
        intent.putExtra(EXTRA_SCORE ,String.valueOf(score));
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.question);
        radioGroup = findViewById(R.id.radioGroup);
        next = findViewById(R.id.next);
        showAnswer = findViewById(R.id.showAnswer);
        scoreView = findViewById(R.id.scoreView);
        quitBtn = findViewById(R.id.quitBtn);
        quesNumberView = findViewById(R.id.quesNumberView);

        Intent intent = getIntent();
        obj_str = intent.getStringExtra(MainActivity.EXTRA_JSON_STR);

        answer = createQuestion(sno);
        scoreView.setText("Score : " + String.valueOf(score));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (next.getText().equals("check")) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    if (radioButton != null){

                    if (radioButton.getText().toString().equals(answer)) {
                        Toast.makeText(MainActivity2.this, "Correct!", Toast.LENGTH_SHORT).show();
                        showAnswer.setText("Your answer is correct!");
                        score++;
                    } else {
                        Toast.makeText(MainActivity2.this, "Wrong!", Toast.LENGTH_SHORT).show();
                        showAnswer.setText("Your answer is wrong\nCorrect Answer : " + answer);
                    }

                    scoreView.setText("Score : " + String.valueOf(score));
                    if (sno==totalQuestion-1) {
                        next.setText("submit");
                    } else {
                        next.setText("next");
                    }
                    } else {
                        Toast.makeText(MainActivity2.this, "Select an option", Toast.LENGTH_SHORT).show();
                    }
                } else if (next.getText().equals("submit")){
                    NewActivity();
                }
                else {
                    sno++;
                    showAnswer.setText("");
                    answer = createQuestion(sno);
                    next.setText("check");
                }
                radioGroup.clearCheck();
            }
        });
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewActivity();
            }
        });

    }
}
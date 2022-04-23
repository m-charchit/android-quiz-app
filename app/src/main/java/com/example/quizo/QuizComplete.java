package com.example.quizo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizComplete extends AppCompatActivity {
    private Boolean nameAdded=false;
    private TextView scoreViewer;
    private Button scoreBtn;
    private EditText editName;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_complete);
        scoreViewer = findViewById(R.id.scoreViewer);
        scoreBtn = findViewById(R.id.scoreBtn);
        editName = findViewById(R.id.editName);
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        String score = intent.getStringExtra(MainActivity2.EXTRA_SCORE);
        scoreViewer.setText("Your Score : " + score);

        DbHandler handler = new DbHandler(this,"playerdb",null,1);
        ArrayList<String[]> list = handler.getAllEmployee();

        handler.close();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter ad = new CustomAdapter(list);
        recyclerView.setAdapter(ad);
        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nameAdded) {
                    String playerName = editName.getText().toString();
                    if(!handler.checkIfPlayerExist(playerName) && playerName.length()>=3){
                        handler.addPlayer(new Player(playerName, Integer.parseInt(score)));
                        list.add(new String[]{playerName, score});
                        ad.notifyItemInserted(list.size() - 1);
                        Toast.makeText(QuizComplete.this, "Score saved.", Toast.LENGTH_SHORT).show();
                        nameAdded = true;
                    }
                    else if (playerName.length()<3){
                        Toast.makeText(QuizComplete.this, "Please Enter a Name of at least 3 letters", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(QuizComplete.this, "Name already used. Choose another.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(QuizComplete.this, "Your have already added your name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
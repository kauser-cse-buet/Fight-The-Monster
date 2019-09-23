package com.example.homework2_mahmmed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {
    private int result;
    private int playerLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Intent i = getIntent();
        result = i.getIntExtra("result", -2);
        playerLevel = i.getIntExtra("playerLevel", 1);


        updateView();

    }

    public void onClickPlayAgain(View view){
        Intent intent = new Intent(FinalActivity.this, MainActivity.class);
        intent.putExtra("playerLevel", playerLevel);
        startActivity(intent);

    }

    public void updateView(){
        final TextView finalResultTextView = findViewById(R.id.finalResultTextView);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (result == -1){
                    finalResultTextView.setText("YOU RAN AWAY!");
                }

                if (result == 0){
                    finalResultTextView.setText("YOU LOSE");
                }

                if (result == 1){
                    playerLevel++;
                    finalResultTextView.setText("YOU WON! PLAYER LEVEL UP! NEW LEVEL: " + playerLevel);
                }
//                handler.postDelayed(this, 10);
            }
        });
    }
}

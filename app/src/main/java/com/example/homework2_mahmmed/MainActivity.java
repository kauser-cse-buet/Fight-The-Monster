package com.example.homework2_mahmmed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int playerHealth = 0;
    private int playerStrength = 0;
    private int playerMana = 0;
    private final int MaximumPoints = 17;
    private int pointsRemaining = MaximumPoints;
    private boolean isReadyToLaunchBatlle = false;
    private int selectedDrawable = R.drawable.evil;
    private int playerLevel = 1;
    private final int LEVEL_FACTOR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        playerLevel = i.getIntExtra("playerLevel", 1);

        if (savedInstanceState != null){
            playerHealth = savedInstanceState.getInt("playerHealth");
            playerStrength = savedInstanceState.getInt("playerStrength");
            playerMana = savedInstanceState.getInt("playerMana");
            pointsRemaining = savedInstanceState.getInt("pointsRemaining");
            selectedDrawable = savedInstanceState.getInt("selectedDrawable");
        }
        else {
            pointsRemaining = MaximumPoints + LEVEL_FACTOR * playerLevel;
        }

        updateView();
    }

    public void onClickHealthDown(View view){
        if(playerHealth > 0) {
            playerHealth--;
            pointsRemaining++;
        }
    }

    public void onClickHealthUp(View view){
        if(pointsRemaining > 0){
            pointsRemaining--;
            playerHealth++;
        }
    }

    public void onClickStrengthDown(View view){
        if(playerStrength > 0) {
            playerStrength--;
            pointsRemaining++;
        }
    }

    public void onClickStrengthUp(View view){
        if(pointsRemaining > 0){
            pointsRemaining--;
            playerStrength++;
        }
    }

    public void onClickManaDown(View view){
        if(playerMana > 0) {
            playerMana--;
            pointsRemaining++;
        }
    }

    public void onClickManaUp(View view){
        if(pointsRemaining > 0){
            pointsRemaining--;
            playerMana++;
        }
    }

    public void onClickLaunchBattle(View view){
        Intent intent = new Intent(MainActivity.this, BattleActivity.class);
        intent.putExtra("playerStrength", playerStrength);
        intent.putExtra("playerMana", playerMana);
        intent.putExtra("playerHealth", playerHealth);
        intent.putExtra("playerHealth", playerHealth);
        intent.putExtra("selectedDrawable", selectedDrawable);
        intent.putExtra("playerLevel", playerLevel);

        startActivity(intent);
    }

    public void onBlackDeathClick(View view){
        selectedDrawable = R.drawable.evil;

    }

    public void onDecreatorClick(View view){
        selectedDrawable = R.drawable.green;
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("playerHealth", playerHealth);
        outState.putInt("playerStrength", playerStrength);
        outState.putInt("playerMana", playerMana);
        outState.putInt("pointsRemaining", pointsRemaining);
        outState.putInt("selectedDrawable", selectedDrawable);

    }

    public void updateView(){
        final TextView currentHealthTextView = findViewById(R.id.currentHealthTextView);
        final TextView currentStrengthTextView = findViewById(R.id.currentStrengthTextView);
        final TextView currentManaTextView = findViewById(R.id.currentManaTextView);
        final TextView pointsRemainingtextView = findViewById(R.id.pointsRemainingtextView);
        final Button launchBattleButton = findViewById(R.id.launchBattleButton);
        final RadioButton blackDeathRadioButton = findViewById(R.id.blackDeathRadioButton);
        final RadioButton decreatorRadioButton = findViewById(R.id.decreatorRadioButton);
        final ImageView monsterImageView = (ImageView) findViewById(R.id.monsterImageView);
        final TextView playerLevelTextView = findViewById(R.id.playerLevelTextView);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                playerLevelTextView.setText("Player Level: " + playerLevel);

                currentHealthTextView.setText("Current Health: " + playerHealth);
                currentStrengthTextView.setText("Current Strength: " + playerStrength);
                currentManaTextView.setText("Current Mana: " + playerMana);
                pointsRemainingtextView.setText("Points Remaining: " + pointsRemaining);

                if (pointsRemaining == 0){
                    if (launchBattleButton.isEnabled() == false){
                        launchBattleButton.setEnabled(true);
                    }
                    launchBattleButton.setText("Launch Battle!");
                }
                else{
                    if (launchBattleButton.isEnabled()){
                        launchBattleButton.setEnabled(false);
                    }
                    launchBattleButton.setText(R.string.launch_battle);
                }

                monsterImageView.setImageResource(selectedDrawable);;



                handler.postDelayed(this, 10);
            }
        });
    }
}

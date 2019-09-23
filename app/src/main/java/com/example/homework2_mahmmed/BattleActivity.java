package com.example.homework2_mahmmed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Console;

public class BattleActivity extends AppCompatActivity {
    private final int BLACKDEATH_HEALTH = 100;
    private final int BLACKDEATH_STRENGTH = 10;

    private final int DECREATOR_HEALTH = 150;
    private final int DECREATOR_STRENGTH = 15;

    private int monsterHealth = 100;
    private int monsterStrength = 10;
    private int playerHealth = 100;
    private int playerStrength = 13;
    private int playerRemainingMagicPoints = 10;
    private int monsterHealthDamage = 0;
    private final double playerMagicDamage = 0.5;

    private final int SWORDCOOLDOWNTIME = 20;
    private final int WANDCOOLDOWNTIME = 80;

    private int swordCoolDownTimeRemains = 0;
    private int wandCoolDownTimeRemains = 0;


    private final int MONSTERHITTIMEINTERVAL = 15;
    private int monsterHitTimeRemains = 0;

    private boolean isRanAway = false;
    private int selectedDrawable;
    private String monsterName;
    private int playerLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        Intent i = getIntent();
        playerStrength = i.getIntExtra("playerStrength", 0);
        playerRemainingMagicPoints = i.getIntExtra("playerMana", 0);
        playerHealth = 10 * i.getIntExtra("playerHealth", 0);
        selectedDrawable = i.getIntExtra("selectedDrawable", R.drawable.evil);
        playerLevel = i.getIntExtra("playerLevel", 1);

        if (savedInstanceState != null) {
            playerHealth = savedInstanceState.getInt("playerHealth");

            monsterHealth = savedInstanceState.getInt("monsterHealth");
            monsterStrength = savedInstanceState.getInt("monsterStrength");
            playerHealth = savedInstanceState.getInt("playerHealth");
            playerStrength = savedInstanceState.getInt("playerStrength");
            playerRemainingMagicPoints = savedInstanceState.getInt("playerRemainingMagicPoints");
            swordCoolDownTimeRemains = savedInstanceState.getInt("swordCoolDownTimeRemains");
            wandCoolDownTimeRemains = savedInstanceState.getInt("wandCoolDownTimeRemains");
            isRanAway = savedInstanceState.getBoolean("isRanAway");
        }
        else {
            if (selectedDrawable == R.drawable.evil) {
                monsterName = getApplicationContext().getResources().getString(R.string.black_death);
                monsterHealth = BLACKDEATH_HEALTH;
                monsterStrength = BLACKDEATH_STRENGTH;
            }

            if (selectedDrawable == R.drawable.green) {
                monsterName = getApplicationContext().getResources().getString(R.string.decreator);
                monsterHealth = DECREATOR_HEALTH;
                monsterStrength = DECREATOR_STRENGTH;
            }
        }

        monsterHitTimeRemains = MONSTERHITTIMEINTERVAL;

        updateView();
    }

    public void onClickSwordButton(View view){
        if (swordCoolDownTimeRemains == 0) {
            monsterHealth -= playerStrength;



            swordCoolDownTimeRemains = SWORDCOOLDOWNTIME;
        }
    }

    public void onClickWandButton(View view){
        if (wandCoolDownTimeRemains == 0) {
            if (playerRemainingMagicPoints > 0) {
                monsterHealth -= (int) Math.round(monsterHealth * playerMagicDamage);
                playerRemainingMagicPoints--;

                wandCoolDownTimeRemains = WANDCOOLDOWNTIME;
            }
        }
    }

    public void onClickRunButton(View view){
        isRanAway = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("monsterHealth", monsterHealth);
        outState.putInt("monsterStrength", monsterStrength);
        outState.putInt("playerHealth", playerHealth);
        outState.putInt("playerStrength", playerStrength);
        outState.putInt("playerRemainingMagicPoints", playerRemainingMagicPoints);
        outState.putInt("swordCoolDownTimeRemains", swordCoolDownTimeRemains);
        outState.putInt("wandCoolDownTimeRemains", wandCoolDownTimeRemains);
        outState.putBoolean("isRanAway", isRanAway);
    }

    public void updateView(){
        final TextView playerCurrentHealthTextView = findViewById(R.id.playerCurrentHealthTextView);
        final TextView playerCurrentManaTextView = findViewById(R.id.playerCurrentManaTextView);
        final TextView monsterStatusTextView = findViewById(R.id.monsterStatusTextView);
        final TextView monsterHealthTextView = findViewById(R.id.monsterHealthTextView);
        final TextView monsterHealthDamageTextView = findViewById(R.id.monsterHealthDamageTextView);
        final TextView swordCoolDownTimeTextView = findViewById(R.id.swordCoolDownTimeTextView);
        final TextView wandCoolDownTimeTextView = findViewById(R.id.wandCoolDownTimeTextView);
        final ImageView wizardImageView = findViewById(R.id.wizardImageView);
        final TextView monsterNameTextView = findViewById(R.id.monsterNameTextView);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                wizardImageView.setImageResource(selectedDrawable);



                monsterNameTextView.setText(monsterName);


                if(monsterHitTimeRemains > 0){
                    monsterHitTimeRemains--;
                }
                else{
                    playerHealth -= monsterStrength;
                    monsterHitTimeRemains = MONSTERHITTIMEINTERVAL;
                }

                if (swordCoolDownTimeRemains > 0) {
                    swordCoolDownTimeTextView.setVisibility(View.VISIBLE);
                    swordCoolDownTimeTextView.setText((int)(swordCoolDownTimeRemains / 10) + "");
                    swordCoolDownTimeRemains--;
                }
                else{
                    swordCoolDownTimeTextView.setVisibility(View.INVISIBLE);
                }

                if(wandCoolDownTimeRemains > 0) {
                    wandCoolDownTimeTextView.setVisibility(View.VISIBLE);
                    wandCoolDownTimeTextView.setText((int)(wandCoolDownTimeRemains / 10) + "");
                    wandCoolDownTimeRemains--;
                }
                else{
                    wandCoolDownTimeTextView.setVisibility(View.INVISIBLE);
                }

                playerCurrentHealthTextView.setText(playerHealth + "");
                playerCurrentManaTextView.setText(playerRemainingMagicPoints + "");


                String monsterOldHealthText = monsterHealthTextView.getText().toString();

                if (monsterOldHealthText != "") {
                    int monsterOldHealth = Integer.parseInt(monsterOldHealthText);
                    int hp = monsterHealth - monsterOldHealth;
                    if (hp != 0){
                        monsterHealthDamageTextView.setText(hp + "HP");
                        monsterHealthDamageTextView .setVisibility(View.VISIBLE);
                        CountDownTimer timer = new CountDownTimer(500, 10) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                monsterHealthDamageTextView .setVisibility(View.INVISIBLE); //(or GONE)
                            }
                        }.start();

                    }

                }

                monsterHealthTextView.setText(monsterHealth + "");

                monsterStatusTextView.setText("Monster's health is: " + monsterHealth + ", and strength is: " + monsterStrength);

                if (isRanAway){
                    Intent intent = new Intent(BattleActivity.this, FinalActivity.class);
                    intent.putExtra("result", -1);
                    intent.putExtra("playerLevel", playerLevel);
                    startActivity(intent);
                }
                else if(playerHealth <= 0) {
                    Intent intent = new Intent(BattleActivity.this, FinalActivity.class);
                    intent.putExtra("result", 0);
                    intent.putExtra("playerLevel", playerLevel);
                    startActivity(intent);
                }
                else if (monsterHealth <= 0) {
                    Intent intent = new Intent(BattleActivity.this, FinalActivity.class);
                    intent.putExtra("result", 1);
                    intent.putExtra("playerLevel", playerLevel);
                    startActivity(intent);
                }
                else {
                    handler.postDelayed(this, 100);
                }
            }
        });



    }
}

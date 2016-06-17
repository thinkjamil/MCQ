package com.hp.jam.mcq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvScore;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore=(TextView)findViewById(R.id.tvScore);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        int maxScore  = sp.getInt("MaxScore",0);
        Intent lastIntent = getIntent();
        int lastScore = lastIntent.getIntExtra("LastScore",0);
        tvScore.setText("Max Score : "+maxScore+"\n"+"Last Score : "+ lastScore);
    }
    public void startTestClick(View v){
        Boolean loggedIn = sp.getBoolean("LoginStatus",false);
        if(!loggedIn) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }else {
            Intent mcqIntent = new Intent(this, MCQActivity.class);
            startActivity(mcqIntent);
        }
    }
}

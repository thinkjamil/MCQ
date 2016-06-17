package com.hp.jam.mcq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText et_uid,et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_uid=(EditText)findViewById(R.id.login_uid);
        et_pass=(EditText)findViewById(R.id.login_password);
    }
    public void loginClick(View v){
        String user_id=et_uid.getText().toString();
        String password =et_pass.getText().toString();
        if(user_id.equals("student")&& password.equals("abc123")) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edt = sp.edit();
            edt.putBoolean("LoginStatus",true);
            edt.apply();
            Intent mcqIntent = new Intent(this, MCQActivity.class);
            startActivity(mcqIntent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Login Failed, try again", Toast.LENGTH_SHORT).show();
        }
    }
}

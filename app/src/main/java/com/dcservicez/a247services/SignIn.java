package com.dcservicez.a247services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void redirect_signUp(View view) {
        Intent intent=new Intent(SignIn.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

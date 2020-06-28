package com.codestar.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.leo.simplearcloader.SimpleArcLoader;

public class MainActivity extends AppCompatActivity {

    SimpleArcLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loader=findViewById(R.id.loader);

        loader.setVisibility(View.VISIBLE);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        },4000);
    }
}
package com.shivanigb.bmicalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash3Activity extends AppCompatActivity {
    TextView tvWelcome;
    ImageView ivAndroidLogo;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash3);

        tvWelcome=(TextView)findViewById(R.id.tvWelcome);
        ivAndroidLogo=(ImageView)findViewById(R.id.ivAndroidLogo);
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.a1);
        ivAndroidLogo.startAnimation(animation);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(4000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Intent i=new Intent(Splash3Activity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        }).start();


    }

}

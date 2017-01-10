package com.example.mitjag.dnevnik_rokov;

/**
 * Created by mitja on 10.1.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


public class Animation_dnevnik extends AppCompatActivity implements Animation.AnimationListener{

    TextView naslov;
    ImageView vozicek,twix,snicker,chio,cocacola,koledar;
    // Animation
    Animation animationVozicek,animationTwix, animationsnicker, animationchio, animationcocacola,animationkoledar,animationtwixkoledar,animationsnickerkoledar,animationcocakoledar,animationchiokoledar;
    Animation animationtext,animodpelji;
    boolean stanje=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_animation);
        vozicek=(ImageView)findViewById(R.id.vozicek);
        twix=(ImageView)findViewById(R.id.twix);
        snicker=(ImageView)findViewById(R.id.snicker);
        chio=(ImageView)findViewById(R.id.chio) ;
        cocacola=(ImageView)findViewById(R.id.coca);
        koledar=(ImageView)findViewById(R.id.koledar);
        naslov=(TextView)findViewById(R.id.txt);

        //load animation
        animationVozicek= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation_vozicek);
        animationTwix=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation_twix);
        animationsnicker=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation_snicker);
        animationchio=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation_chio);
        animationcocacola=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation_cocacola);
        animationkoledar=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        animationtwixkoledar=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_twix_koledar);
        animationsnickerkoledar=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_snic_koledar);
        animationcocakoledar=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_coca_koledar);
        animationchiokoledar=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_chio_koledar);
        animationtext=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_text);
        animodpelji=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_odpelji);

        vozicek.setImageResource(R.drawable.vozicek);
        twix.setImageResource(R.drawable.twix);
        snicker.setImageResource(R.drawable.snicker);
        chio.setImageResource(R.drawable.chio);
        cocacola.setImageResource(R.drawable.cocacola1);
        koledar.setImageResource(R.drawable.koledar1);
        // set animation listener

        animationVozicek.setAnimationListener(this);
        animationTwix.setAnimationListener(this);
        animationsnicker.setAnimationListener(this);
        animationchio.setAnimationListener(this);
        animationcocacola.setAnimationListener(this);
        animationkoledar.setAnimationListener(this);
        animationtwixkoledar.setAnimationListener(this);
        animationsnickerkoledar.setAnimationListener(this);
        animationcocakoledar.setAnimationListener(this);
        animationchiokoledar.setAnimationListener(this);
        animationtext.setAnimationListener(this);
        animodpelji.setAnimationListener(this);
        delay(1);



    }
    public int milliseconds;
    public void delay(int seconds){
        milliseconds = seconds * 500;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(stanje)
                            zacniAnimacijo();                 //add your code here
                        else {
                            vozicek.startAnimation(animodpelji);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(Animation_dnevnik.this, MainActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }, 2000);
                        }
                    }
                }, milliseconds);
            }
        });
    }
    @Override
    public void onAnimationEnd(Animation animation) {
        if(stanje==true) {
            twix.startAnimation(animationtwixkoledar);
            snicker.startAnimation(animationsnickerkoledar);
            cocacola.startAnimation(animationcocakoledar);
            chio.startAnimation(animationchiokoledar);
            stanje=false;
            naslov.setVisibility(View.VISIBLE);
            naslov.startAnimation(animationtext);
            delay(3);
        }



    }

    @Override
    public void onAnimationRepeat(Animation animation) {


    }


    public void zacniAnimacijo(){
        stanje=true;
        vozicek.setVisibility(View.VISIBLE);
        vozicek.startAnimation(animationVozicek);
        twix.setVisibility(View.VISIBLE);
        twix.startAnimation(animationTwix);
        snicker.setVisibility(View.VISIBLE);
        snicker.startAnimation(animationsnicker);
        chio.setVisibility(View.VISIBLE);
        chio.startAnimation(animationchio);
        cocacola.setVisibility(View.VISIBLE);
        cocacola.startAnimation(animationcocacola);

        koledar.setVisibility(View.VISIBLE);
        koledar.startAnimation(animationkoledar);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

}


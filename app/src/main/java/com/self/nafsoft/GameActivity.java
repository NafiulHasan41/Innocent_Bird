package com.self.nafsoft;

import static java.lang.Math.abs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class GameActivity extends AppCompatActivity {

    private LottieAnimationView bird,enemy1,enemy2,enemy3,enemy4,coin1,coin2,coin3,right1,right2,right3;
    private TextView textViewScore,textViewStart;
    private ConstraintLayout constraintLayoutGame;

    //for bird flying
    int birdX,enemy1x,enemy2x,enemy3x,enemy4x,coin1x,coin2x,coin3x;
    int birdY,enemy1y,enemy2y,enemy3y,enemy4y,coin1y,coin2y,coin3y;

    //dimension
    int screenWidth;
    int screenHeight;

    //score and speed parameter
    int scoreCurrent=0;

    //remaining life
    int right=3;


    private boolean gameStart1 = false;


    private  boolean touchControl = false;
    private boolean beginControl = false;

    private Runnable runnable,runnable1;
    private Handler handler,handler1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //lottie
        bird = findViewById(R.id.bird);
        enemy1 = findViewById(R.id.enemy1);
        enemy2 = findViewById(R.id.enemy2);
        enemy3 = findViewById(R.id.enemy3);
        enemy4 = findViewById(R.id.enemy4);
        coin1 = findViewById(R.id.coin1);
        coin2 = findViewById(R.id.coin2);
        coin3 = findViewById(R.id.coin3);
        right1 = findViewById(R.id.right1);
        right2 = findViewById(R.id.right2);
        right3 = findViewById(R.id.right3);




        //Textview

        textViewScore = findViewById(R.id.userScore);
        textViewStart = findViewById(R.id.textViewStartingInfo);

        // constrainLayout

        constraintLayoutGame = findViewById(R.id.constrainLayoutGame);

        constraintLayoutGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                textViewStart.setVisibility(View.INVISIBLE);
                gameStart1=true;
                if(!beginControl)
                {
                    beginControl=true;

                    screenWidth =(int) constraintLayoutGame.getWidth();
                    screenHeight = (int) constraintLayoutGame.getHeight();




                    birdX = (int) bird.getX();
                    birdY = (int) bird.getY();





                    handler = new Handler();
                    runnable = () -> {

                        moveBird();
                        enemyMovement();
                       collision();


                    };

                    handler.post(runnable);
                }
                else
                {
                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        touchControl=true;

                    }
                    if(event.getAction() == MotionEvent.ACTION_UP)
                    {
                        touchControl=false;
                    }

                }

                return true;
            }
        });




    }
    public void moveBird()
    {
        if(touchControl)
        {
            birdY = birdY - (screenHeight/50);
        }
        else
        {
            birdY = birdY + (screenHeight/50);
        }

        if(birdY <=0 )
        {
            birdY = 0 ;
        }
        else if (birdY >= (screenHeight - bird.getHeight()))
        {
            birdY = (screenHeight - bird.getHeight());
        }


        bird.setY(birdY);


    }

    public void enemyMovement()
    {


            ///start
            enemy1.setVisibility(View.VISIBLE);
            enemy2.setVisibility(View.VISIBLE);
            enemy3.setVisibility(View.VISIBLE);
            enemy4.setVisibility(View.VISIBLE);
            coin1.setVisibility(View.VISIBLE);
            coin2.setVisibility(View.VISIBLE);
            coin3.setVisibility(View.VISIBLE);
            if(scoreCurrent<=100)
            {
                //start

                enemy1x = enemy1x - ((screenWidth/160));



                if(enemy1x <0)
                {
                    enemy1x = screenWidth+200;
                    enemy1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy1y <=0 )
                    {
                        enemy1y = 0 ;
                    }
                    else if (enemy1y >= (screenHeight - enemy1.getHeight()))
                    {
                        enemy1y = (screenHeight - enemy1.getHeight());
                    }
                }
                enemy1.setX(enemy1x);
                enemy1.setY(enemy1y);

                //end for every character

                //start

                enemy2x = enemy2x - ((screenWidth/165));



                if(enemy2x <0)
                {
                    enemy2x = screenWidth+800;
                    enemy2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy2y <=0 )
                    {
                        enemy2y = 0 ;
                    }
                    else if (enemy2y >= (screenHeight - enemy2.getHeight()))
                    {
                        enemy2y = (screenHeight - enemy2.getHeight());
                    }
                }
                enemy2.setX(enemy2x);
                enemy2.setY(enemy2y);

                //end for every character

                //start

                enemy3x = enemy3x - ((screenWidth/154));



                if(enemy3x <0)
                {
                    enemy3x = screenWidth+1400;
                    enemy3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy3y <=0 )
                    {
                        enemy3y = 0 ;
                    }
                    else if (enemy3y >= (screenHeight - enemy3.getHeight()))
                    {
                        enemy3y = (screenHeight - enemy3.getHeight());
                    }
                }
                enemy3.setX(enemy3x);
                enemy3.setY(enemy3y);

                //end for every character

                //start

                enemy4x = enemy4x - (screenWidth/152);



                if(enemy4x <0)
                {
                    enemy4x = screenWidth+2000;
                    enemy4y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy4y <=0 )
                    {
                        enemy4y = 0 ;
                    }
                    else if (enemy4y >= (screenHeight - enemy4.getHeight()))
                    {
                        enemy4y = (screenHeight - enemy4.getHeight());
                    }
                }
                enemy4.setX(enemy4x);
                enemy4.setY(enemy4y);


                //end for every character

                //start

                coin1x = coin1x - (screenWidth/156);



                if(coin1x <0)
                {
                    coin1x = screenWidth+200;
                    coin1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin1y <=0 )
                    {
                        coin1y = 0 ;
                    }
                    else if (coin1y >= (screenHeight - coin1.getHeight()))
                    {
                        coin1y = (screenHeight - coin1.getHeight());
                    }
                }
                coin1.setX(coin1x);
                coin1.setY(coin1y);


                //end for every character

                //start

                coin2x = coin2x - (screenWidth/163);



                if(coin2x <0)
                {
                    coin2x = screenWidth+600;
                    coin2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin2y <=0 )
                    {
                        coin2y = 0 ;
                    }
                    else if (coin2y >= (screenHeight - coin2.getHeight()))
                    {
                        coin2y = (screenHeight - coin2.getHeight());
                    }
                }
                coin2.setX(coin2x);
                coin2.setY(coin2y);


                //end for every character

                //start

                coin3x = coin3x - (screenWidth/153);



                if(coin3x <0)
                {
                    coin3x = screenWidth+1000;
                    coin3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin3y <=0 )
                    {
                        coin3y = 0 ;
                    }
                    else if (coin3y >= (screenHeight - coin3.getHeight()))
                    {
                        coin3y = (screenHeight - coin3.getHeight());
                    }
                }
                coin3.setX(coin3x);
                coin3.setY(coin3y);


                //end for every character

            }
            else if(scoreCurrent>100 && scoreCurrent<200)
            {

                //start

                enemy1x = enemy1x - ((screenWidth/155));



                if(enemy1x <0)
                {
                    enemy1x = screenWidth+200;
                    enemy1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy1y <=0 )
                    {
                        enemy1y = 0 ;
                    }
                    else if (enemy1y >= (screenHeight - enemy1.getHeight()))
                    {
                        enemy1y = (screenHeight - enemy1.getHeight());
                    }
                }
                enemy1.setX(enemy1x);
                enemy1.setY(enemy1y);

                //end for every character

                //start

                enemy2x = enemy2x - ((screenWidth/160));



                if(enemy2x <0)
                {
                    enemy2x = screenWidth+200;
                    enemy2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy2y <=0 )
                    {
                        enemy2y = 0 ;
                    }
                    else if (enemy2y >= (screenHeight - enemy2.getHeight()))
                    {
                        enemy2y = (screenHeight - enemy2.getHeight());
                    }
                }
                enemy2.setX(enemy2x);
                enemy2.setY(enemy2y);

                //end for every character

                //start

                enemy3x = enemy3x - ((screenWidth/151));



                if(enemy3x <0)
                {
                    enemy3x = screenWidth+200;
                    enemy3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy3y <=0 )
                    {
                        enemy3y = 0 ;
                    }
                    else if (enemy3y >= (screenHeight - enemy3.getHeight()))
                    {
                        enemy3y = (screenHeight - enemy3.getHeight());
                    }
                }
                enemy3.setX(enemy3x);
                enemy3.setY(enemy3y);

                //end for every character

                //start

                enemy4x = enemy4x - ((screenWidth/148));



                if(enemy4x <0)
                {
                    enemy4x = screenWidth+200;
                    enemy4y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy4y <=0 )
                    {
                        enemy4y = 0 ;
                    }
                    else if (enemy4y >= (screenHeight - enemy4.getHeight()))
                    {
                        enemy4y = (screenHeight - enemy4.getHeight());
                    }
                }
                enemy4.setX(enemy4x);
                enemy4.setY(enemy4y);


                //end for every character

                //start

                coin1x = coin1x - ((screenWidth/150));



                if(coin1x <0)
                {
                    coin1x = screenWidth+200;
                    coin1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin1y <=0 )
                    {
                        coin1y = 0 ;
                    }
                    else if (coin1y >= (screenHeight - coin1.getHeight()))
                    {
                        coin1y = (screenHeight - coin1.getHeight());
                    }
                }
                coin1.setX(coin1x);
                coin1.setY(coin1y);


                //end for every character

                //start

                coin2x = coin2x - ((screenWidth/157));



                if(coin2x <0)
                {
                    coin2x = screenWidth+200;
                    coin2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin2y <=0 )
                    {
                        coin2y = 0 ;
                    }
                    else if (coin2y >= (screenHeight - coin2.getHeight()))
                    {
                        coin2y = (screenHeight - coin2.getHeight());
                    }
                }
                coin2.setX(coin2x);
                coin2.setY(coin2y);


                //end for every character

                //start

                coin3x = coin3x - ((screenWidth/149));



                if(coin3x <0)
                {
                    coin3x = screenWidth+200;
                    coin3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin3y <=0 )
                    {
                        coin3y = 0 ;
                    }
                    else if (coin3y >= (screenHeight - coin3.getHeight()))
                    {
                        coin3y = (screenHeight - coin3.getHeight());
                    }
                }
                coin3.setX(coin3x);
                coin3.setY(coin3y);


                //end for every character

            }
            else if (scoreCurrent>=200 && scoreCurrent<400) {


                //start

                enemy1x = enemy1x - ((screenWidth/150));



                if(enemy1x <0)
                {
                    enemy1x = screenWidth+200;
                    enemy1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy1y <=0 )
                    {
                        enemy1y = 0 ;
                    }
                    else if (enemy1y >= (screenHeight - enemy1.getHeight()))
                    {
                        enemy1y = (screenHeight - enemy1.getHeight());
                    }
                }
                enemy1.setX(enemy1x);
                enemy1.setY(enemy1y);

                //end for every character

                //start

                enemy2x = enemy2x - ((screenWidth/155));



                if(enemy2x <0)
                {
                    enemy2x = screenWidth+200;
                    enemy2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy2y <=0 )
                    {
                        enemy2y = 0 ;
                    }
                    else if (enemy2y >= (screenHeight - enemy2.getHeight()))
                    {
                        enemy2y = (screenHeight - enemy2.getHeight());
                    }
                }
                enemy2.setX(enemy2x);
                enemy2.setY(enemy2y);

                //end for every character

                //start

                enemy3x = enemy3x - ((screenWidth/146));



                if(enemy3x <0)
                {
                    enemy3x = screenWidth+200;
                    enemy3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy3y <=0 )
                    {
                        enemy3y = 0 ;
                    }
                    else if (enemy3y >= (screenHeight - enemy3.getHeight()))
                    {
                        enemy3y = (screenHeight - enemy3.getHeight());
                    }
                }
                enemy3.setX(enemy3x);
                enemy3.setY(enemy3y);

                //end for every character

                //start

                enemy4x = enemy4x - ((screenWidth/143));



                if(enemy4x <0)
                {
                    enemy4x = screenWidth+200;
                    enemy4y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy4y <=0 )
                    {
                        enemy4y = 0 ;
                    }
                    else if (enemy4y >= (screenHeight - enemy4.getHeight()))
                    {
                        enemy4y = (screenHeight - enemy4.getHeight());
                    }
                }
                enemy4.setX(enemy4x);
                enemy4.setY(enemy4y);


                //end for every character

                //start

                coin1x = coin1x - ((screenWidth/145));



                if(coin1x <0)
                {
                    coin1x = screenWidth+200;
                    coin1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin1y <=0 )
                    {
                        coin1y = 0 ;
                    }
                    else if (coin1y >= (screenHeight - coin1.getHeight()))
                    {
                        coin1y = (screenHeight - coin1.getHeight());
                    }
                }
                coin1.setX(coin1x);
                coin1.setY(coin1y);


                //end for every character

                //start

                coin2x = coin2x - ((screenWidth/153));



                if(coin2x <0)
                {
                    coin2x = screenWidth+200;
                    coin2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin2y <=0 )
                    {
                        coin2y = 0 ;
                    }
                    else if (coin2y >= (screenHeight - coin2.getHeight()))
                    {
                        coin2y = (screenHeight - coin2.getHeight());
                    }
                }
                coin2.setX(coin2x);
                coin2.setY(coin2y);


                //end for every character

                //start

                coin3x = coin3x - ((screenWidth/144));



                if(coin3x <0)
                {
                    coin3x = screenWidth+200;
                    coin3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin3y <=0 )
                    {
                        coin3y = 0 ;
                    }
                    else if (coin3y >= (screenHeight - coin3.getHeight()))
                    {
                        coin3y = (screenHeight - coin3.getHeight());
                    }
                }
                coin3.setX(coin3x);
                coin3.setY(coin3y);


                //end for every character

            }
            else if (scoreCurrent>=400 && scoreCurrent<600) {


                //start

                enemy1x = enemy1x - ((screenWidth/145));



                if(enemy1x <0)
                {
                    enemy1x = screenWidth+200;
                    enemy1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy1y <=0 )
                    {
                        enemy1y = 0 ;
                    }
                    else if (enemy1y >= (screenHeight - enemy1.getHeight()))
                    {
                        enemy1y = (screenHeight - enemy1.getHeight());
                    }
                }
                enemy1.setX(enemy1x);
                enemy1.setY(enemy1y);

                //end for every character

                //start

                enemy2x = enemy2x - ((screenWidth/150));



                if(enemy2x <0)
                {
                    enemy2x = screenWidth+200;
                    enemy2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy2y <=0 )
                    {
                        enemy2y = 0 ;
                    }
                    else if (enemy2y >= (screenHeight - enemy2.getHeight()))
                    {
                        enemy2y = (screenHeight - enemy2.getHeight());
                    }
                }
                enemy2.setX(enemy2x);
                enemy2.setY(enemy2y);

                //end for every character

                //start

                enemy3x = enemy3x - ((screenWidth/140));



                if(enemy3x <0)
                {
                    enemy3x = screenWidth+200;
                    enemy3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy3y <=0 )
                    {
                        enemy3y = 0 ;
                    }
                    else if (enemy3y >= (screenHeight - enemy3.getHeight()))
                    {
                        enemy3y = (screenHeight - enemy3.getHeight());
                    }
                }
                enemy3.setX(enemy3x);
                enemy3.setY(enemy3y);

                //end for every character

                //start

                enemy4x = enemy4x - ((screenWidth/138));



                if(enemy4x <0)
                {
                    enemy4x = screenWidth+200;
                    enemy4y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy4y <=0 )
                    {
                        enemy4y = 0 ;
                    }
                    else if (enemy4y >= (screenHeight - enemy4.getHeight()))
                    {
                        enemy4y = (screenHeight - enemy4.getHeight());
                    }
                }
                enemy4.setX(enemy4x);
                enemy4.setY(enemy4y);


                //end for every character

                //start

                coin1x = coin1x - ((screenWidth/142));



                if(coin1x <0)
                {
                    coin1x = screenWidth+200;
                    coin1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin1y <=0 )
                    {
                        coin1y = 0 ;
                    }
                    else if (coin1y >= (screenHeight - coin1.getHeight()))
                    {
                        coin1y = (screenHeight - coin1.getHeight());
                    }
                }
                coin1.setX(coin1x);
                coin1.setY(coin1y);


                //end for every character

                //start

                coin2x = coin2x - ((screenWidth/148));



                if(coin2x <0)
                {
                    coin2x = screenWidth+200;
                    coin2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin2y <=0 )
                    {
                        coin2y = 0 ;
                    }
                    else if (coin2y >= (screenHeight - coin2.getHeight()))
                    {
                        coin2y = (screenHeight - coin2.getHeight());
                    }
                }
                coin2.setX(coin2x);
                coin2.setY(coin2y);


                //end for every character

                //start

                coin3x = coin3x - ((screenWidth/139));



                if(coin3x <0)
                {
                    coin3x = screenWidth+200;
                    coin3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin3y <=0 )
                    {
                        coin3y = 0 ;
                    }
                    else if (coin3y >= (screenHeight - coin3.getHeight()))
                    {
                        coin3y = (screenHeight - coin3.getHeight());
                    }
                }
                coin3.setX(coin3x);
                coin3.setY(coin3y);


                //end for every character
            }
            else if (scoreCurrent>=600 && scoreCurrent<800) {


                //start

                enemy1x = enemy1x - ((screenWidth/135));



                if(enemy1x <0)
                {
                    enemy1x = screenWidth+200;
                    enemy1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy1y <=0 )
                    {
                        enemy1y = 0 ;
                    }
                    else if (enemy1y >= (screenHeight - enemy1.getHeight()))
                    {
                        enemy1y = (screenHeight - enemy1.getHeight());
                    }
                }
                enemy1.setX(enemy1x);
                enemy1.setY(enemy1y);

                //end for every character

                //start

                enemy2x = enemy2x - ((screenWidth/140));



                if(enemy2x <0)
                {
                    enemy2x = screenWidth+200;
                    enemy2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy2y <=0 )
                    {
                        enemy2y = 0 ;
                    }
                    else if (enemy2y >= (screenHeight - enemy2.getHeight()))
                    {
                        enemy2y = (screenHeight - enemy2.getHeight());
                    }
                }
                enemy2.setX(enemy2x);
                enemy2.setY(enemy2y);

                //end for every character

                //start

                enemy3x = enemy3x - ((screenWidth/131));



                if(enemy3x <0)
                {
                    enemy3x = screenWidth+200;
                    enemy3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy3y <=0 )
                    {
                        enemy3y = 0 ;
                    }
                    else if (enemy3y >= (screenHeight - enemy3.getHeight()))
                    {
                        enemy3y = (screenHeight - enemy3.getHeight());
                    }
                }
                enemy3.setX(enemy3x);
                enemy3.setY(enemy3y);

                //end for every character

                //start

                enemy4x = enemy4x - ((screenWidth/128));



                if(enemy4x <0)
                {
                    enemy4x = screenWidth+200;
                    enemy4y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy4y <=0 )
                    {
                        enemy4y = 0 ;
                    }
                    else if (enemy4y >= (screenHeight - enemy4.getHeight()))
                    {
                        enemy4y = (screenHeight - enemy4.getHeight());
                    }
                }
                enemy4.setX(enemy4x);
                enemy4.setY(enemy4y);


                //end for every character

                //start

                coin1x = coin1x - ((screenWidth/130));



                if(coin1x <0)
                {
                    coin1x = screenWidth+200;
                    coin1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin1y <=0 )
                    {
                        coin1y = 0 ;
                    }
                    else if (coin1y >= (screenHeight - coin1.getHeight()))
                    {
                        coin1y = (screenHeight - coin1.getHeight());
                    }
                }
                coin1.setX(coin1x);
                coin1.setY(coin1y);


                //end for every character

                //start

                coin2x = coin2x - ((screenWidth/137));



                if(coin2x <0)
                {
                    coin2x = screenWidth+200;
                    coin2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin2y <=0 )
                    {
                        coin2y = 0 ;
                    }
                    else if (coin2y >= (screenHeight - coin2.getHeight()))
                    {
                        coin2y = (screenHeight - coin2.getHeight());
                    }
                }
                coin2.setX(coin2x);
                coin2.setY(coin2y);


                //end for every character

                //start

                coin3x = coin3x - ((screenWidth/129));



                if(coin3x <0)
                {
                    coin3x = screenWidth+200;
                    coin3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin3y <=0 )
                    {
                        coin3y = 0 ;
                    }
                    else if (coin3y >= (screenHeight - coin3.getHeight()))
                    {
                        coin3y = (screenHeight - coin3.getHeight());
                    }
                }
                coin3.setX(coin3x);
                coin3.setY(coin3y);


                //end for every character
            }
            else if (scoreCurrent>=800 && scoreCurrent<1000) {


                //start

                enemy1x = enemy1x - ((screenWidth/125));



                if(enemy1x <0)
                {
                    enemy1x = screenWidth+200;
                    enemy1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy1y <=0 )
                    {
                        enemy1y = 0 ;
                    }
                    else if (enemy1y >= (screenHeight - enemy1.getHeight()))
                    {
                        enemy1y = (screenHeight - enemy1.getHeight());
                    }
                }
                enemy1.setX(enemy1x);
                enemy1.setY(enemy1y);

                //end for every character

                //start

                enemy2x = enemy2x - ((screenWidth/130));



                if(enemy2x <0)
                {
                    enemy2x = screenWidth+200;
                    enemy2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy2y <=0 )
                    {
                        enemy2y = 0 ;
                    }
                    else if (enemy2y >= (screenHeight - enemy2.getHeight()))
                    {
                        enemy2y = (screenHeight - enemy2.getHeight());
                    }
                }
                enemy2.setX(enemy2x);
                enemy2.setY(enemy2y);

                //end for every character

                //start

                enemy3x = enemy3x - ((screenWidth/121));



                if(enemy3x <0)
                {
                    enemy3x = screenWidth+200;
                    enemy3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy3y <=0 )
                    {
                        enemy3y = 0 ;
                    }
                    else if (enemy3y >= (screenHeight - enemy3.getHeight()))
                    {
                        enemy3y = (screenHeight - enemy3.getHeight());
                    }
                }
                enemy3.setX(enemy3x);
                enemy3.setY(enemy3y);

                //end for every character

                //start

                enemy4x = enemy4x - ((screenWidth/118));



                if(enemy4x <0)
                {
                    enemy4x = screenWidth+200;
                    enemy4y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy4y <=0 )
                    {
                        enemy4y = 0 ;
                    }
                    else if (enemy4y >= (screenHeight - enemy4.getHeight()))
                    {
                        enemy4y = (screenHeight - enemy4.getHeight());
                    }
                }
                enemy4.setX(enemy4x);
                enemy4.setY(enemy4y);


                //end for every character

                //start

                coin1x = coin1x - ((screenWidth/120));



                if(coin1x <0)
                {
                    coin1x = screenWidth+200;
                    coin1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin1y <=0 )
                    {
                        coin1y = 0 ;
                    }
                    else if (coin1y >= (screenHeight - coin1.getHeight()))
                    {
                        coin1y = (screenHeight - coin1.getHeight());
                    }
                }
                coin1.setX(coin1x);
                coin1.setY(coin1y);


                //end for every character

                //start

                coin2x = coin2x - ((screenWidth/128));



                if(coin2x <0)
                {
                    coin2x = screenWidth+200;
                    coin2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin2y <=0 )
                    {
                        coin2y = 0 ;
                    }
                    else if (coin2y >= (screenHeight - coin2.getHeight()))
                    {
                        coin2y = (screenHeight - coin2.getHeight());
                    }
                }
                coin2.setX(coin2x);
                coin2.setY(coin2y);


                //end for every character

                //start

                coin3x = coin3x - ((screenWidth/119));



                if(coin3x <0)
                {
                    coin3x = screenWidth+200;
                    coin3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin3y <=0 )
                    {
                        coin3y = 0 ;
                    }
                    else if (coin3y >= (screenHeight - coin3.getHeight()))
                    {
                        coin3y = (screenHeight - coin3.getHeight());
                    }
                }
                coin3.setX(coin3x);
                coin3.setY(coin3y);


                //end for every character
            }
            else if (scoreCurrent>=1000 ) {


                //start

                enemy1x = enemy1x - ((screenWidth/115));



                if(enemy1x <0)
                {
                    enemy1x = screenWidth+200;
                    enemy1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy1y <=0 )
                    {
                        enemy1y = 0 ;
                    }
                    else if (enemy1y >= (screenHeight - enemy1.getHeight()))
                    {
                        enemy1y = (screenHeight - enemy1.getHeight());
                    }
                }
                enemy1.setX(enemy1x);
                enemy1.setY(enemy1y);

                //end for every character

                //start

                enemy2x = enemy2x - ((screenWidth/120));



                if(enemy2x <0)
                {
                    enemy2x = screenWidth+200;
                    enemy2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy2y <=0 )
                    {
                        enemy2y = 0 ;
                    }
                    else if (enemy2y >= (screenHeight - enemy2.getHeight()))
                    {
                        enemy2y = (screenHeight - enemy2.getHeight());
                    }
                }
                enemy2.setX(enemy2x);
                enemy2.setY(enemy2y);

                //end for every character

                //start

                enemy3x = enemy3x - ((screenWidth/110));



                if(enemy3x <0)
                {
                    enemy3x = screenWidth+200;
                    enemy3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy3y <=0 )
                    {
                        enemy3y = 0 ;
                    }
                    else if (enemy3y >= (screenHeight - enemy3.getHeight()))
                    {
                        enemy3y = (screenHeight - enemy3.getHeight());
                    }
                }
                enemy3.setX(enemy3x);
                enemy3.setY(enemy3y);

                //end for every character

                //start

                enemy4x = enemy4x - ((screenWidth/108));



                if(enemy4x <0)
                {
                    enemy4x = screenWidth+200;
                    enemy4y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(enemy4y <=0 )
                    {
                        enemy4y = 0 ;
                    }
                    else if (enemy4y >= (screenHeight - enemy4.getHeight()))
                    {
                        enemy4y = (screenHeight - enemy4.getHeight());
                    }
                }
                enemy4.setX(enemy4x);
                enemy4.setY(enemy4y);


                //end for every character

                //start

                coin1x = coin1x - ((screenWidth/109));



                if(coin1x <0)
                {
                    coin1x = screenWidth+200;
                    coin1y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin1y <=0 )
                    {
                        coin1y = 0 ;
                    }
                    else if (coin1y >= (screenHeight - coin1.getHeight()))
                    {
                        coin1y = (screenHeight - coin1.getHeight());
                    }
                }
                coin1.setX(coin1x);
                coin1.setY(coin1y);


                //end for every character

                //start

                coin2x = coin2x - ((screenWidth/118));



                if(coin2x <0)
                {
                    coin2x = screenWidth+200;
                    coin2y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin2y <=0 )
                    {
                        coin2y = 0 ;
                    }
                    else if (coin2y >= (screenHeight - coin2.getHeight()))
                    {
                        coin2y = (screenHeight - coin2.getHeight());
                    }
                }
                coin2.setX(coin2x);
                coin2.setY(coin2y);


                //end for every character

                //start

                coin3x = coin3x - ((screenWidth/109));



                if(coin3x <0)
                {
                    coin3x = screenWidth+200;
                    coin3y =(int) Math.floor(Math.random() * screenHeight );

                    //corner case
                    if(coin3y <=0 )
                    {
                        coin3y = 0 ;
                    }
                    else if (coin3y >= (screenHeight - coin3.getHeight()))
                    {
                        coin3y = (screenHeight - coin3.getHeight());
                    }
                }
                coin3.setX(coin3x);
                coin3.setY(coin3y);


                //end for every character
            }












        

    }

    public void collision()
    {

        {


            //code start of collision

            int centerEnemy1x = enemy1x + (enemy1.getWidth() / 2);
            int centerEnemy1y = enemy1y + (enemy1.getHeight() /2);

            if(centerEnemy1x >= birdX && centerEnemy1x <= (birdX + bird.getWidth())
                    && centerEnemy1y >= birdY && centerEnemy1y <= (birdY + bird.getHeight()))
            {
                enemy1x = screenWidth + 210;
                right--;

            }



            //code end of collision

            //code start of collision

            int centerEnemy2x = enemy2x + enemy2.getWidth() / 2;
            int centerEnemy2y = enemy2y + enemy2.getHeight() /2;

            if(centerEnemy2x >= birdX && centerEnemy2x <=(birdX + bird.getWidth()) && centerEnemy2y >= birdY && centerEnemy2y <= (birdY + bird.getHeight()))
            {
                enemy2x = screenWidth+220;
                right--;
            }

            //code end of collision


            //code start of collision

            int centerEnemy3x = enemy3x + enemy3.getWidth() / 2;
            int centerEnemy3y = enemy3y + enemy3.getHeight() /2;

            if(centerEnemy3x >= birdX && centerEnemy3x <=(birdX + bird.getWidth()) && centerEnemy3y >= birdY && centerEnemy3y <= (birdY + bird.getHeight()))
            {
                enemy3x = screenWidth+230;
                right--;
            }

            //code end of collision

            //code start of collision

            int centerEnemy4x = enemy4x + enemy4.getWidth() / 2;
            int centerEnemy4y = enemy4y + enemy4.getHeight() /2;

            if(centerEnemy4x >= birdX && centerEnemy4x <=(birdX + bird.getWidth()) && centerEnemy4y >= birdY && centerEnemy4y <= (birdY + bird.getHeight()))
            {
                enemy4x = screenWidth+240;
                right--;
            }

            //code end of collision

            //code start of collision

            int centerCoin1x = coin1x + coin1.getWidth() / 2;
            int centerCoin1y = coin1y + coin1.getHeight() /2;

            if(centerCoin1x >= birdX && centerCoin1x <=(birdX + bird.getWidth()) && centerCoin1y >= birdY && centerCoin1y <= (birdY + bird.getHeight()))
            {
                coin1x = screenWidth+250;

                scoreCurrent = scoreCurrent + 10 ;
                textViewScore.setText(""+scoreCurrent);

            }

            //code end of collision

            //code start of collision

            int centerCoin2x = coin2x + coin2.getWidth() / 2;
            int centerCoin2y = coin2y + coin2.getHeight() /2;

            if(centerCoin2x >= birdX && centerCoin2x <=(birdX + bird.getWidth()) && centerCoin2y >= birdY && centerCoin2y <= (birdY + bird.getHeight()))
            {
                coin2x = screenWidth+260;

                scoreCurrent = scoreCurrent + 10 ;
                textViewScore.setText(""+scoreCurrent);


            }

            //code end of collision

            //code start of collision

            int centerCoin3x = coin3x + coin3.getWidth() / 2;
            int centerCoin3y = coin3y + coin3.getHeight() /2;

            if(centerCoin3x >= birdX && centerCoin3x <=(birdX + bird.getWidth()) && centerCoin3y >= birdY && centerCoin3y <= (birdY + bird.getHeight()))
            {
                coin3x = screenWidth+190;

                scoreCurrent = scoreCurrent + 10 ;
                textViewScore.setText(""+scoreCurrent);


            }

            //code end of collision

            if(right>0)
            {
                if(right == 2)
                {
                    right1.setImageResource(R.drawable.gray_heart);
                }

                if(right == 1)
                {

                    right2.setImageResource(R.drawable.gray_heart);
                }


                handler.postDelayed(runnable,20);

            }
            else if(right == 0)
            {
                handler.removeCallbacks(runnable);
                right3.setImageResource(R.drawable.gray_heart);


                enemy1.setVisibility(View.INVISIBLE);
                enemy2.setVisibility(View.INVISIBLE);
                enemy3.setVisibility(View.INVISIBLE);
                enemy4.setVisibility(View.INVISIBLE);
                coin1.setVisibility(View.INVISIBLE);
                coin2.setVisibility(View.INVISIBLE);
                coin3.setVisibility(View.INVISIBLE);

                handler1 = new Handler();
                runnable1 = new Runnable() {
                    @Override
                    public void run() {

                        birdX +=(screenWidth/40);
                        bird.setX(birdX);
                        bird.setY(screenHeight/2f);
                        if(birdX<=screenWidth)
                        {
                            handler1.postDelayed(runnable1,20);
                        }
                        else
                        {
                            handler1.removeCallbacks(runnable1);
                            Intent i = new Intent(GameActivity.this,ResultActivity.class);
                            i.putExtra("scoreCurrent",scoreCurrent);
                            startActivity(i);
                            finish();
                        }

                    }
                };
                handler1.post(runnable1);



            }

        }

    }

    @Override
    public void onBackPressed() {


        if(gameStart1==false)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
            builder.setMessage("Are You sure you want to Leave The Game Page");
            builder.setCancelable(false);

            builder.setNegativeButton("GO TO HOME", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(GameActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        }
        else
        {
            handler.removeCallbacks(runnable);



            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
            builder.setMessage("Are You sure you want to Leave The Game Page");
            builder.setCancelable(false);

            builder.setNegativeButton("GO TO HOME", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(GameActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    handler.postDelayed(runnable,20);
                }
            });
            builder.create().show();
        }


    }
}
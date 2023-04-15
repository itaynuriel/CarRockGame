package com.example.hw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {



    private ShapeableImageView[][] board;
    private FloatingActionButton arrow_left_btn;
    private FloatingActionButton arrow_right_btn;
    private static int colNum=1;
    private ImageView[] heartArr;
    final int DELAY=900;
    private int heartArrayIndex=2;


    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("pttt","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
    }
    private void findViews()
    {
        heartArr=new ImageView[3];
        heartArr[0]=findViewById(R.id.heart1);
        heartArr[1]=findViewById(R.id.heart2);
        heartArr[2]=findViewById(R.id.heart3);
        board=new ShapeableImageView[5][3];
        board[0][0]=findViewById(R.id.rock_0_0);
        board[0][1]=findViewById(R.id.rock_0_1);
        board[0][2]=findViewById(R.id.rock_0_2);
        board[1][0]=findViewById(R.id.rock_1_0);
        board[1][1]=findViewById(R.id.rock_1_1);
        board[1][2]=findViewById(R.id.rock_1_2);
        board[2][0]=findViewById(R.id.rock_2_0);
        board[2][1]=findViewById(R.id.rock_2_1);
        board[2][2]=findViewById(R.id.rock_2_2);
        board[3][0]=findViewById(R.id.rock_3_0);
        board[3][1]=findViewById(R.id.rock_3_1);
        board[3][2]=findViewById(R.id.rock_3_2);
        board[4][0]=findViewById(R.id.car_4_0);
        board[4][1]=findViewById(R.id.car_4_1);
        board[4][2]=findViewById(R.id.car_4_2);
        arrow_left_btn=findViewById(R.id.left_btn);
        arrow_right_btn=findViewById(R.id.right_btn);


    }
    private void initViews()
    {
        board[4][0].setVisibility(View.INVISIBLE);
        board[4][1].setVisibility(View.VISIBLE);
        board[4][2].setVisibility(View.INVISIBLE);
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<3;j++)
            {
                board[i][j].setVisibility(View.INVISIBLE);
            }
        }
        arrow_left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveLeft();
            }
        });
        arrow_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveRight();
            }
        });

    }

    private void moveLeft()
    {
        if(colNum==1)
        {
            board[4][0].setVisibility(View.VISIBLE);
            board[4][1].setVisibility(View.INVISIBLE);
            board[4][2].setVisibility(View.INVISIBLE);
            colNum=0;
            return;
        }
        if(colNum==2)
        {
            board[4][0].setVisibility(View.INVISIBLE);
            board[4][1].setVisibility(View.VISIBLE);
            board[4][2].setVisibility(View.INVISIBLE);
            colNum=1;
            return;
        }
        if(colNum==0)
        {
            return;
        }


    }
    private void moveRight()
    {
        if(colNum==1)
        {
            board[4][0].setVisibility(View.INVISIBLE);
            board[4][1].setVisibility(View.INVISIBLE);
            board[4][2].setVisibility(View.VISIBLE);
            colNum=2;
            return;
        }
        if(colNum==0)
        {
            board[4][0].setVisibility(View.INVISIBLE);
            board[4][1].setVisibility(View.VISIBLE);
            board[4][2].setVisibility(View.INVISIBLE);
            colNum=1;
            return;
        }
        if(colNum==2)
            return;

    }


    @Override
    protected void onStart() {
        Log.d("pttt","onStart");
        super.onStart();
        startGame();
    }
    @Override
    protected void onResume() {
        Log.d("pttt","onResume");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.d("pttt","onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("pttt","onStop");
        super.onStop();
        stopGame();
    }
    final Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this,DELAY);
            helper();
        }
    };
    private void startGame()
    {
        handler.postDelayed(runnable,DELAY);
    }
    private void stopGame()
    {
        handler.removeCallbacks(runnable);
    }
    private int setRandomRock()
    {
        Random rnd=new Random();
        int colIndex=rnd.nextInt(3);
        board[0][colIndex].setVisibility(View.VISIBLE);
        return colIndex;
    }
    boolean start = true;
    int colIndex = 0;
    int rowIndex = 0;
    private void helper()
    {
        if (start)
        {
            board[rowIndex][colIndex].setVisibility(View.INVISIBLE);
            rowIndex = 0;
            colIndex = setRandomRock();
            start = false;
        }
        else
        {
            moveRocks(colIndex);
        }
    }


    private void moveRocks(int colIndex)
    {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // note to self!!!
        if (rowIndex < 3) {
            board[rowIndex+1][colIndex].setVisibility(View.VISIBLE);
        }
        board[rowIndex++][colIndex].setVisibility(View.INVISIBLE);
        if (rowIndex == 3) {
            start = true;
        }
        if(heartArrayIndex<0)
        {
            heartArr[0].setVisibility(View.VISIBLE);
            heartArr[1].setVisibility(View.VISIBLE);
            heartArr[2].setVisibility(View.VISIBLE);
            heartArrayIndex=2;
        }
        if(board[4][0].getVisibility()==View.VISIBLE && board[3][0].getVisibility()==View.VISIBLE)
        {
            Toast.makeText(MainActivity.this, "Accident occured !", Toast.LENGTH_SHORT).show();
            vibrator.vibrate(500);
            heartArr[heartArrayIndex].setVisibility(View.INVISIBLE);
            heartArrayIndex--;
        }
        if(board[4][1].getVisibility()==View.VISIBLE && board[3][1].getVisibility()==View.VISIBLE)
        {
            vibrator.vibrate(500);
            Toast.makeText(MainActivity.this, "Accident occured !", Toast.LENGTH_SHORT).show();
            heartArr[heartArrayIndex].setVisibility(View.INVISIBLE);
            heartArrayIndex--;
        }
        if(board[4][2].getVisibility()==View.VISIBLE && board[3][2].getVisibility()==View.VISIBLE)
        {
            vibrator.vibrate(500);
            Toast.makeText(MainActivity.this, "Accident occured !", Toast.LENGTH_SHORT).show();
            heartArr[heartArrayIndex].setVisibility(View.INVISIBLE);
            heartArrayIndex--;
        }
//        board[3][0].setVisibility(View.INVISIBLE);
//        board[3][1].setVisibility(View.INVISIBLE);
//        board[3][2].setVisibility(View.INVISIBLE);

    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("pttt","onState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Log.d("pttt","onDestroy");
        super.onDestroy();
    }





}

package com.example.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tictactoe.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] button = new Button[3][3];

    private boolean playerOneTurn = true;

    private int roundCount;

    private int player1point;
    private int player2point;

    private TextView textViewplayerOne;
    private TextView textViewplayerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewplayerOne = (TextView) findViewById(R.id.player1);
        textViewplayerTwo = (TextView) findViewById(R.id.player2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String ButtonID = "button_" + i + j;
                int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
                button[i][j] = findViewById(resID);
                button[i][j].setOnClickListener(this);
            }
        }

        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals(""))  //if the button is already used.
        {
            return;
        }

        if (playerOneTurn) {
            ((Button) v).setText("X");
            ((Button) v).setBackgroundColor(this.getResources().getColor((R.color.purple)));
        } else {
            ((Button) v).setText("O");
            ((Button) v).setBackgroundColor(this.getResources().getColor((R.color.orange)));
        }

        roundCount++;

        //Checking for winners if any
        if (checkWin()) {
            if (playerOneTurn) {
                playerOneWins();
                changeDefaultColor();
            } else {
                playerTwoWins();
                changeDefaultColor();
            }
        } else if (roundCount == 9) {
            draw();
            changeDefaultColor();
        } else {
            playerOneTurn = !playerOneTurn;
        }


    }

    private boolean checkWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)  //all the values of buttons are keeping in fields
            {
                field[i][j] = button[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++)      //Checking all the possiblities and also checking that it should not ment
        {                         //consider three empty boxes
            if ((field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && field[i][0] != ""))
                return true;
        }

        for (int i = 0; i < 3; i++) {                        //same thing for c
            if ((field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && field[0][i] != ""))
                return true;
        }

        if ((field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && field[0][0] != ""))
            return true;//for diagonals

        if ((field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && field[0][2] != ""))
            return true;

        return false;
    }

    private void playerOneWins() {
        player1point++;
        Toast.makeText(MainActivity.this, "Player One Wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void playerTwoWins() {
        player2point++;
        Toast.makeText(MainActivity.this, "Player Two Wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(MainActivity.this, "Draw points", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewplayerOne.setText("Player 1:  " + player1point);
        textViewplayerTwo.setText("player 2:  " + player2point);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                button[i][j].setText("");
            }
        }
        roundCount = 0;
        playerOneTurn = true;
        changeDefaultColor();

    }

    private void resetGame() {
        player1point = 0;
        player2point = 0;
        updatePointsText();
        resetBoard();
        changeDefaultColor();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Point", player1point);
        outState.putInt("player2Point", player2point);
        outState.putBoolean("playerOneTurn", playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1point = savedInstanceState.getInt("player1Point");
        player2point = savedInstanceState.getInt("player2point");
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn");
    }

    private void changeDefaultColor() {   //This will change the button color default
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                button[i][j].setBackgroundResource(android.R.drawable.btn_default);
            }
        }
    }

}

package com.example.game1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private Boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is music
        MediaPlayer player = MediaPlayer.create(this, R.raw.game);
        player.setLooping(true);
        player.start();

        //Here  are the references to our textView
        textViewPlayer1 = findViewById(R.id.text_view_1);
        textViewPlayer2 = findViewById(R.id.text_view_2);

        //Assign button array with references to our buttons with nested for loops, then assign the buttons with the button IDs
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j; //append the rows and columns by using the first part of the ID
                int redID = getResources().getIdentifier(buttonID, "id", getPackageName()); //Building resource ID
                buttons[i][j] = findViewById(redID); //Get references to our buttons w/o having to do for every one
                buttons[i][j].setOnClickListener(this);// Same for the setOnClickListener by setting the main activity by using "this"
            }

        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) { // checks if button has empty string
            return;
        }

        if (player1Turn) { //if playerOne turn is true, it's an X, else it's an O and takes care of changing the text of the button
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");

        }
        roundCount++; //One more round is over and increments

        if (checkForWin()) { //This method is if no one has won and there is a draw and it switches turns
            if (player1Turn) {
                winner_P1();
            } else {
                winner_P2();
            }
        } else if (roundCount == 9) { //if 9 rounds are over there's a draw
            draw();
        } else {
            player1Turn = !player1Turn; //this switches turns
        }
    }

    private Boolean checkForWin() {
        String[][] points = new String[3][3];

        //Go through all of the buttons and save them in a string array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                points[i][j] = buttons[i][j].getText().toString();

            }
        }
        // compares three fields next to each other (ROWS)
        for (int i = 0; i < 3; i++) {
            if (points[i][0].equals(points[i][1]) && points[i][0].equals(points[i][2]) && !points[i][0].equals("")) {
                return true;
            }
        }
        // compares three fields next to each other (COLUMNS)
        for (int i = 0; i < 3; i++) {
            if (points[0][i].equals(points[1][i]) && points[0][i].equals(points[2][i]) && !points[0][i].equals("")) {
                return true;
            }
        }
        // check diagonal left to right
        if (points[0][0].equals(points[1][1]) && points[0][0].equals(points[2][2]) && !points[0][0].equals("")) {
            return true;
        }
        // check diagonal right to left
        if (points[0][2].equals(points[1][1]) && points[0][2].equals(points[2][0]) && !points[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void winner_P1() {
        player1Points++; //increment P1 points
        Toast.makeText(this, "Player 1 Is The Winner", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void winner_P2() {
        player2Points++; //increments P2 points
        Toast.makeText(this, "Player 2 Is The Winner", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "This Is A Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() { // goes through the whole board and resets it to empty
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0; //sets round to zero
        player1Turn = true; //sets it back to player 1's turn
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();

    }

    // Method called when the device is rotated
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

    }
}


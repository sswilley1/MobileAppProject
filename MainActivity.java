package com.example.game1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] btn = new Button[3][3];

    private Boolean player1Goes = true;

    private int nextRound;

    private int pointsP1;
    private int pointsP2;

    private TextView textView_P1;
    private TextView textView_P2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here are the references to our textView
        textView_P1 = findViewById(R.id.text_view_1);
        textView_P2 = findViewById(R.id.text_view_2);

        // Assign button array with references to our buttons with nested for loops, then assign the buttons with the button IDs
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j; // append the rows and columns bby using the first part of the ID.
                int redID = getResources().getIdentifier(buttonID, "id", getPackageName()); // Building resource ID
                btn[i][j] = findViewById(redID); // Get references to our buttons w/o having to do for every one
                btn[i][j].setOnClickListener(this); // Same for the setOnClickListener by setting the main activity by using "this"
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
        if (!((Button) v).getText().toString().equals("")) { // "" -- checks if button has empty string
            return;
        }

        if (player1Goes) { //  if playerOne turn is true, it's an X, else it's an O and takes care of changing the text of the button
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");

        }
        nextRound++; // One more round is over and increments

        if (checkForWin()) {
            if (player1Goes) {
                winner_P1();
            } else {
                winner_P2();
            }
        } else if (nextRound == 9) { // if 9 rounds are over there's a draw
            draw();
        } else {
            player1Goes = !player1Goes; // this switches turns
        }
    }

    private Boolean checkForWin() {
        String[][] position = new String[3][3];

        // Go through all of the buttons and save them in a string array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                position[i][j] = btn[i][j].getText().toString();

            }
        }
        // compares three fields next to each other (columns)
        for (int i = 0; i < 3; i++) {
            if (position[i][0].equals(position[i][1]) && position[i][0].equals(position[i][2]) && !position[i][0].equals("")) { // "" -- makes sure it doesn't compare to an empty field
                return true;
            }
        }
        // compares three fields next to each other (rows)
        for (int i = 0; i < 3; i++) {
            if (position[0][i].equals(position[i][1]) && position[0][i].equals(position[2][i]) && !position[0][i].equals("")) {
                return true;
            }
        }
        // check diagonal left to right
        if (position[0][0].equals(position[1][1]) && position[0][0].equals(position[2][2]) && !position[0][0].equals("")) {
            return true;
        }
        // check diagonal right to left
        if (position[0][2].equals(position[1][1]) && position[0][2].equals(position[2][0]) && !position[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void winner_P1() {
        pointsP1++; //increment P1 points
        Toast.makeText(this, "The Winner is Player 1!", Toast.LENGTH_SHORT).show(); // toast displays a message that disappears
        updatePointsInTextView();
        resetBoard();
    }

    private void winner_P2() {
        pointsP2++;
        Toast.makeText(this, "The Winner is Player 2!", Toast.LENGTH_SHORT).show();
        updatePointsInTextView();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "This game is a draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsInTextView() {
        textView_P1.setText("Player 1: " + pointsP1);
        textView_P2.setText("Player 2: " + pointsP2);
    }

    private void resetBoard() { // goes through the whole board and resets it to zero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j].setText("");
            }
        }
        nextRound = 0;  // sets round to zero
        player1Goes = true; // sets it back to player 1's turn
    }

    private void resetGame() {
        pointsP1 = 0;
        pointsP2 = 0;
        updatePointsInTextView();
        resetBoard();

    }

    // This is how the device is rotated
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", nextRound);
        outState.putInt("player1Points", pointsP1);
        outState.putInt("player2Points", pointsP2);
        outState.putBoolean("player1Turn", player1Goes);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        nextRound = savedInstanceState.getInt("roundCount");
        pointsP1 = savedInstanceState.getInt("player1points");
        pointsP2 = savedInstanceState.getInt("player2Points");
        player1Goes = savedInstanceState.getBoolean("player1Turn");

    }
}


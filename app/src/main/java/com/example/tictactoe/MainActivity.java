
package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean isPlayerX = true;
    private String[] board = new String[9];
    private boolean isGameOver = false;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        GridLayout gridLayout = findViewById(R.id.grid_layout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final int index = i;
            Button button = (Button) gridLayout.getChildAt(i);
            button.setOnClickListener(v -> onButtonClick(index, button));
        }

        Button resetButton = findViewById(R.id.btn_reset);
        resetButton.setOnClickListener(v -> resetGame());
    }

    private void onButtonClick(int index, Button button) {
        if (isGameOver || board[index] != null) return;

        board[index] = isPlayerX ? "X" : "O";
        button.setText(board[index]);

        if (checkWin()) {
            tvStatus.setText("Player " + board[index] + " Wins!");
            isGameOver = true;
        } else if (isBoardFull()) {
            tvStatus.setText("It's a Draw!");
            isGameOver = true;
        } else {
            isPlayerX = !isPlayerX;
            tvStatus.setText("Player " + (isPlayerX ? "X" : "O") + "'s Turn");
        }
    }

    private boolean checkWin() {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] condition : winConditions) {
            if (board[condition[0]] != null &&
                board[condition[0]].equals(board[condition[1]]) &&
                board[condition[1]].equals(board[condition[2]])) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (String cell : board) {
            if (cell == null) return false;
        }
        return true;
    }

    private void resetGame() {
        isGameOver = false;
        isPlayerX = true;
        board = new String[9];
        tvStatus.setText("Player X's Turn");

        GridLayout gridLayout = findViewById(R.id.grid_layout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText("");
        }
    }
}

package com.example.tictactoegame;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;

    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        if (!gameActive) {
            gameReset(view);
            counter = 0;
        }

        if (gameState[tappedImage] == 2) {
            counter++;
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                updateStatus("O's Turn - Tap to play");
            } else {
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                updateStatus("X's Turn - Tap to play");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }

        if (counter > 4) {
            for (int[] winPosition : winPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                        gameState[winPosition[1]] == gameState[winPosition[2]] &&
                        gameState[winPosition[0]] != 2) {
                    gameActive = false;
                    String winnerStr = (gameState[winPosition[0]] == 0) ? "X has won" : "O has won";
                    updateStatus(winnerStr);
                    Toast.makeText(this, "Tap anywhere on the board to clean!", Toast.LENGTH_SHORT).show();

                }
            }
            if (counter == 9 && gameActive) {
                updateStatus("Match Draw");
            }
        }
    }

    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        gameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};

        for (int i = 0; i < 9; i++) {
            int resourceId = getResources().getIdentifier("imageView" + i, "id", getPackageName());
            ((ImageView) findViewById(resourceId)).setImageResource(0);
        }
        updateStatus("X's Turn - Tap to play");
    }

    private void updateStatus(String statusText) {
        TextView status = findViewById(R.id.status);
        status.setText(statusText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

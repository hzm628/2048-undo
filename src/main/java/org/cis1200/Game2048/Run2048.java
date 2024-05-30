package org.cis1200.Game2048;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Run2048  implements Runnable {

    static final Color BG_COLOR = new Color(250, 248, 239);

    /**
     * This class sets up the top-level frame and widgets for the GUI.
     *
     * This game adheres to a Model-View-Controller design framework. This
     * framework is very effective for turn-based games. We STRONGLY
     * recommend you review these lecture slides, starting at slide 8,
     * for more details on Model-View-Controller:
     * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
     *
     * In a Model-View-Controller framework, Game initializes the view,
     * implements a bit of controller functionality through the reset
     * button, and then instantiates a GameBoard. The GameBoard will
     * handle the rest of the game's view and controller functionality, and
     * it will instantiate a TicTacToe object to serve as the game's model.
     */
    public void run() {

        // Start frame
        JFrame startFrame = new JFrame();
        startFrame.setTitle("2048");
        startFrame.setSize(450, 450);
        startFrame.setResizable(false);

        // Start Screen
        final StartPanel startPanel = new StartPanel();
        startFrame.add(startPanel, BorderLayout.NORTH);

        JPanel gameButtons = new JPanel(new GridLayout(0, 2));

        // Create new game
        final JButton newGame = new JButton("New Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setTitle("2048");

                frame.setSize(600, 750);
                frame.setResizable(false);

                GameBoard board = new GameBoard();
                frame.add(board, BorderLayout.CENTER);

                final ControlPanel control_panel = new ControlPanel(board);
                frame.add(control_panel, BorderLayout.NORTH);

                frame.getContentPane().setBackground(BG_COLOR);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                startFrame.setVisible(false);
                frame.setVisible(true);

                board.newGame();

            }
        });

        gameButtons.add(newGame);

        // Load game
        final JButton loadGame = new JButton("Load Game");
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setTitle("2048");

                frame.setSize(600, 750);
                frame.setResizable(false);

                GameBoard board = new GameBoard();
                frame.add(board, BorderLayout.CENTER);

                final ControlPanel control_panel = new ControlPanel(board);
                frame.add(control_panel, BorderLayout.NORTH);

                frame.getContentPane().setBackground(BG_COLOR);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                startFrame.setVisible(false);
                frame.setVisible(true);

                board.loadGame();

            }
        });

        // Only enable load game button if file exist
        loadGame.setEnabled(GameLogic.fileExist("game_state.txt"));
        gameButtons.add(loadGame);
        startFrame.add(gameButtons, BorderLayout.CENTER);

        startFrame.getContentPane().setBackground(BG_COLOR);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setVisible(true);
    }

}

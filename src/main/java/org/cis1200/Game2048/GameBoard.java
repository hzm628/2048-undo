package org.cis1200.Game2048;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JPanel implements KeyListener {

    private final GameLogic logic;
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 500;
    private static final Color BG_COLOR = new Color(250, 248, 239);
    private static final Color BOARD_BG_COLOR = new Color(187, 173, 160);
    private static final Color TILE_COLOR = new Color(119, 110, 101);
    private static final Color TILE_COLOR_LARGE = new Color(249, 246, 242);
    private static final String FONT_NAME = "Arial";
    private static final int SPACING = 14;
    private static final int TILE_SIZE = 107;
    private static final int FONT_SIZE = 47;

    private JButton tryAgain;
    private JButton newGame;

    public GameBoard() {
        setFocusable(true);

        setSize(500, 500);
        setLayout(null);

        logic = new GameLogic();

        setBackground(BG_COLOR);
        addKeyListener(this);

        tryAgain = new JButton("Try Again");
        tryAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tryAgain.setVisible(false);
                newGame();
            }
        });
        tryAgain.setBounds(300 - 45, 270 + 20, 90, 40);
        add(tryAgain);
        tryAgain.setVisible(false);

        newGame = new JButton("New Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGame.setVisible(false);
                newGame();
            }
        });
        newGame.setBounds(300 - 45, 270 + 20, 90, 40);
        add(newGame);
        newGame.setVisible(false);

    }

    public void newGame() {

        logic.newGame();

        ControlPanel.setScore(logic.getScore());
        ControlPanel.setBestScore(logic.getBestScore());

        repaint();

        if (tryAgain.isVisible()) {
            tryAgain.setVisible(false);
        }
        if (newGame.isVisible()) {
            newGame.setVisible(false);
        }
        requestFocusInWindow();
    }

    public void loadGame() {

        logic.loadGame();

        ControlPanel.setScore(logic.getScore());
        ControlPanel.setBestScore(logic.getBestScore());
        repaint();

        requestFocusInWindow();
    }

    public void undo() {
        logic.undoMove();
        repaint();
        ControlPanel.setScore(logic.getScore());
        repaint();
        requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (logic.getGameState() == GameState.STARTED) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    logic.move("up");
                    break;
                case KeyEvent.VK_RIGHT:
                    logic.move("right");
                    break;
                case KeyEvent.VK_DOWN:
                    logic.move("down");
                    break;
                case KeyEvent.VK_LEFT:
                    logic.move("left");
                    break;
                default:
                    break;
            }
            ControlPanel.setScore(logic.getScore());
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(BOARD_BG_COLOR);
        g2.fillRoundRect(50, 0, 500, 500, 6, 6);

        Tile[][] board = logic.getBoard();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Tile t = board[row][col];

                int xPosition = 50 + (col * 107) + ((col + 1) * SPACING);
                int yPosition = (row * 107) + ((row + 1) * SPACING);

                g2.setColor(getTileBackground(t));
                g2.fillRoundRect(xPosition, yPosition, TILE_SIZE, TILE_SIZE, 4, 4);

                if (t.getValue() != 0) {

                    String stringValue = String.valueOf(t.getValue());
                    Font font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);

                    if (t.getValue() <= 4) {
                        g.setColor(TILE_COLOR);
                    } else {
                        g.setColor(TILE_COLOR_LARGE);
                    }
                    drawTextCentered(font, g, stringValue, xPosition, yPosition, TILE_SIZE, 0);
                }
            }
        }

        // Game Over
        if (logic.getGameState() == GameState.LOSS) {
            g2.setColor(new Color(238, 228, 218, 186));
            g2.fillRoundRect(50, 0, 500, 500, 6, 6);

            g.setColor(TILE_COLOR);
            Font font = new Font(FONT_NAME, Font.BOLD, 60);
            drawTextCentered(font, g, "Game Over", 50, 0, 500, 30);

            tryAgain.setVisible(true);

            // Win!
        } else if (logic.getGameState() == GameState.WIN) {
            g2.setColor(new Color(238, 228, 218, 186));
            g2.fillRoundRect(50, 0, 500, 500, 6, 6);

            g.setColor(TILE_COLOR);
            Font font = new Font(FONT_NAME, Font.BOLD, 60);
            drawTextCentered(font, g, "You won!", 50, 0, 500, 30);

            newGame.setVisible(true);
        }
    }

    public void drawTextCentered(Font font, Graphics g, String text,
                                 int startX, int startY, int size, int yOffset) {

        FontMetrics fontMetrics = g.getFontMetrics(font);

        int centerX = startX + (size - fontMetrics.stringWidth(text)) / 2;
        int centerY = startY + ((size - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

        g.setFont(font);
        g.drawString(text, centerX, centerY - yOffset);
    }

    public Color getTileBackground(Tile t) {
        return t.getTileColor();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

}

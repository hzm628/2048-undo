package org.cis1200.Game2048;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameLogic {
    private Tile[][] board;
    private int score;
    private int bestScore;
    private LinkedList<Tile[][]> boardHistory;
    private LinkedList<Integer> scoreHistory;
    private GameState gameState;

    public GameLogic() {
        board = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = new Tile();
            }
        }
        score = 0;
        bestScore = 0;
        boardHistory = new LinkedList<>();
        scoreHistory = new LinkedList<>();
    }

    public Tile[][] getBoard() {
        Tile[][] boardCopy = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boardCopy[i][j] = new Tile(board[i][j].getValue());
            }
        }
        return boardCopy;
    }

    public void setBoard(Tile[][] board) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.board[i][j].setValue(board[i][j].getValue());
            }
        }
    }

    public int getScore() {
        return this.score;
    }

    public int getBestScore() {
        return this.bestScore;
    }

    public void updateBestScore() {
        if (score > bestScore) {
            bestScore = score;
            writeGameStateFile();
        }
    }

    public List<Integer> emptySpaces() {
        List<Integer> empty = new ArrayList<>();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col].isEmpty()) {
                    empty.add((row * 10) + col);
                }
            }
        }
        return empty;
    }

    public void addTile() {
        List<Integer> empty = emptySpaces();
        if (!empty.isEmpty()) {
            int x = empty.get((int) (Math.random() * empty.size()));
            double prob = Math.random();
            if (prob < 0.05) {
                board[(int) (x / 10)][x % 10] = new Tile(4);
            } else {
                board[(int) (x / 10)][x % 10] = new Tile(2);
            }
        }
    }

    public int slideAndReturnScore() {
        int moveScore = 0;

        Tile[][] boardCopy = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], 4);
        }

        for (int col = 0; col < 4; col++) {
            LinkedList<Integer> shiftedCol = new LinkedList<>();
            for (int row = 0; row < 4; row++) {
                if (boardCopy[row][col].getValue() > 0) {
                    shiftedCol.add(boardCopy[row][col].getValue());
                }
            }
            LinkedList<Integer> mergedCol = new LinkedList<>();
            while (shiftedCol.size() > 1) {
                int head = shiftedCol.pop();
                int next = shiftedCol.getFirst();
                if (head == next) {
                    mergedCol.add(head * 2);
                    moveScore += head * 2;
                    shiftedCol.pop();
                    if (head * 2 == 2048) {
                        gameState = GameState.WIN;
                    }
                } else {
                    mergedCol.add(head);
                }
            }
            mergedCol.addAll(shiftedCol);

            for (int row = 0; row < 4; row++) {
                if (mergedCol.size() > 0) {
                    board[row][col].setValue(mergedCol.pop());
                } else {
                    board[row][col].setValue(0);
                }
            }
        }
        return moveScore;
    }

    public void transformAndThenSlide(String direction) {
        if (direction.equals("right") || direction.equals("left")) {
            transposeBoard();
        }
        if (direction.equals("right") || direction.equals("down")) {
            reverseBoard();
        }
        this.score += slideAndReturnScore();

        if (direction.equals("right") || direction.equals("down")) {
            reverseBoard();
        }

        if (direction.equals("right") || direction.equals("left")) {
            transposeBoard();
        }
    }

    // Interchange row and column
    public void transposeBoard() {
        Tile[][] transposed = new Tile[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                transposed[col][row] = board[row][col];
            }
        }
        board = transposed;
    }

    // reverse values in each column
    public void reverseBoard() {
        Tile[][] reversed = new Tile[4][4];
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                reversed[board.length - 1 - row][col] = board[row][col];
            }
        }
        board = reversed;
    }

    public void move(String direction) {
        transformAndThenSlide(direction);
        addTile();

        boardHistory.push(getBoard());
        scoreHistory.push(score);
        writeGameStateFile();

        checkLoss();
        if (gameState == GameState.WIN || gameState == GameState.LOSS) {
            deleteFile("game_state.txt");
            updateBestScore();
        }
    }

    public void undoMove() {
        if (boardHistory.size() > 1 && gameState == GameState.STARTED) {
            boardHistory.pop();
            setBoard(boardHistory.peek());
            scoreHistory.pop();
            score = scoreHistory.peek();
            writeGameStateFile();
        }
    }

    public void checkLoss() {
        if (!emptySpaces().isEmpty()) {
            return;
        }
        for (int col = 0; col < board[0].length; col++) {
            LinkedList<Integer> checkingCol = new LinkedList<Integer>();
            for (int row = 0; row < board.length; row++) {
                checkingCol.add(board[row][col].getValue());
            }

            while (checkingCol.size() > 1) {
                int head = checkingCol.pop();
                int next = checkingCol.peek();
                if (head == next) {
                    return;
                }
            }
        }
        for (int row = 0; row < board.length; row++) {
            LinkedList<Integer> checkingCol = new LinkedList<Integer>();
            for (int col = 0; col < board[0].length; col++) {
                checkingCol.add(board[row][col].getValue());
            }
            while (checkingCol.size() > 1) {
                int head = checkingCol.pop();
                int next = checkingCol.peek();
                if (head == next) {
                    return;
                }
            }
        }
        gameState = GameState.LOSS;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    // FILE I/0 =============================================================
    public void writeGameStateFile() {
        File file;

        try {
            file = Paths.get("game_state.txt").toFile();
        } catch (InvalidPathException e) {
            file = new File("game_state.txt");
        }

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    bw.write(board[row][col] + " ");
                }
                bw.newLine();
            }
            bw.write(String.valueOf(score));

        } catch (IOException e) {
            System.out.println("" + e);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception e) {
                System.out.println("Cannot close: " + e);
            }
        }
    }

    public void readFileAndSet(String fileName) {
        BufferedReader br = null;
        try {
            String line;
            File file = new File(fileName);
            br = new BufferedReader(new FileReader(file));

            if (fileName.equals("game_state.txt")) {
                for (int i = 0; i < 4; i++) {
                    if ((line = br.readLine()) != null) {
                        String[] value = line.split(" ");

                        for (int j = 0; j < value.length; j++) {
                            board[i][j].setValue(Integer.parseInt(value[j]));
                        }
                    }
                }
                if ((line = br.readLine()) != null) {
                    score = Integer.parseInt(line);
                }
            } else {
                if ((line = br.readLine()) != null) {
                    bestScore = Integer.parseInt(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e);
        } catch (IOException e) {
            System.out.println("" + e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                System.out.println("Cannot close: " + e);
            }
        }
    }

    public void deleteFile(String fileName) {
        if (fileExist(fileName)) {
            File file = new File("game_state.txt");
            file.delete();
        }
    }

    public static boolean fileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public void newGame() {
        updateBestScore();
        board = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = new Tile();
            }
        }
        gameState = GameState.STARTED;
        score = 0;

        boardHistory = new LinkedList<>();
        scoreHistory = new LinkedList<>();

        addTile();
        addTile();

        boardHistory.push(getBoard());
        scoreHistory.push(score);
        writeGameStateFile();
    }

    public void loadGame() {
        updateBestScore();
        gameState = GameState.STARTED;
        readFileAndSet("game_state.txt");

        boardHistory = new LinkedList<>();
        scoreHistory = new LinkedList<>();

        boardHistory.push(getBoard());
        scoreHistory.push(score);
    }

    // Testing Only
    public Tile[][] convertIntBoard(int[][] intBoard) {
        Tile[][] result = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = new Tile(intBoard[i][j]);
            }
        }
        return result;
    }

    // Testing Only
    public void setGame(Tile[][] board) {
        this.board = board;
        this.boardHistory.push(board);
        this.scoreHistory.push(0);
    }


    // Testing Only
    public void setGameState(GameState g) {
        this.gameState = g;
    }

}

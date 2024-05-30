package org.cis1200.Game2048;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameLogicTest {
    GameLogic gameLogic = new GameLogic();

    @Test
    public void testAddTileAndEmptySpaces() {
        gameLogic.addTile();
        gameLogic.addTile();
        gameLogic.addTile();
        int emptySpaces = gameLogic.emptySpaces().size();
        assertEquals(13, emptySpaces);
    }

    @Test
    public void testCannotAddTileToFullBoard() {
        int[][] intBoard = {{8, 8, 8, 8}, {8, 8, 8, 8}, {8, 8, 8, 8}, {8, 8, 8, 8}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(8, gameLogic.getBoard()[i][j].getValue());
            }
        }
    }

    @Test
    public void testMoveRight() {
        int[][] intBoard = {{4, 4, 4, 4}, {4, 4, 4, 4}, {4, 4, 4, 4}, {4, 4, 4, 4}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        gameLogic.move("right");

        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 3; j++) {
                assertEquals(8, gameLogic.getBoard()[i][j].getValue());
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 3; j < 4; j++) {
                assertEquals(8, gameLogic.getBoard()[i][j].getValue());
            }
        }
    }

    @Test
    public void testMoveUp() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {2, 2, 2, 2}, {2, 2, 2, 2}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        gameLogic.move("up");

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(4, gameLogic.getBoard()[i][j].getValue());
            }
        }
    }

    @Test
    public void testAddCorrectScore() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {2, 4, 2, 2}, {2, 2, 4, 4}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        gameLogic.move("left");

        assertEquals(16, gameLogic.getScore());
    }

    @Test
    public void testNoScoreAdded() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {4, 4, 2, 2}, {2, 2, 4, 4}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        gameLogic.move("down");

        assertEquals(0, gameLogic.getScore());
    }

    @Test
    public void testGetBoardEncapsulation() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {4, 4, 2, 2}, {2, 2, 4, 4}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        Tile[][] getBoard = gameLogic.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                getBoard[i][j] = new Tile();
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(board[i][j].getValue(), gameLogic.getBoard()[i][j].getValue());
            }
        }
    }

    @Test
    public void testUndoMoveDoesNotWorkInitially() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        gameLogic.addTile();
        gameLogic.addTile();
        gameLogic.setGameState(GameState.STARTED);
        gameLogic.undoMove();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(board[i][j].getValue(), gameLogic.getBoard()[i][j].getValue());
            }
        }
    }

    @Test
    public void testUndoOneTime() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {4, 4, 2, 2}, {2, 2, 4, 4}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        gameLogic.setGameState(GameState.STARTED);
        gameLogic.move("up");
        gameLogic.undoMove();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(board[i][j].getValue(), gameLogic.getBoard()[i][j].getValue());
            }
        }
    }

    @Test
    public void testUndoMultipleTimes() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {4, 4, 4, 4}, {4, 4, 4, 4}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        gameLogic.setGameState(GameState.STARTED);
        gameLogic.move("down");
        gameLogic.move("left");
        gameLogic.move("up");
        gameLogic.move("down");
        gameLogic.move("right");
        gameLogic.undoMove();
        gameLogic.undoMove();
        gameLogic.undoMove();
        assertEquals(16, gameLogic.getBoard()[3][0].getValue());
        assertEquals(16, gameLogic.getBoard()[3][1].getValue());
    }

    @Test
    public void testUndoBackToInitialState() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {4, 4, 4, 4}, {4, 4, 4, 4}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        gameLogic.setGameState(GameState.STARTED);
        gameLogic.move("down");
        gameLogic.move("left");
        gameLogic.move("up");
        gameLogic.move("down");
        gameLogic.move("right");
        gameLogic.undoMove();
        gameLogic.undoMove();
        gameLogic.undoMove();
        gameLogic.undoMove();
        gameLogic.undoMove();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(board[i][j].getValue(), gameLogic.getBoard()[i][j].getValue());
            }
        }
    }

    @Test
    public void testUndoMoveUndo() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {4, 4, 4, 4}, {4, 4, 4, 4}};
        Tile[][] board = gameLogic.convertIntBoard(intBoard);
        gameLogic.setGame(board);
        gameLogic.setGameState(GameState.STARTED);
        gameLogic.move("down");
        gameLogic.undoMove();
        gameLogic.move("left");
        gameLogic.undoMove();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(board[i][j].getValue(), gameLogic.getBoard()[i][j].getValue());
            }
        }
    }

}

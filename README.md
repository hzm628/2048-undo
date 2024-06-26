# 2048 w/ Unlimited Undo

Zimo Huang (zimoh)

## Overview 

<img width="477" alt="start-panel" src="https://github.com/hzm628/2048-undo/assets/127806300/acf73b94-5af2-407c-8dd9-b1ac6fca29d5">

<img width="623" alt="game" src="https://github.com/hzm628/2048-undo/assets/127806300/0fefc0ff-a149-4fa4-a044-5018bc739b5e">

## Core concepts and the features they implement

  1. 2D Array: I use 2D array to represent the 4 x 4 game board, with each entry in
     the array containing a Tile element (an instance of "Tile" class that I implement).

  2. Collections: For undo, I use two linked lists to store every move and every score
     respectively because in the undo functionality, order matters. And LinkedList's
     push and pop methods allow me to add and delete an move (board) easily.

  3. File I/O: For the load game functionality, I use I/O to store the game states.
     I store the board layout and the score in a file so that when a user wants to
     come back to the game I can read from the file and resume the game.

  4. JUnit testable component: I use JUnit tests to test the logic of the game. 
     

## Overview of each of the classes and their function in the overall game

  1. Run2048: it contains the run() method to actually execute the program and display the JFrames.

  2. Tile: Tile class represents individual Tile in the board. It contains methods such as getValue,
        setValue and getTileColor.

  3. GameState: It stores three types of game state: started, win, loss. It makes the implementation
             easier and also prevents the user from doing things in the wrong game state.
  
  4. GameLogic: This is the most important class in the game. It acts as a model for the key logic
             and implementation of 2048. It is completely separated from GUI components and therefore
             allows for unit testing.

  5. StartPanel: StartPanel displays the starting page of 2048. It includes a brief description of
              how to play this game and the "New Game" and "Load Game" options.

  6. GameBoard: The JPanel that "paints" 2048 onto the window. It uses methods in GameLogic and turn
             them into Graphics. It also includes scenarios where the user wins and losses.

  7. ControlPanel: This JPanel includes two buttons: "New Game" and "Undo", which control the GameBoard.

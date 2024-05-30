=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: zimoh
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Array: I use 2D array to represent the 4x4 game board, with each entry in
     the array containing a Tile element (an instance of "Tile" class that I implement).

  2. Collections: For undo, I use two linked lists to store every move and every score
     respectively because in the undo functionality, order matters. And LinkedList's
     push and pop methods allow me to add and delete an move (board) easily.

  3. File I/O: For the load game functionality, I use I/O to store the game states.
     I will store the board layout and the score in a file so that when a user wants to
     come back to the game I can read from the file and resume the game.

  4. JUnit testable component: I use JUnit tests to test the logic of my game. I make sure
     that I use the right assert statements and cover important edge cases and not repeat
     my tests.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Run2048: it contains the run() method to actually execute the program and display the JFrames.

  Tile: Tile class represents individual Tile in the board. It contains methods such as getValue,
        setValue and getTileColor.

  GameState: It stores three types of game state: started, win, loss. It makes the implementation
             easier and also prevents the user from doing things in the wrong game state.

  GameLogic: This is the most important class in the game. It acts as a model for the key logic
             and implementation of 2048. It is completely separated from GUI components and therefore
             allows for unit testing.

  StartPanel: StartPanel displays the starting page of 2048. It includes a brief description of
              how to play this game and the "New Game" and "Load Game" options.

  GameBoard: The JPanel that "paints" 2048 onto the window. It uses methods in GameLogic and turn
             them into Graphics. It also includes scenarios where the user wins and losses.

  ControlPanel: This JPanel includes two buttons: "New Game" and "Undo", which control the GameBoard.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  At first, it was very difficult to get all the classes to communicate with each other
  properly. Besides, the move method is especially challenging to implement because I have to
  manipulate 2D array and merge values accordingly in consideration of the four directions.
  UndoMove method is also difficult as it deals with LinkedList. Previously we did not come
  across the undo functionality either, so I have to figure it out by myself with the help
  of java docs.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  The functionality is separated well. I don't think I would significantly refactor any part.
  The game model/logic is completely separated from GUI graphics and this design makes the code
  unit-testable.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  I consulted the Javadocs and Lecture Notes for BufferedReader, BufferedWriter and LinkedList.
  I used online RGB color palette to determine different Tiles' background colors.

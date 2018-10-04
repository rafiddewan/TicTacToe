package com.company;

/**
 * Assignment #1
 * A simple TicTacToe game (does not do diagonals)
 * @author Rafid Dewan
 */
public class TicTacToe {
    private int nRows; // Number of Rows in grid
    private int nColumns; //Number of Columns in grid
    private int numToWin; //Numbers of characters in a row or column it takes to win
    private char[][] grid; //Grid for the game
    private char turn; //the turn is of the game is dependent on 'x' or 'o'
    private TicTacToeEnum gameState; //The state of the game when wins, loses, or draws the match also indicates whether or not match is in progress
    private int nMarks; //Number of plays made (a counter to make sure when the game is drawn)

    /**
     * One argument constructor
     * Default constructor that creates the game
     *
     * @param initialTurn
     */
    public TicTacToe(char initialTurn) {
        this(3, 3, 3, initialTurn);
    }

    /**
     * Four argument constructor
     * Gives choice as to how large the TicTacToe table
     * Resets the original grid and starts the game
     * @param nRows
     * @param nColumns
     * @param numToWin
     * @param initialTurn
     */
    public TicTacToe(int nRows, int nColumns, int numToWin, char initialTurn) {
        //Checks to see if the columns and rows were fit the guidelines when creating the tic tac toe table
        if ((nRows != nColumns) && (nRows != numToWin) && (nRows < 3)) {
            throw new IllegalArgumentException("Numbers must be the same and greater than 3");
        }
        this.nRows = nRows; //Set the turn to the initial rows used in the arguments to the object
        this.nColumns = nColumns; //Set the turn to the initial columns used in the arguments to the object
        this.numToWin = numToWin; //Set the the amount off characters in a row
        this.grid = new char[nRows][nColumns]; //Creates the grid for tic tac toe
        reset(initialTurn);
    }

    /**
     * Resets the grid to the corresponding columns and rows the user entered
     * All columns are set to ' '
     * Set the initial turn, the marks, and game state to start the game
     *  @param initialTurn
     */
    public void reset(char initialTurn) {
        //Resets the table to spaces when starting the game
        for (int i = 0; i < nColumns; i++) {
            for (int j = 0; j < nRows; j++) {
                grid[i][j] = ' '; //Set each index to a space character
            }
        }
        this.turn = initialTurn; //Set the turn to the initial turn passed in the arguments to the object
        this.nMarks = 0; //Set the initial mark to  0 the arguments passed to the object
        this.gameState = TicTacToeEnum.IN_PROGRESS; //Returns an enum regarding the game state since In_Progress to signify the start of the game
    }

    /**
     * Returns who's turn it is
     * @return TicTacToeEnum
     */
    public int getTurn()
    {
        return this.turn;
    }

    /**
     * Return's the state of the game
     * @return TicTacToeEnum
     */
    public TicTacToeEnum getGameState(){
        return this.gameState;
    }

    private TicTacToeEnum charToEnum(char player)
    {
        player = Character.toUpperCase(player); //Makes the X and O uppercase in case the user entered their result in lower
        if (player == 'O')  return TicTacToeEnum.O_WON;
        if (player == 'X') return TicTacToeEnum.X_WON;
        //if(player != 'X' || player != 'O') throw new IllegalArgumentException("You entered: " + player + "which is an invalid character, must be X or O");
        return TicTacToeEnum.IN_PROGRESS;
    }

    /**
     * Takes the turn
     * @param row
     * @param column
     * @return
     */
    public TicTacToeEnum takeTurn(int row, int column)
    {
        //Checks if game state is in progress if not its game over
        if(this.gameState != TicTacToeEnum.IN_PROGRESS)
            throw new IllegalArgumentException("GAME OVER!");
        //Checks if the user enters a valid comma/row
        if(row > nColumns || column > nColumns|| row < 0 || column < 0)
            throw new IllegalArgumentException ("No such row and column (" + row+ "," + column + " it might be out of bounds. \nPlease try another option");
        //Checks if the specific row/column is already used
        if(this.grid[row][column] != ' ')
            throw new IllegalArgumentException("Row and column occupied by: " + this.grid[row][column] + ". \nPlease try a different option ");
        this.grid[row][column] = this.turn;
        this.nMarks++; //Turn counter
        this.gameState = findWinner(); //Check to see if game is won/drawn/ongoing
        //used for switching turns
        if(this.turn == 'X') this.turn ='O';
        else this.turn ='X';
        return this.gameState; //gives an indication where the game is at
    }

    /**
     * Finds the winner of the game if X won, O won, a draw or if the game is still in progress
     * @return IN_PROGRESS, X_WON, O_WON, DRAW
     */
    private TicTacToeEnum findWinner()
    {
        if(nMarks == nColumns * nRows) return TicTacToeEnum.DRAW;
        else
            {
                //Check  for rows
                int c = 0; //count kept constant checking the rows/columns
                int counterX = 0;//count the number of X's in a row/column
                int counterO = 0;//count the number of O's in a row/column
                //Makes sure that all the columns are checked
                while(c < numToWin)
                {
                    //Checks each row for each specific column
                    for(int i = 0; i < nRows; i++)
                    {
                        //Increment the counter to keep in track of the # of X's ad O's in a row
                        if (grid[i][c] == 'X') counterX++;
                        else counterO++;
                    }
                    c++; //Increment the c to check the next column
                }
                if (counterX == 3) return TicTacToeEnum.X_WON;
                else if (counterO == 3) return TicTacToeEnum.O_WON;
                //Check  for columns, reuse the variables by reinitializing them to 0
                c = 0;
                counterX = 0;
                counterO = 0;
                //Do the same thing as the code above checking each column but instead check each row
                while(c < numToWin)
                {
                    for(int i = 0; i < nColumns; i++)
                    {
                        if (grid[c][i] == 'X') counterX++;
                        else counterO++;
                    }
                    c++;
                }
                if (counterX == 3) return TicTacToeEnum.X_WON;
                else if (counterO == 3) return TicTacToeEnum.O_WON;
            }
        return TicTacToeEnum.IN_PROGRESS;//If none of the cases are true, then it returns the progress of the game
    }

    /**
     * Makes the grid for the game in a form of a string
     * @return String
     */
    public String toString()
    {
        String gridPrint = new String(""); //Initialize a new string for tic tac toe
        //Nested for  loop used to print the each box in tic tac toe
        for (int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nColumns; j++)
            {
                gridPrint += grid[i][j] + " | "; // print's the space/O/X and then a line indicating the box in each column.
            }
            gridPrint += "\n"; //creates rows by creating a new line after going through each column
        }
        return gridPrint; //returns the string of that grid
    }
}
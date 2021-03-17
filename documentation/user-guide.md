# User Guide

## Running the application

The application can be started by using command:

```
gradle run --console plain
```

Then you will be greeted by a welcome message where you can choose what you
want to do. 

```
Hello! Please choose an option (1, 2 or 3):
1) Play versus the computer.
2) Let the computer play with itself.
3) Run performance tests
```

You need to type in the option that you want to use. 

### Play versus the computer

If you want to play versus the computer, you can choose the option 1. The appliation will randomize the colors and then when it is your turn, it will ask what move would you like to make. The moves are given in a format of "e2e4" (this would move a piece from square e2 to e4). If the command is incorrect or the move is illegal, you will be asked again until you give a valid move. An exception for the format is when you move a pawn to the last rank and have to promote it. In that case you have to specify how to promote the piece. For example, e7e8q would promote the pawn on e file to a queen. q stands for queen, n for a knight, b for a bishop and r for a rook. 

When the application notices that the game as ended, it will print the result. 

TIP. You can use a real chess board or go to [Lichess](https://lichess.org/) for example to see the chess board. Then when you make moves on the board, just give them to the application through the command-line. This way it is easier to play because playing chess from the command-line can be quite tough. 

### Bot vs Bot

By choosing the option 2, the AI will face against itself. The application will print out the position and the evaluation after every move so that you can follow the game. 

### Performance tests

If you choose the option number 3, the application will do performance tests. More about testing [here](./testing-document.md).

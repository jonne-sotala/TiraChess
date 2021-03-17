# Implementation Document

## Classes

### Position

Position class takes care of the different chess positions and move generation. It also allows importing other positions for example from the internet by using FEN. The move generation follows the rules of chess precisely and has been tested extensively. More about testing [here](./testing-document.md).

### Zobrist

Zobrist class is used to create unique hashes for different chess positions. More info about the algorithm can be found [here](https://www.chessprogramming.org/Zobrist_Hashing)

### Evaluator

Evaluator class has the responsibity of evaluating chess positions. The main way of evaluating the position is minmax search with alpha-beta pruning. There is a method for a simple minmax search as well for comparison. The leaf nodes are evaluated by the evaluate function which takes into account material imbalances and piece placement. 

### PerformanceTester

PerformaceTester can be used to compare the speed of custom data structures. It also compares the simple minmax search against alpha-beta pruning. 

### Other

The other classes are mostly there to set up a chess game and manage the command-line interface. 

## Data Structures

I have implemented two custom data structures in this project. MyHashMap and MyArrayList are used to replace Java's own HashMap and ArrayList. The purpose has not been to create all of the functionality that the Java's data structures have but to have all the methods I need for my own application. For that reason, for example, MyArrayList doesn't have a remove method implemented. 

## Improvement ideas

After doing most of the move generation, I realized that I could have done it in a different way. Currently the application always creates a new position object for each position when traversing the search tree. It would be more efficient to just use the same object for the whole search and just make changes to it when moves and made/unmade. 

I also tried to implement the iterative deepening with transpositional tables. However, it introduced some weird behaviour and I wasn't able to figure out the problem in time so I left out those optimazations from the search algorithm. With more time, I could try to implement them. 

Another improvement would be to have some kind of UI for playing and viewing the games. The command-line interface is ok but it is a little bit hard to keep track of chess games with it. 

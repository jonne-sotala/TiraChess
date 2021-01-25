# Requirements specification

## Project description

The purpose of this project is to create a simple chess AI in Java.

## Data structures and algorithms

- Min-max searching
- Alpha-beta pruning
- Iterative deepening
- Transposition tables
- Hash tables
- Board evaluation

## Problem 

The goal is to calculate correct moves for each turn in a chess game.
The main algorithm used will be alpha-beta pruning which searches the game tree for the best possible move. 
The alpha-beta algorithm will be using iterative deepening to make it more efficient and make it possible to give a 
good move even if it runs out of time. Transposition tables will be used to save already calculated positions 
which makes the algorithm faster and is required by iterative deepening. Transposition tables need hash tables so 
they will also be implemented. Lastly there will be a board evaluation function that will be able to evaluate any 
chess position. 

## Time and space complexity

The alpha-beta pruning algorithm will dominate the time complexity. The worst-case time complexity for alpha-beta
pruning would be O(b<sup>d</sup>) where b is the number of possible moves and d would be the maximum depth of the search. 
In this case, the algorithm wasn't able to prune at all and went through the whole tree up to the maximum depth just like 
a simple minimax search. However, this is not very realistic and only happens if the moves are evaluated from the
worst move to the best move. The goal is to optimize this as much as possible by evaluating the tree starting from the best 
move to the the worst. 

The space complexity will be dominated by the transposition table. We would like to use all the available system 
memory for the transposition table in order to make the searches as efficient as possible. 

## Sources

- [Alpha-Beta](https://www.chessprogramming.org/Alpha-Beta)
- [Iterative Deepening](https://www.chessprogramming.org/Iterative_Deepening)
- [Transposition Table](https://www.chessprogramming.org/Transposition_Table)

## Other information

- University program: Bachelor program in Mathematical Sciences
- Language of the project: English

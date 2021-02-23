# Week 5

This week I realized that I should probably reimplement the way I traverse through the game tree. Currently we always create a new Position for each move but I think it is quite space inefficient. I read that it is quite common to have move/unmove methods that enable the program to have only one state in the memory and then I could make moves and undo moves and traverse the tree in that way. This would save a lot of memory. However, that would require me to rewrite the whole Position class which is already almost 1000 lines of code. I started doing it already but it is not ready for this week so I don't want to release it yet. 

Other things I did was to make the evaluation of the game state better. Now the AI seems to play a lot better and its play looks a bit like a real human players. I also created a custom HashMap class that will be used in the project instead of the Java's HashMap implementation. 

Once I have rewritten the Position class, I will continue with goals I set last week (week 4).

Estimated time used: 6 hours
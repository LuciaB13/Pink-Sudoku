🌸 Pink Sudoku

A Sudoku game I made because I play Sudoku on NYT Games. Every puzzle is randomly generated and has exactly one solution.

Inspiration

I used Kenny Yip Coding's Sudoku tutorial on YouTube as inspiration, then made my own version where a puzzle can be randomly generated using arrays. I was also inspired by my QUB Java coursework on photography images.

I added the generator, the unique solution check, the timer and the new header layout myself.

Run it
bash
javac Sudoku.java App.java
java Sudoku

Pick a number at the bottom, then click an empty tile.

The maths
Part	How it works
Cell to row/column	Cells are numbered 0-80, so row = cell / 9 and col = cell % 9.
3x3 box	(row / 3) * 3 and (col / 3) * 3 give the top-left corner of the box.
Valid move	The number can't already be in the same row, column or box.
Making a solution	Backtracking. Try digits 1-9 in random order, and if there's a dead end, undo and try the next one. There are about 6.67 x 10^21 valid grids [1], so repeats are very unlikely.
Shuffling	Fisher-Yates. Going backwards through the array, swap each item with a random earlier one: j = (int)(Math.random() * (i + 1)).
Making the puzzle	Remove numbers in random order and keep a removal only if there is still exactly 1 solution. Removing 40 leaves 41 clues. The minimum possible for a unique puzzle is 17 [1].
Counting solutions	Another backtracking method that stops at 2, since I only need to know if it's unique.
Checking moves	Compared to the saved solution. A correct move lowers emptyCells, a wrong one adds to errors, and the puzzle is done when emptyCells == 0.
Timer	A Swing Timer adds 1 second every 1000 ms. Minutes and seconds are secs / 60 and secs % 60.
Changing the difficulty

To change how hard it is, change cellsToRemove at the top of Sudoku.java. The more cells you remove, the fewer clues you get, so it's harder.

java
int cellsToRemove = 40; // default

I tried these values:

cellsToRemove	Difficulty
30	Easy
40	Medium (what I have it on)
50	Hard
55	Really hard (takes a bit longer to generate)

If you go too high it might not remove all of them, because it puts a number back if removing it would give more than one solution.

Things I want to add
 A menu to pick easy / medium / hard
 Pencil marks for notes
 A hint button
 Saving my best time
 Whenever a number is selected, its column and row are also highlighted
References
"Mathematics of Sudoku", Wikipedia. https://en.wikipedia.org/wiki/Mathematics_of_Sudoku
Kenny Yip Coding, Sudoku tutorial (YouTube).

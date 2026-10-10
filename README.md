# 🧩 💓 Pink Sudoku

A Sudoku game I made because I love playing Sudoku on NYT Games and I wanted to understand how the maths behind the game! Every puzzle is randomly generated and has exactly one solution.

## Inspiration

I used Kenny Yip Coding's Sudoku tutorial on YouTube as inspiration, then made my own version where a puzzle can be randomly generated using arrays. I was also inspired by my QUB Java coursework on photography images.

I added the generator, the unique solution check, the timer and the new header layout myself.

## Run it

Pick a number at the bottom, then click an empty tile.

## How it Works

The game generates a Sudoku puzzle by filling a grid and removing numbers to create empty cells.

The solution check ensures that removing a number does not leave the puzzle with multiple possible solutions. This helps maintain the rule that every generated puzzle has exactly one solution.

## Changing the difficulty

You can adjust the difficulty by changing cellsToRemove near the top of Sudoku.java.

int cellsToRemove = 40;                           // Default
Fewer cells removed: More clues and an easier starting puzzle.
More cells removed: Fewer clues and a harder puzzle.

If you increase this value too much, the game may remove fewer cells than requested because it keeps numbers in place when removing them would result in multiple solutions. It is proved you need at least 17 starting numbers to solve Sudoku so be warned!!

## Things I want to add

* [ ] A timer
* [ ] A menu to pick easy / medium / hard
* [ ] Pencil marks for notes
* [ ] A hint button
* [ ] Saving my best time
* [ ] Whenever a number is selected; the column and row are also highlighted

## References

* "Mathematics of Sudoku", [Wikipedia](https://en.wikipedia.org/wiki/Mathematics_of_Sudoku)
* Kenny Yip Coding, Sudoku tutorial (YouTube)

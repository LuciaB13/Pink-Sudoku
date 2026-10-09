import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Sudoku {
    class Tile extends JButton {
        int r;
        int c;

        Tile(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    int boardWidth = 600;
    int boardHeight = 650;

    String[] puzzle;
    String[] solution;

    JFrame frame = new JFrame("Sudoku");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel(); // Holds all buttons for sudoku board
    JPanel buttonsPanel = new JPanel();

    JButton numSelected = null;
    int errors = 0;
    int cellsToRemove = 40;
    int emptyCells = 0; // tiles still empty
    JButton refreshPuzzleButton = new JButton("New Puzzle");

    Color headerColor = new Color(224, 80, 135);
    Color backgroundColor = new Color(255, 235, 243);
    Color startTileColor = new Color(255, 205, 224);
    Color emptyTileColor = new Color(255, 250, 252);
    Color buttonColor = new Color(255, 245, 249);
    Color selectedColor = new Color(224, 80, 135);
    Color userNumberColor = new Color(190, 30, 100);
    Color thickLineColor = new Color(150, 40, 90);
    Color thinLineColor = new Color(240, 170, 200);
    Color textColor = new Color(90, 55, 55);
    Color solvedColor = new Color(255, 190, 215);
    Color unsolvedColor = new Color(217, 0, 108);

    Sudoku() {
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Sudoku Errors:  " + errors);
        textPanel.add(textLabel);

        refreshPuzzleButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshPuzzleButton.setFocusable(false);
        refreshPuzzleButton.setBackground(backgroundColor);
        refreshPuzzleButton.setForeground(textColor);
        refreshPuzzleButton.addActionListener(e -> newGame());
        textPanel.add(refreshPuzzleButton);

        textPanel.setBackground(headerColor);
        textPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(9, 9));
        boardPanel.setBackground(backgroundColor);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        generatePuzzle();
        setupTiles();
        frame.add(boardPanel, BorderLayout.CENTER);

        buttonsPanel.setLayout(new GridLayout(1, 9));
        buttonsPanel.setBackground(backgroundColor);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        setupButttons();
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        // Makes the board visible when all components are included
        frame.setVisible(true);

    }

    void setupTiles() {
        emptyCells = 0; // reset the count for each sudoku game

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Tile tile = new Tile(r, c);
                char tileChar = puzzle[r].charAt(c);

                tile.setOpaque(true);
                tile.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                if (tileChar != '-') {
                    tile.setText(String.valueOf(tileChar));
                    tile.setFont(new Font("Arial", Font.BOLD, 22));
                    tile.setForeground(textColor);
                    tile.setBackground(startTileColor);
                } else {
                    tile.setFont(new Font("Arial", Font.PLAIN, 22));
                    tile.setForeground(userNumberColor);
                    tile.setBackground(emptyTileColor);
                    emptyCells++;
                }

                int bottom = (r == 2 || r == 5) ? 5 : 1;
                int right = (c == 2 || c == 5) ? 5 : 1;
                tile.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, bottom, right, thickLineColor),
                        BorderFactory.createLineBorder(thinLineColor)));

                boardPanel.add(tile);

                tile.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        Tile clicked = (Tile) e.getSource();
                        int row = clicked.r;
                        int col = clicked.c;

                        // only do something if a number button has been selected
                        if (numSelected != null) {

                            // if the tile already has a number, ignore the click
                            if (!clicked.getText().isEmpty()) {
                                return;
                            }

                            String numSelectedText = numSelected.getText();
                            String tileSolution = String.valueOf(solution[row].charAt(col));

                            // if the chosen number matches the solution; place it
                            if (tileSolution.equals(numSelectedText)) {
                                clicked.setText(numSelectedText);
                                emptyCells--;

                                // if no empty tiles are left the puzzle is finished
                                if (emptyCells == 0) {
                                    textLabel.setText("Solved! Errors: " + errors);

                                    // turn every tile pink when solved
                                    Component[] allTiles = boardPanel.getComponents();
                                    for (int i = 0; i < allTiles.length; i++) {
                                        allTiles[i].setBackground(solvedColor);
                                    }

                                    // show the dialog after the screen redraws so the colour is visible
                                    SwingUtilities.invokeLater(() -> {
                                        int choice = JOptionPane.showConfirmDialog(
                                                frame,
                                                "You solved it with " + errors + " errors!\nPlay a new puzzle?",
                                                "Puzzle Complete",
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.INFORMATION_MESSAGE);

                                        // if they pressed Yes, start a new game
                                        if (choice == JOptionPane.YES_OPTION) {
                                            newGame();
                                        }
                                    });
                                }
                            } else {
                                errors++;
                                textLabel.setText("Errors: " + errors);

                                // flash the tile hot pink, then back to normal after 0.4 seconds
                                clicked.setBackground(unsolvedColor);
                                Timer timer = new Timer(400, ev -> clicked.setBackground(emptyTileColor));
                                timer.setRepeats(false);
                                timer.start();
                            }
                        }
                    }
                });
            }
        }
    }

    // creates 9 buttons
    void setupButttons() {
        for (int i = 1; i < 10; i++) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.setText(String.valueOf(i));
            button.setFocusable(false);
            button.setOpaque(true);
            button.setBackground(buttonColor);
            button.setForeground(textColor);
            button.setBorder(BorderFactory.createLineBorder(thinLineColor));
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton clicked = (JButton) e.getSource();
                    if (numSelected != null) {
                        numSelected.setBackground(buttonColor);
                        numSelected.setForeground(textColor);
                    }
                    numSelected = clicked;
                    numSelected.setBackground(selectedColor);
                    numSelected.setForeground(Color.WHITE);
                }
            });
        }
    }

    void newGame() {
        generatePuzzle();

        errors = 0;
        textLabel.setText("Errors: " + errors);

        // deselect the number button if one was selected
        if (numSelected != null) {
            numSelected.setBackground(buttonColor);
            numSelected.setForeground(textColor);
            numSelected = null;
        }

        // throw away the old tiles and build new ones from the new puzzle
        boardPanel.removeAll();
        setupTiles();
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    void generatePuzzle() {
        int[][] grid = new int[9][9];
        fillGrid(grid, 0);

        // save the full solution as strings
        solution = new String[9];
        for (int r = 0; r < 9; r++) {
            String row = "";
            for (int c = 0; c < 9; c++) {
                row += grid[r][c];
            }
            solution[r] = row;
        }

        // make a shuffled list of the 81 cell positions (0 to 80)
        int[] order = new int[81];
        for (int i = 0; i < 81; i++) {
            order[i] = i;
        }
        shuffle(order);

        // remove numbers one at a time until we have removed enough
        int removed = 0;
        int i = 0;
        while (removed < cellsToRemove && i < 81) {
            int r = order[i] / 9;
            int c = order[i] % 9;
            int backup = grid[r][c];
            grid[r][c] = 0;

            // if the puzzle no longer has exactly 1 solution, put the number back
            if (countSolutions(grid, 0) != 1) {
                grid[r][c] = backup;
            } else {
                removed++;
            }
            i++;
        }

        // turn the grid into strings; using - for empty cells
        puzzle = new String[9];
        for (int r = 0; r < 9; r++) {
            String row = "";
            for (int c = 0; c < 9; c++) {
                // if the cell is 0 it is empty so show -; otherwise show the number
                if (grid[r][c] == 0) {
                    row += "-";
                } else {
                    row += grid[r][c];
                }
            }
            puzzle[r] = row;
        }
    }

    // randomly mixes up the order of an array
    void shuffle(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // fills the grid with a random valid solution
    boolean fillGrid(int[][] grid, int cell) {

        if (cell == 81) {
            return true;
        }

        int r = cell / 9;
        int c = cell % 9;

        int[] nums = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        shuffle(nums);

        boolean solved = false;
        int i = 0;
        while (!solved && i < 9) {

            // only try this number if it doesn't break a sudoku rule
            if (isValid(grid, r, c, nums[i])) {
                grid[r][c] = nums[i];

                // if the rest of the grid can be filled, we are done
                if (fillGrid(grid, cell + 1)) {
                    solved = true;
                } else {
                    grid[r][c] = 0; // dead end, undo and try the next number
                }
            }
            i++;
        }
        return solved;
    }

    // counts how many solutions the puzzle has (stops counting at 2)
    int countSolutions(int[][] grid, int cell) {

        if (cell == 81) {
            return 1;
        }

        int r = cell / 9;
        int c = cell % 9;

        if (grid[r][c] != 0) {
            return countSolutions(grid, cell + 1);
        }

        int count = 0;
        int n = 1;
        // keep trying numbers until we've tried all 9 or found 2 solutions
        while (n <= 9 && count < 2) {
            // only try this number if it doesn't break a sudoku rule
            if (isValid(grid, r, c, n)) {
                grid[r][c] = n;
                count += countSolutions(grid, cell + 1);
                grid[r][c] = 0;
            }
            n++;
        }
        return count;
    }

    // checks if number n can go at row r, column c
    boolean isValid(int[][] grid, int r, int c, int n) {
        boolean valid = true;

        // check the row and the column
        for (int i = 0; i < 9; i++) {
            // n is already in this row
            if (grid[r][i] == n) {
                valid = false;
            }
            // n is already in this column
            if (grid[i][c] == n) {
                valid = false;
            }
        }

        int boxRow = (r / 3) * 3;
        int boxCol = (c / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // n is already in this 3x3 box
                if (grid[boxRow + i][boxCol + j] == n) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(Sudoku::new);
    }

}
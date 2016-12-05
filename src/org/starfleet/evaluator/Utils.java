package org.starfleet.evaluator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Utils {

	public static final ArrayList<String> directions = new ArrayList<String>() {
		
		private static final long serialVersionUID = 1L;

		{
			add("north");
			add("south");
			add("east");
			add("west");
		}
	};

	public static final ArrayList<String> firingPattern = new ArrayList<String>() {
		
		private static final long serialVersionUID = 1L;

		{
			add("alpha");
			add("beta");
			add("gamma");
			add("delta");
		}
	};

	public static char[][] readGridFromFieldFile(String string) throws FileNotFoundException {

		Scanner input = new Scanner(new File(string));
		int rows = 0;
		int columns = 0;
		boolean columnSet = false;
		String nextLine;
		while (input.hasNextLine()) {
			++rows;
			nextLine = input.nextLine().trim();
			if (!columnSet) {
				columns = nextLine.length();
				columnSet = true;
			}
		}
		input.close();
		char[][] grid = new char[rows][columns];
		input = new Scanner(new File(string));
		for (int i = 0; i < rows; i++) {
			if (input.hasNextLine()) {
				grid[i] = input.nextLine().trim().toCharArray();

			}
		}
		input.close();
		return grid;

	}

	public static String[] readStepsFromScriptFile(String string) throws FileNotFoundException {
		Scanner input = new Scanner(new File(string));
		List<String> steps = new ArrayList<String>();
		while (input.hasNextLine()) {
			steps.add(input.nextLine().trim());
		}
		input.close();
		String[] stepsArray = new String[steps.size()];
		stepsArray = steps.toArray(stepsArray);
		return stepsArray;
	}

	public static int getCuboidHeight(char[][] grid) {
		int max = 0;
		int rows = grid.length;
		int cols = grid[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int depth = getDepthFromCharacter(grid[i][j]);
				if (max <= depth) {
					max = depth;
				}
			}
		}
		return max;
	}

	public static int getDepthFromCharacter(char depthChar) {
		int depth = 0;
		if ((depthChar >= 97) && (depthChar <= 122)) {
			depth = depthChar - 96;
			return depth;
		}
		if ((depthChar >= 65) && (depthChar <= 90)) {
			depth = depthChar - 38;
			return depth;
		}
		return depth;
	}

	public static void printGrid(char[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;
		System.out.println();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(grid[i][j]);

			}
			System.out.println();
		}
	}

	public static char[][] fireAlphaPattern(int row, int col, char[][] grid, HashSet<Mine> mineSet) {

		int rows = grid.length;
		int cols = grid[0].length;
		if (((row - 1) >= 0) && ((col - 1) >= 0)) {
			grid[row - 1][col - 1] = '.';
			removeFromMineSet(row - 1, col - 1, mineSet);

		}

		if (((row - 1) >= 0) && ((col + 1) <= (cols - 1))) {
			grid[row - 1][col + 1] = '.';
			removeFromMineSet(row - 1, col + 1, mineSet);
		}
		if (((row + 1) <= (rows - 1)) && ((col - 1) >= 0)) {
			grid[row + 1][col - 1] = '.';
			removeFromMineSet(row + 1, col - 1, mineSet);
		}
		if (((row + 1) <= (rows - 1)) && ((col + 1) <= (cols - 1))) {
			grid[row + 1][col + 1] = '.';
			removeFromMineSet(row + 1, col + 1, mineSet);
		}
		return grid;
	}

	public static char[][] fireBetaPattern(int row, int col, char[][] grid, HashSet<Mine> mineSet) {

		int rows = grid.length;
		int cols = grid[0].length;
		if ((col - 1) >= 0) {
			grid[row][col - 1] = '.';
			removeFromMineSet(row, col - 1, mineSet);
		}
		if ((col + 1) <= (cols - 1)) {
			grid[row][col + 1] = '.';
			removeFromMineSet(row, col + 1, mineSet);
		}
		if ((row - 1) >= 0) {
			grid[row - 1][col] = '.';
			removeFromMineSet(row - 1, col, mineSet);
		}
		if ((row + 1) <= (rows - 1)) {
			grid[row + 1][col] = '.';
			removeFromMineSet(row + 1, col, mineSet);
		}
		return grid;

	}

	public static char[][] fireGammaPattern(int row, int col, char[][] grid, HashSet<Mine> mineSet) {

		int cols = grid[0].length;
		if ((col - 1) >= 0) {
			grid[row][col - 1] = '.';
			removeFromMineSet(row, col - 1, mineSet);
		}
		if ((col + 1) <= (cols - 1)) {
			grid[row][col + 1] = '.';
			removeFromMineSet(row, col + 1, mineSet);
		}
		grid[row][col] = '.';
		removeFromMineSet(row, col, mineSet);
		return grid;

	}

	public static char[][] fireDeltaPattern(int row, int col, char[][] grid, HashSet<Mine> mineSet) {

		int rows = grid.length;

		if ((row - 1) >= 0) {
			grid[row - 1][col] = '.';
			removeFromMineSet(row - 1, col, mineSet);
		}
		if ((row + 1) <= (rows - 1)) {
			grid[row + 1][col] = '.';
			removeFromMineSet(row + 1, col, mineSet);
		}

		grid[row][col] = '.';
		removeFromMineSet(row, col, mineSet);

		return grid;

	}

	private static void removeFromMineSet(int i, int j, HashSet<Mine> mineSet) {
		Mine mine = new Mine();
		mine.row = i;
		mine.col = j;
		if (mineSet.contains(mine)) {
			mineSet.remove(mine);
		}
	}

	public static HashSet<Mine> getMines(char[][] grid) {

		HashSet<Mine> mines = new HashSet<Mine>();
		int rows = grid.length;
		int cols = grid[0].length;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (grid[i][j] != '.') {
					Mine mineSet = new Mine();
					mineSet.row = i;
					mineSet.col = j;
					mines.add(mineSet);
				}
			}
		}
		return mines;
	}

	public static char[][] decrementDistance(HashSet<Mine> mineSet, char[][] grid) {
		Iterator<Mine> itr = mineSet.iterator();
		while (itr.hasNext()) {
			Mine mine = itr.next();
			grid[mine.row][mine.col] = --grid[mine.row][mine.col];
		}
		return grid;
	}

	public static boolean checkMiss(char[][] grid, HashSet<Mine> mineSet) {
		Iterator<Mine> itr = mineSet.iterator();
		char baseChar = 'a';
		boolean isMissed = false;
		while (itr.hasNext()) {
			Mine mine = itr.next();
			if (baseChar == (char) (grid[mine.row][mine.col] +1)) {
				isMissed = true;
				grid[mine.row][mine.col] = '*';
			}

		}

		return isMissed;
	}

	public static void printScore(boolean success, int instructionStepsLeft, int points, int movementPointsDeduction,
			int firingPointsDeduction) {

		if (success) {
			if (instructionStepsLeft > 0) {
				System.out.println("Pass (1)");
			} else {
				System.out.println("Pass (" + (points - movementPointsDeduction - firingPointsDeduction) + ")");
			}
		} else {
			System.out.println("Fail (0)");
		}

	}

	public static void validateInputArguments(String[] args) {

		if ((args == null) || (args.length == 0))
			throw new IllegalArgumentException("Input args cannot be null or empty!");

		if (args.length != 2)
			throw new IllegalArgumentException("In order to successfully evaluate your program. "
					+ "This evaluation program needs two filename as arguments(inorder) : field & script.");
	}

	static void evaluate(int row, int col, int cuboidHeight, HashSet<Mine> mineSet, char[][] grid, String[] steps) {

		boolean success = false;
		int step = 1;
		int instructionsSize = steps.length;
		int instructionStepsLeft = steps.length;
		int rows = grid.length;
		int cols = grid[0].length;
		int minesSize = mineSet.size();
		int points = 10 * minesSize;
		int maxFiringPointsDeductionLimit = 5 * minesSize;
		int firingPointsDeduction = 0;
		int movementPointsDeduction = 0;
		int maxMovmentPointsDeductionLimit = 3 * minesSize;

		for (int i = 0; i < cuboidHeight; i++) {

			if (mineSet.isEmpty()) {
				success = true;
				break;
			}

			if (instructionStepsLeft == 0)
				break;

			System.out.println("\nStep-" + step);
			Utils.printGrid(grid);

			String instruction = steps[instructionsSize - instructionStepsLeft];
			System.out.println("\n" + instruction);

			String[] splits = instruction.split("\\s+");
			int instructionSize = splits.length;
			if (instructionSize > 2) {
				throw new IllegalArgumentException(
						"Instruction either related to direction or firing cannot be more than 2 per step.");
			}

			for (String command : splits) {

				if (Utils.directions.contains(command)) {

					if (movementPointsDeduction < maxMovmentPointsDeductionLimit) {
						movementPointsDeduction = movementPointsDeduction + 2;
					}

					switch (command) {
					case "north":
						if ((row - 1) >= 0)
							row--;
						break;
					case "south":
						if ((row + 1) <= rows - 1)
							row++;
						break;
					case "east":
						if ((col + 1) <= cols - 1)
							col++;
						break;
					case "west":
						if ((col - 1) >= 0)
							col--;
						break;
					}

				}

				if (Utils.firingPattern.contains(command)) {

					if (firingPointsDeduction < maxFiringPointsDeductionLimit) {
						firingPointsDeduction = firingPointsDeduction + 5;
					}

					switch (command) {
					case "alpha":
						grid = Utils.fireAlphaPattern(row, col, grid, mineSet);
						break;
					case "beta":
						grid = Utils.fireBetaPattern(row, col, grid, mineSet);
						break;
					case "gamma":
						grid = Utils.fireGammaPattern(row, col, grid, mineSet);
						break;
					case "delta":
						grid = Utils.fireDeltaPattern(row, col, grid, mineSet);
						break;
					}
				}

			}
			grid = Utils.decrementDistance(mineSet, grid);
			if (Utils.checkMiss(grid, mineSet)) {
				break;
			}
			step++;
			instructionStepsLeft--;

		}
		
		printGrid(grid);
		System.out.println(row+"  "+col);
		Utils.printScore(success, instructionStepsLeft, points, movementPointsDeduction, firingPointsDeduction);

	}

}

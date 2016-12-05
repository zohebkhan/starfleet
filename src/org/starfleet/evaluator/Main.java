package org.starfleet.evaluator;

import java.util.HashSet;

public class Main {

	public static void main(String[] args) {

		try {
			Utils.validateInputArguments(args);
			char[][] grid = Utils.readGridFromFieldFile(args[0]);
			String[] steps = Utils.readStepsFromScriptFile(args[1]);
			int cuboidHeight = Utils.getCuboidHeight(grid);
			HashSet<Mine> mineSet = Utils.getMines(grid);
			int startRow = grid.length / 2;
			int startCol = grid[0].length / 2;
			Utils.evaluate(startRow, startCol, cuboidHeight, mineSet, grid, steps);
		} catch (IllegalArgumentException illegalArgumentException) {
			illegalArgumentException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}

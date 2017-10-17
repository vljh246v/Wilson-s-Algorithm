import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazePrinter {

	private Cell[][] cells;
	private int cellsRowCount;
	private int cellsColCount;
	private int totalBlankCount;

	public MazePrinter() {
		cells = new Cell[25 + 2][25 + 2];
		initCells();
	}

	public MazePrinter(int row, int col) {
		cells = new Cell[row + 2][col + 2];
		initCells();
	}

	public void initCells() {
		cellsRowCount = cells.length;
		cellsColCount = cells[0].length;
		

		System.out.println("cellsRowCount : " + (cellsRowCount - 2) + ", cellsColCount : " + (cellsColCount - 2));

		for (int i = 0; i < cellsRowCount; i++) {
			for (int j = 0; j < cellsColCount; j++) {
				cells[i][j] = new Cell();
				cells[i][j].setRowNumber(i);
				cells[i][j].setColNumber(j);
				if ((i == 0 || i == cellsRowCount - 1) || (j == 0 || j == cellsColCount - 1)) {
					cells[i][j].setUsable(false);
				} else {
					cells[i][j].setUsable(true);
					cells[i][j].setBlank(true);
					cells[i][j].setWall((byte) 15);
				}
			}
		}

		Cell startCell = selectCell(true);
		startCell.setBlank(false);
		totalBlankCount = (cellsRowCount - 2) * (cellsColCount - 2) - 1;
		//drawCurrentMaze();
	}

	public void drawCurrentMaze() {

		for (int i = 0; i < cellsRowCount; i++) {
			for (int j = 0; j < cellsColCount; j++) {
				if(cells[i][j].isUsable() == false) {
					continue;
				}
				if ((cells[i][j].getWall() & 8) == 8) {
					System.out.print(String.format("%-5s", "   -"));
				} else {
					System.out.print(String.format("%-5s", "    "));
				}
			}
			System.out.println();
			for (int j = 0; j < cellsColCount; j++) {
				if(cells[i][j].isUsable() == false) {
					continue;
				}
				String temp = "";
				if ((cells[i][j].getWall() & 4) == 4) {
					temp += "|";
				} else {
					temp += " ";
				}

				if (cells[i][j].isUsable()) {
					if (cells[i][j].isBlank()) {
						temp += "o";
					} else {
						temp += " ";
					}

				} else {
					temp += " ";
				}

				if ((cells[i][j].getWall() & 1) == 1) {
					temp += "|";
				} else {
					temp += " ";
				}
				temp = " " + temp;
				System.out.print(String.format("%5s", temp));

			}
			System.out.println();
			for (int j = 0; j < cellsColCount; j++) {
				if(cells[i][j].isUsable() == false) {
					continue;
				}
				if ((cells[i][j].getWall() & 2) == 2) {
					System.out.print(String.format("%-5s", "   -"));
				} else {
					System.out.print(String.format("%-5s", "    "));
				}
			}
			System.out.println();
		}
	}

	public Cell selectCell(boolean blankFlag) {
		List<Cell> cellList = new ArrayList<>();

		for (int i = 1; i < cellsRowCount - 1; i++) {
			for (int j = 1; j < cellsColCount - 1; j++) {
				if (cells[i][j].isBlank() == blankFlag) {
					cellList.add(cells[i][j]);
				}
			}
		}

		Random rnd = new Random();
		return cellList.get(rnd.nextInt(cellList.size()));
	}

	public Cell selectNextCell(Cell currentCell) {

		List<Cell> possibleDirectCells = new ArrayList<>();

		if (cells[currentCell.getRowNumber() - 1][currentCell.getColNumber()].isUsable()) {
			possibleDirectCells.add(cells[currentCell.getRowNumber() - 1][currentCell.getColNumber()]);
		}

		if (cells[currentCell.getRowNumber()][currentCell.getColNumber() - 1].isUsable()) {
			possibleDirectCells.add(cells[currentCell.getRowNumber()][currentCell.getColNumber() - 1]);
		}

		if (cells[currentCell.getRowNumber()][currentCell.getColNumber() + 1].isUsable()) {
			possibleDirectCells.add(cells[currentCell.getRowNumber()][currentCell.getColNumber() + 1]);
		}

		if (cells[currentCell.getRowNumber() + 1][currentCell.getColNumber()].isUsable()) {
			possibleDirectCells.add(cells[currentCell.getRowNumber() + 1][currentCell.getColNumber()]);
		}

		Random rnd = new Random();
		return possibleDirectCells.get(rnd.nextInt(possibleDirectCells.size()));
	}

	public void setRoute(Cell startCell) {
		
		// 0 No direct any where
		// 1 east
		// 2 south
		// 3 west
		// 4 north

		// 0000 No walls in any direction.
		// 0001 Walls east
		// 0010 Walls south
		// 0100 Walls west
		// 1000 Walls north
		// ex. 1001 Walls east and north
		
		Cell currentCell = startCell;
		Cell nextCell = null;
		while (true) {
			nextCell = selectNextCell(currentCell);
			currentCell.setDirect(setCurrentCellDirectInfo(nextCell, currentCell));
			currentCell = nextCell;
			
			if(currentCell.isBlank() == false) {
				break;
			}
		}
		
		currentCell = startCell;
		while(true) {
			
			if(currentCell.getDirect() == 1) {
				nextCell = cells[currentCell.getRowNumber()][currentCell.getColNumber() + 1];
				currentCell.setWall((byte) (currentCell.getWall() ^ 1));
				nextCell.setWall((byte) (nextCell.getWall() ^ 4));
			}else if(currentCell.getDirect() == 2) {
				nextCell = cells[currentCell.getRowNumber() + 1][currentCell.getColNumber()];
				currentCell.setWall((byte) (currentCell.getWall() ^ 2));
				nextCell.setWall((byte) (nextCell.getWall() ^ 8));
			}else if(currentCell.getDirect() == 3) {
				nextCell = cells[currentCell.getRowNumber()][currentCell.getColNumber() - 1];
				currentCell.setWall((byte) (currentCell.getWall() ^ 4));
				nextCell.setWall((byte) (nextCell.getWall() ^ 1));
			}else if(currentCell.getDirect() == 4) {
				nextCell = cells[currentCell.getRowNumber() - 1][currentCell.getColNumber()];
				currentCell.setWall((byte) (currentCell.getWall() ^ 8));
				nextCell.setWall((byte) (nextCell.getWall() ^ 2));
			}
			
			currentCell.setBlank(false);
			totalBlankCount--;
			currentCell = nextCell;
			
			if(currentCell.isBlank() == false) {
				break;
			}
		}
	}
	
	public void travelMaze() {
		while(totalBlankCount > 0) {
			setRoute(selectCell(true));
		}
	}

	public byte setCurrentCellDirectInfo(Cell nextCell, Cell currentCell) {
		
		// 0 No direct any where
		// 1 east
		// 2 south
		// 3 west
		// 4 north
		
		int rowCheckNumber = nextCell.getRowNumber() - currentCell.getRowNumber();
		int colCheckNumber = nextCell.getColNumber() - currentCell.getColNumber();

		byte direct = 0;
		
		if (rowCheckNumber == 0 && colCheckNumber == 1) { // east
			direct = 1;
		} else if (rowCheckNumber == 1 && colCheckNumber == 0) { // south
			direct = 2;	
		} else if (rowCheckNumber == 0 && colCheckNumber == -1) { // west
			direct = 3;
		} else if (rowCheckNumber == -1 && colCheckNumber == 0) { // north
			direct = 4;
		}

		return direct;
	}
}

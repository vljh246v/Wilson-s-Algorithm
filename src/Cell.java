
public class Cell {
	private boolean isUsable;

	// blank true is not route
	private boolean isBlank;
	private int rowNumber;
	private int colNumber;

	// 0 No direct any where
	// 1 east
	// 2 south
	// 3 west
	// 4 north
	private byte direct = 0;

	// 0000 No walls in any direction.
	// 0001 Walls east
	// 0010 Walls south
	// 0100 Walls west
	// 1000 Walls north
	// ex. 1001 Walls east and north

	private byte wall = 15;

	public boolean isUsable() {
		return isUsable;
	}

	public void setUsable(boolean isUsable) {
		this.isUsable = isUsable;
	}

	public boolean isBlank() {
		return isBlank;
	}

	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}

	public byte getDirect() {
		return direct;
	}

	public void setDirect(byte direct) {
		this.direct = direct;
	}

	public byte getWall() {
		return wall;
	}

	public void setWall(byte wall) {
		this.wall = wall;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColNumber() {
		return colNumber;
	}

	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}

	@Override
	public String toString() {
		return "Cell [isUsable=" + isUsable + ", isBlank=" + isBlank + ", rowNumber=" + rowNumber + ", colNumber="
				+ colNumber + ", direct=" + direct + ", wall=" + wall + "]";
	}

}

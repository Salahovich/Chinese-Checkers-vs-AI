package game;

import java.io.Serializable;

public class Hole implements Serializable{

	private char color;
	private String number;
	private int row, column;
	private Marble myMarble;
	
	public Hole(char color, int row, int column, Marble myMarble) {
		this.color = color;
		this.row = row;
		this.column = column;
		this.myMarble = myMarble;
		this.number = generateNumber();
	}
	
	// Generates the code for the hole
	private String generateNumber () {
		int num = 97 + this.row;
		char symbol = (char) num;
		return Character.toString(symbol) + this.column;
	}

	public int getRow() {
		return this.row;
	}
	public int getColumn() {
		return this.column;
	}
	public char getColor() {
		return this.color;
	}
	
	public String getNumber() {
		return this.number; 
	}
	
	public Marble getMarble() {
		return this.myMarble;
	}
	
	public void setMarble(Marble myMarble) {
		this.myMarble = myMarble;
	}
	
	public void reset() {
		this.myMarble = null;
	}
	
	// Displaying the hole
	public String toString() {
		if(myMarble != null) {
			return myMarble.getCode();
		}else {
			return this.number;
		}
	}
	
}

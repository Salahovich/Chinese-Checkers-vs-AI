package game;

import java.io.Serializable;

public class Board implements Serializable{

	private Hole[][] myHoles;
	private int rows=17,columns=25;
	private Marble[] redMarbles;
	private Marble[] greenMarbles;
	private Hole[] redHoles;
	private Hole[] greenHoles;
	public Board() {
		myHoles = new Hole[rows][columns]; 
		redMarbles = new Marble[10];
		greenMarbles = new Marble[10];
		redHoles = new Hole[10];
		greenHoles = new Hole[10];
		assignRedHoles();
		assignGreenHoles();
		assignOtherHoles();
	}
	
	
	// assigns red holes and marbles on the board
	private void assignRedHoles() {
		int startRow = rows-1, startColumn = columns/2;
		int endColumn = columns/2;
		int colIncrement = 2, marbleNumber=0;
		int i;
		while(marbleNumber < 10) {
			i = startColumn;
			while(i <= endColumn) {
				Marble redMarble = new Marble('R',marbleNumber);
				redMarbles[marbleNumber] = redMarble;
				this.myHoles[startRow][i] = new Hole('R',startRow,i,redMarble);
				redMarble.setHole(this.myHoles[startRow][i]);
				redHoles[marbleNumber] = this.myHoles[startRow][i];
				i += colIncrement;
				marbleNumber++;

			}
			startRow--;
			startColumn--;
			endColumn++;
		}
	}
	
	// assigns green holes and marbles on the board
	private void assignGreenHoles() {
		int startRow = 0, startColumn = columns/2;
		int endColumn = columns/2;
		int colIncrement = 2, marbleNumber=0;
		int i;
		while(marbleNumber < 10) {
			i = startColumn;
			while(i <= endColumn) {
				Marble greenMarble = new Marble('G',marbleNumber);
				greenMarbles[marbleNumber] = greenMarble;
				this.myHoles[startRow][i] = new Hole('G',startRow,i,greenMarble);
				greenMarble.setHole(this.myHoles[startRow][i]);
				greenHoles[marbleNumber] = this.myHoles[startRow][i];
				i += colIncrement;
				marbleNumber++;
			}
			startRow++;
			startColumn--;
			endColumn++;
		}
	}
	
	// assign empty holes on the board 
	private void assignOtherHoles() {
		int startRow = 4, endRow = 13;
		int startColumn = 0, endColumn = this.columns;
		int colStartIncrement = 1,colEndIncrement = -1;
		
		for(int i=startRow; i<endRow; i++) {
			for(int j=startColumn; j<endColumn; j+=2) {
				this.myHoles[i][j] = new Hole('N',i,j,null);
			}
			if(i == this.rows/2) {
				colStartIncrement = -1;
				colEndIncrement = 1;
			}
			startColumn += colStartIncrement;
			endColumn += colEndIncrement;
		}
	}
	
	public Marble[] getRedMarbles() {
		return this.redMarbles;
	}
	
	public Marble[] getGreenMarbles() {
		return this.greenMarbles;
	}
	
	public Hole getHole(int i, int j) {
		if(i >= this.rows || j >= this.columns || i < 0 || j < 0)
			return null;
		return myHoles[i][j];
	}
	
	public Hole[] getRedHoles() {
		return this.redHoles;
	}
	
	public Hole[] getGreenHoles() {
		return this.greenHoles;
	}
	
	public void Display() {
		for(int i=0; i<this.rows; i++) {
			for(int j=0; j<this.columns; j++) {
				if(this.myHoles[i][j] != null) {
					System.out.print(myHoles[i][j].toString());
				}else
					System.out.print("  ");
			}
			System.out.println();
		}
	}

}

package game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameEngine implements Serializable{

	Board myBoard;
	Player firstPlayer,secondPlayer;
	char firstPlayerColor, secondPlayerColor;
	int verticalEvaluation=0;
	int horizontalEvaluation=0;
	boolean playerOneWin = false, playerTwoWin = false;
	
	public GameEngine(char firstColor, char secondColor) {
		this.firstPlayerColor = firstColor;
		this.secondPlayerColor = secondColor;
		this.myBoard = new Board();
		if(firstColor == 'R') {
			this.firstPlayer = new Player(firstColor, myBoard.getRedMarbles());
			this.secondPlayer = new Player(secondColor, myBoard.getGreenMarbles());
		}
		else {
			this.firstPlayer = new Player(firstColor, myBoard.getGreenMarbles());
			this.secondPlayer = new Player(secondColor, myBoard.getRedMarbles());			
		}
	}
	
	
	// main method to detect the next move for the marble
	public ArrayList<Hole> detectNextMove(Marble iMarble, boolean series){
		int i = iMarble.getHole().getRow();
		int j = iMarble.getHole().getColumn();
		ArrayList<Hole> nextMoves = new ArrayList<Hole>();
		// top left adjacent hole
		if(myBoard.getHole(i-1, j-1) != null) {
			if(!series) {
				if(myBoard.getHole(i-1, j-1).getMarble() == null) {
					nextMoves.add(myBoard.getHole(i-1, j-1));
				}
				else {
					if(myBoard.getHole(i-2, j-2) != null) {
						if(myBoard.getHole(i-2, j-2).getMarble() == null) {							
							nextMoves.add(myBoard.getHole(i-2, j-2));
						}
					}
				}
			}else {
				if(myBoard.getHole(i-1, j-1).getMarble() != null && myBoard.getHole(i-2, j-2) != null) {
					if(myBoard.getHole(i-2, j-2).getMarble() == null)
						nextMoves.add(myBoard.getHole(i-2, j-2));
				}
			}
		}
		// top right adjacent hole
		if(myBoard.getHole(i-1, j+1) != null) {
			if(!series) {				
				if(myBoard.getHole(i-1, j+1).getMarble() == null)
					nextMoves.add(myBoard.getHole(i-1, j+1));
				else {
					if(myBoard.getHole(i-2, j+2) != null) {
						if(myBoard.getHole(i-2, j+2).getMarble() == null)
							nextMoves.add(myBoard.getHole(i-2, j+2));
					}
				}
			}else {
				if(myBoard.getHole(i-1, j+1).getMarble() != null && myBoard.getHole(i-2, j+2) != null) {
					if(myBoard.getHole(i-2, j+2).getMarble() == null)
						nextMoves.add(myBoard.getHole(i-2, j+2));
				}
			}
		}
		// left adjacent hole 
		if(myBoard.getHole(i, j+2) != null) {
			if(!series) {				
				if(myBoard.getHole(i, j+2).getMarble() == null)
					nextMoves.add(myBoard.getHole(i, j+2));
				else {
					if(myBoard.getHole(i, j+4) != null) {
						if(myBoard.getHole(i, j+4).getMarble() == null)
							nextMoves.add(myBoard.getHole(i, j+4));
					}
				}
			}else {
				if(myBoard.getHole(i, j+2).getMarble() != null && myBoard.getHole(i, j+4) != null) {
					if(myBoard.getHole(i, j+4).getMarble() == null)
						nextMoves.add(myBoard.getHole(i, j+4));
				}
			}
		}
		// left adjacent hole 
		if(myBoard.getHole(i, j-2) != null) {
			if(!series) {				
				if(myBoard.getHole(i, j-2).getMarble() == null)
					nextMoves.add(myBoard.getHole(i, j-2));
				else {
					if(myBoard.getHole(i, j-4) != null) {
						if(myBoard.getHole(i, j-4).getMarble() == null)
							nextMoves.add(myBoard.getHole(i, j-4));
					}
				}
			}else {
				if(myBoard.getHole(i, j-2).getMarble() != null && myBoard.getHole(i, j-4) != null) {
					if(myBoard.getHole(i, j-4).getMarble() == null)
						nextMoves.add(myBoard.getHole(i, j-4));
				}
			}
		}
		// bottom left adjacent hole
		if(myBoard.getHole(i+1, j-1) != null) {
			if(!series) {				
				if(myBoard.getHole(i+1, j-1).getMarble() == null)
					nextMoves.add(myBoard.getHole(i+1, j-1));
				else {
					if(myBoard.getHole(i+2, j-2) != null) {
						if(myBoard.getHole(i+2, j-2).getMarble() == null)
							nextMoves.add(myBoard.getHole(i+2, j-2));
					}
				}
			}else {
				if(myBoard.getHole(i+1, j-1).getMarble() != null && myBoard.getHole(i+2, j-2) != null) {
					if(myBoard.getHole(i+2, j-2).getMarble() == null)
						nextMoves.add(myBoard.getHole(i+2, j-2));
				}
			}
		}
		// bottom right adjacent hole
		if(myBoard.getHole(i+1, j+1) != null) {
			if(!series) {				
				if(myBoard.getHole(i+1, j+1).getMarble() == null)
					nextMoves.add(myBoard.getHole(i+1, j+1));
				else {
					if(myBoard.getHole(i+2, j+2) != null) {
						if(myBoard.getHole(i+2, j+2).getMarble() == null)
							nextMoves.add(myBoard.getHole(i+2, j+2));
					}
				}
			}else {
				if(myBoard.getHole(i+1, j+1).getMarble() != null && myBoard.getHole(i+2, j+2) != null) {
					if(myBoard.getHole(i+2, j+2).getMarble() == null)
						nextMoves.add(myBoard.getHole(i+2, j+2));
				}
			}
		}
		iMarble.setNextMoves(nextMoves);
		return nextMoves;	
	}
	
	// player one utility methods
	public void playerOneValidMoves() {
		firstPlayer.displayMoves();
	}
	
	public void playerOneTurn(boolean serial) {

		for(Marble iMarble : firstPlayer.getMarbles()) {
				this.detectNextMove(iMarble, serial);
		}
	}
	
	public ArrayList<Marble> playerOneValidMarbles(){
		return firstPlayer.validMarbles();
	}

	// winning checking for player 1
	public boolean palyerOneHasWon() {
		for(Marble iMarble : firstPlayer.getMarbles()) {
			if(iMarble.getHole().getColor() != this.secondPlayerColor)
				return false;
		}
		return true;
	}
	
	// player Two utility methods
	public void playerTwoValidMoves() {
		secondPlayer.displayMoves();
	}
		
	public void playerTwoTurn(boolean serial) {
		for(Marble iMarble : secondPlayer.getMarbles()) {
				this.detectNextMove(iMarble, serial);
		}
	}
	
	public ArrayList<Marble> playerTwoValidMarbles(){
		return secondPlayer.validMarbles();
	}
	
	// winning checking for player 2
	public boolean palyerTwoHasWon() {
		for(Marble iMarble : secondPlayer.getMarbles()) {
			if(iMarble.getHole().getColor() != this.firstPlayerColor)
				return false;
		}
		return true;
	}

	
	// winning checking method
	public boolean isGameEnded() {
		if(palyerOneHasWon()) {
			this.playerOneWin  = true;
			return true;
		}else if(palyerTwoHasWon()) {
			this.playerTwoWin = true;
			return true;
		}
		return false;
	}
	
	
	public void displayBoard() {
		myBoard.Display();
	}
	
	public void setVerticalEvaluation(int num) {
		this.verticalEvaluation = num;
	}
	public void setHorizontalEvaluation(int num) {
		this.horizontalEvaluation = num;
	}
	
	public boolean inZone(Marble iMarble) {
		if(iMarble.getColor() == 'R') {
			if(iMarble.getHole().getRow() >= 0 && iMarble.getHole().getRow() <= 3) {
				return true;
			}
			return false;
		}else {
			if(iMarble.getHole().getRow() >= 13 && iMarble.getHole().getRow() <= 16) {
				return true;
			}
			return false;
		}
	}
	
	// a method to deep clone the object using serialization for MiniMax purposes
	public GameEngine deepClone() {
		Object object = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			bos.close();
			byte[] byteData = bos.toByteArray();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			object = new ObjectInputStream(bais).readObject();
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (GameEngine) object;
	}
}

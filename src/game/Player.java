package game;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{

	private char color;
	private Marble[] myMarbles;
	
	public Player(char color, Marble[] marbles) {
		this.myMarbles = marbles;
		this.color = color;
	}
	
	
	public Marble[] getMarbles() {
		return this.myMarbles;
	}
	
	
	// display the available moves for the player
	public void displayMoves(){
		for(Marble iMarble: myMarbles) {
			if(iMarble.getNextMoves().size() != 0) {
				System.out.println("You can move " + iMarble.getCode() + " to " + iMarble.getNextMoves().toString());
			}
		}
	}
	
	// display the valid marbles to move
	public ArrayList<Marble> validMarbles(){
		ArrayList<Marble> valid = new ArrayList<>();
		for(Marble iMarble: myMarbles) {
			if(iMarble.getNextMoves().size() != 0) {
				valid.add(iMarble);
			}
		}
		return valid;
	}
	
}

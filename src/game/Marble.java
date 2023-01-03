package game;

import java.io.Serializable;
import java.util.ArrayList;

public class Marble implements Serializable{

	private char color;
	private int number;
	private String code;
	private Hole myHole;
	private ArrayList<Hole> nextMoves;
	
	public Marble(char color, int number) {
		this.color = color;
		this.number = number;
		this.code = Character.toString(color) + this.number;
		nextMoves = new ArrayList<Hole>();
	}
	
	public Hole getHole() {
		return this.myHole;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public char getColor() {
		return this.color;
	}
	
	public String getCode() {
		return this.code;
	}
	public ArrayList<Hole> getNextMoves(){
		return this.nextMoves;
	}
	
	public void setNextMoves(ArrayList<Hole> myMoves) {
		this.nextMoves = myMoves;
	}
	
	public void setHole(Hole myHole) {
		this.myHole = myHole;
	}
	
}

package game;

import java.util.ArrayList;

public class AIEngine {

	int difficulty;
	
	public AIEngine() {
		
	}
	
	// Evaluates a board using Horizontal displacement for each marble
	private int HorizontalEvaluate(GameEngine myEngine, boolean myTurn) {
		Hole[] greenHoles = myEngine.myBoard.getGreenHoles();
		Hole[] redHoles = myEngine.myBoard.getRedHoles();
		
		int totalSelfHorz = 0;
		int totalOpHorz = 0;
		
		int i=0;
		for(Marble iMarble : myEngine.secondPlayer.getMarbles()) {
			if(myEngine.secondPlayerColor == 'R') {
				totalSelfHorz += Math.abs(greenHoles[i].getColumn() - iMarble.getHole().getColumn());				
			}
			else if(myEngine.secondPlayerColor == 'G') {
				totalSelfHorz += Math.abs(greenHoles[i].getColumn() - iMarble.getHole().getColumn());								
			}
			i++;
		}
		i=0;
		for(Marble iMarble : myEngine.firstPlayer.getMarbles()) {
			if(myEngine.firstPlayerColor == 'R') {
				totalOpHorz += Math.abs(greenHoles[i].getColumn() - iMarble.getHole().getColumn());				
			}
			else if(myEngine.firstPlayerColor == 'G') {
				totalOpHorz += Math.abs(redHoles[i].getColumn() - iMarble.getHole().getColumn());
			}
			i++;
		}

		if(myTurn)
			return totalSelfHorz-totalOpHorz;
		return totalOpHorz-totalSelfHorz;
	}
	
	// Evaluates a board using Vertical displacement for each marble
	private int verticalEvaluate(GameEngine myEngine, boolean myTurn) {
		Hole[] greenHoles = myEngine.myBoard.getGreenHoles();
		Hole[] redHoles = myEngine.myBoard.getRedHoles();
		
		int totalSelf = 0;
		
		int i=0;
		for(Marble iMarble : myEngine.secondPlayer.getMarbles()) {
			if(myEngine.secondPlayerColor == 'R') {
				totalSelf += Math.abs(greenHoles[i].getRow() - iMarble.getHole().getRow());
			}
			else if(myEngine.secondPlayerColor == 'G') {
				totalSelf += Math.abs(redHoles[i].getRow() - iMarble.getHole().getRow());
			}
			i++;
		}
		int totalOp = 0;
		i=0;
		for(Marble iMarble : myEngine.firstPlayer.getMarbles()) {
			if(myEngine.firstPlayerColor == 'R') {
				totalOp += Math.abs(greenHoles[i].getRow() - iMarble.getHole().getRow());
			}
			else if(myEngine.firstPlayerColor == 'G') {
				totalOp += Math.abs(redHoles[i].getRow() - iMarble.getHole().getRow());
			}
			i++;
		}

		if(myTurn)
			return totalSelf-totalOp;
		return totalOp-totalSelf;
	}

	// Generates a List of valid next boards for a given board
	public ArrayList<GameEngine> generateBoard(GameEngine myEngine, boolean myTurn){
		ArrayList<GameEngine> children = new ArrayList<GameEngine>();
		Player currPlayer;
		
		if(myTurn) {
			myEngine.playerTwoTurn(false);
			currPlayer = myEngine.secondPlayer;
		}
		else {
			myEngine.playerOneTurn(false);
			currPlayer = myEngine.firstPlayer;
		}
		for(Marble iMarble : currPlayer.validMarbles()) {
			for(Hole iHole : iMarble.getNextMoves()) {
				GameEngine currEngine = myEngine.deepClone();
				Marble currMarble = currEngine.myBoard.getHole(iMarble.getHole().getRow(),iMarble.getHole().getColumn()).getMarble();
				Hole currHole = currEngine.myBoard.getHole(iHole.getRow(), iHole.getColumn());
				
				if(currEngine.inZone(iMarble) && difficulty != 2 && currMarble.getColor() == 'G' && currHole.getRow()<=currMarble.getHole().getRow())
					continue;

				if(currEngine.inZone(iMarble) && difficulty != 2 && currMarble.getColor() == 'R' && currHole.getRow()>=currMarble.getHole().getRow())
					continue;

				if(currHole.getRow()<currMarble.getHole().getRow() && currMarble.getColor() == 'G')
					continue;

				if(currHole.getRow()>currMarble.getHole().getRow() && currMarble.getColor() == 'R')
					continue;
				
				
				int iCurrHole = currMarble.getHole().getRow(), jCurrHole=currMarble.getHole().getColumn();
				int iNextHole = currHole.getRow(), jNextHole= currHole.getColumn();
				int diffI = Math.abs(iCurrHole-iNextHole);
				int diffJ = Math.abs(jCurrHole-jNextHole);
				
				currMarble.getHole().reset();
				currMarble.setHole(currHole);
				currHole.setMarble(currMarble);
				
				if((diffI == 2 && diffJ == 2) || diffJ>2) {
					while(true) {		
						currEngine.detectNextMove(currMarble, true);
						
						Hole bestHole=null;
						int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
						for(Hole myHole : currMarble.getNextMoves()) {
							if(currMarble.getColor() == 'R') {
								if(myHole.getRow() < currHole.getRow()) {	
									if(myHole.getRow()<min) {
										min = myHole.getRow();
										bestHole = myHole;
									}
								}
							}else {
								if(myHole.getRow() > currHole.getRow()) {
									if(myHole.getRow()>max) {
										max = myHole.getRow();
										bestHole = myHole;
									}
								}
							}
						}
						
						if(bestHole == null)
							break;
						currMarble.getHole().reset();
						currMarble.setHole(bestHole);
						bestHole.setMarble(currMarble);
						currHole = bestHole;
					}
				}
				children.add(currEngine);
			}
		}
		return children;
	}

	public GameEngine MiniMax(GameEngine myEngine, int depth) {
		this.difficulty = depth;
		return 	MiniMaxHelper(myEngine, 0, depth, true);
	}
	
	
	// method to choose the board which has the minimum evaluation
	private GameEngine getMinimum(ArrayList<GameEngine> list) {
		int min=Integer.MAX_VALUE;
		GameEngine target = null;
		for(GameEngine i : list) {
			if(difficulty == 2) {
				if(i.verticalEvaluation +i.horizontalEvaluation < min) {
					min  = i.verticalEvaluation +i.horizontalEvaluation;
					target = i;
				}	
			}else {
				if(i.verticalEvaluation +i.horizontalEvaluation < min) {
					min  = i.verticalEvaluation +i.horizontalEvaluation;
					target = i;
				}
				else {
					min = i.verticalEvaluation<i.horizontalEvaluation?i.verticalEvaluation:i.horizontalEvaluation;
					target = i;	
				}
			}
		}
		return target;
	}
	
	// method to choose the board which has the maximum evaluation
	private GameEngine getMaximum(ArrayList<GameEngine> list) {
		int max=Integer.MIN_VALUE;
		GameEngine target = null;
		for(GameEngine i : list) {
			if(difficulty == 2) {
				if(i.verticalEvaluation +i.horizontalEvaluation > max) {		
					max  = i.verticalEvaluation +i.horizontalEvaluation;
					target = i;
				}
			}else{
				if(i.verticalEvaluation +i.horizontalEvaluation > max) {		
					max  = i.verticalEvaluation +i.horizontalEvaluation;
					target = i;
				}
				else {
					max = i.verticalEvaluation>i.horizontalEvaluation?i.verticalEvaluation:i.horizontalEvaluation;
					target = i;	
				}
			}
		}
		//System.out.println("Max Evaluation = " + target.evaluation);
		//System.out.println();
		return target;
	}
	
	// main minimax algortihm
	private GameEngine MiniMaxHelper(GameEngine myEngine, int level, int depth, boolean myTurn) {
		if(level >= depth)
			return myEngine;
		ArrayList<GameEngine> children = this.generateBoard(myEngine, myTurn);
		for(GameEngine i : children) {
			GameEngine temp = MiniMaxHelper(i,level+1,depth,!myTurn);
			if(level == depth-1) {
				i.setVerticalEvaluation(this.verticalEvaluate(i, myTurn));
				i.setHorizontalEvaluation(this.HorizontalEvaluate(i, myTurn));				
			}
			else {
				i.setVerticalEvaluation(temp.verticalEvaluation);
				i.setHorizontalEvaluation(temp.horizontalEvaluation);				
			}
		}
		if(myTurn) 
			return getMaximum(children);
		else
			return getMinimum(children);

	}
}

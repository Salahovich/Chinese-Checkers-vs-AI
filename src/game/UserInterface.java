package game;


import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

	public static void playerOne(GameEngine game, Scanner scan) {
		boolean serial = false;
		Marble playerOneMarble = null;
		Hole playerOneHole = null;
		int userInput;
		String code;
		while(true) {
			game.displayBoard();
			System.out.println("Player 1 Turn:");
			game.playerOneTurn(serial);

			if(!serial) {					
				game.playerOneValidMoves();
				ArrayList<Marble> playerOneMarbles = game.playerOneValidMarbles();
				
				System.out.println("Choose the marble: ");
				code = scan.next();
				
				// search for the choosen marble
				for(Marble iMarble: playerOneMarbles) {
					if(iMarble.getCode().equals(code))
						playerOneMarble = iMarble;
				}
				if(playerOneMarble == null) {
					System.out.println("You can not move this marble!");
					continue;
				}
			}else
				game.detectNextMove(playerOneMarble, serial);
			
			
			ArrayList<Hole> playerOneHoles = playerOneMarble.getNextMoves();
			
			if(serial) {
				System.out.println("You can move " + playerOneMarble.getCode() + " to " + playerOneHoles.toString());
				System.out.println("If you want to finish the move press 0.");
				try {
					userInput = scan.nextInt();
					if(userInput == 0)
						break;							
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("Choose the next hole of Marble " + playerOneMarble.getCode() + " :");
			code = scan.next();
			
			for(Hole iHole : playerOneHoles) {
				if(iHole.getNumber().equals(code))
					playerOneHole = iHole;
			}
			
			if(playerOneHole == null) {
				System.out.println("You can not move to this hole");
				continue;
			}
			
			
			int iCurrHole = playerOneMarble.getHole().getRow(), jCurrHole=playerOneMarble.getHole().getColumn();
			int iNextHole = playerOneHole.getRow(), jNextHole=playerOneHole.getColumn();
			int diffI = Math.abs(iCurrHole-iNextHole);
			int diffJ = Math.abs(jCurrHole-jNextHole);
			
			playerOneMarble.getHole().reset();
			playerOneHole.setMarble(playerOneMarble);
			playerOneMarble.setHole(playerOneHole);
			
			if((diffI == 2 && diffJ == 2) || diffJ>2)
				serial = true;
			else 
				break;
		}
	}
	
	public static void playerTwo(GameEngine game, Scanner scan) {
		boolean serial = false;
		Marble playerTwoMarble = null;
		Hole playerTwoHole = null;
		int userInput;
		String code;
		
		while(true) {
			game.displayBoard();
			System.out.println("Player 2 Turn:");
			game.playerTwoTurn(serial);
			
			if(!serial) {					
				game.playerTwoValidMoves();
				ArrayList<Marble> playerTwoMarbles = game.playerTwoValidMarbles();
				
				System.out.println("Choose the marble: ");
				code = scan.next();
				
				// search for the choosen marble
				for(Marble iMarble: playerTwoMarbles) {
					if(iMarble.getCode().equals(code))
						playerTwoMarble = iMarble;
				}
				if(playerTwoMarble == null) {
					System.out.println("You can not move this marble!");
					continue;
				}
			}else
				game.detectNextMove(playerTwoMarble, serial);
			
			ArrayList<Hole> playerTwoHoles = playerTwoMarble.getNextMoves();
			
			if(serial) {
				System.out.println("You can move " + playerTwoMarble.getCode() + " to " + playerTwoHoles.toString());
				System.out.println("If you want to finish the move press 0.");
				try {
					userInput = scan.nextInt();
					if(userInput == 0)
						break;							
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("Choose the next hole of Marble " + playerTwoMarble.getCode() + " :");
			code = scan.next();
			
			for(Hole iHole : playerTwoHoles) {
				if(iHole.getNumber().equals(code))
					playerTwoHole = iHole;
			}
			
			if(playerTwoHole == null) {
				System.out.println("You can not move to this hole");
				continue;
			}
			
			
			int iCurrHole = playerTwoMarble.getHole().getRow(), jCurrHole=playerTwoMarble.getHole().getColumn();
			int iNextHole = playerTwoHole.getRow(), jNextHole=playerTwoHole.getColumn();
			int diffI = Math.abs(iCurrHole-iNextHole);
			int diffJ = Math.abs(jCurrHole-jNextHole);
			
			playerTwoMarble.getHole().reset();
			playerTwoHole.setMarble(playerTwoMarble);
			playerTwoMarble.setHole(playerTwoHole);
			
			
			if((diffI == 2 && diffJ == 2) || diffJ>2)
				serial = true;
			else 
				break;
		}
	}
	
	public static void main(String[] args) {
		
		char playerOneColor, playerTwoColor;
		Scanner scan = new Scanner(System.in);
		boolean HvH = false, HvC =false;
		int userInput;
		AIEngine autoEngine = new AIEngine();
		int difficulty=0;
		
		System.out.println("*******Welcome to Chinese Checkers Game*******");
		System.out.println("1- Human VS Human");
		System.out.println("2- Human VS Computer");
		System.out.println("Enter your choice: ");
		userInput = scan.nextInt();
		
		if(userInput == 1)
			HvH = true;
		else if(userInput == 2) {
			HvC = true;
			System.out.println("Choose difficulty");
			System.out.println("1- Easy");
			System.out.println("2- Medium");
			System.out.println("3- Hard");
			difficulty = scan.nextInt();
		}
		
		System.out.println("Player 1, please choose your color: ");
		System.out.println("1- Red");
		System.out.println("2- Green");
		userInput = scan.nextInt();
		
		if(userInput == 1) {
			playerOneColor = 'R';
			playerTwoColor = 'G';
		}
		else {
			playerOneColor = 'G';
			playerTwoColor = 'R';
		}
		GameEngine game = new GameEngine(playerOneColor, playerTwoColor);
		
		// Human vs Computer Case
		if(HvC) {
			while(!game.isGameEnded()) {
				
				// player 1 turn
				playerOne(game, scan);
				
				// player 2 turn
				game.displayBoard();
				System.out.println("Computer is thinking...");
				game = autoEngine.MiniMax(game, difficulty);
			}
		}
		// Human vs Human Case
		else if(HvH){			
			while(!game.isGameEnded()) {

				// player 1 turn
				playerOne(game, scan);
				
				// player 2 turn
				playerTwo(game, scan);
			}
		}
		
		game.displayBoard();
		if(game.playerOneWin)
			System.out.println("Player 1 Has Won! Congrats...");
		else if(game.playerTwoWin)
			System.out.println("Player 2 Has Won! Congrats...");

		scan.close();
	}
}

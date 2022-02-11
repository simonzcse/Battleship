/**
 * @(#)Battleship.java
 * 
 * IT114105 1A
 * @author Cheung King Hung
 * @version 1.00 2019/11/12
 */

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;
public class Battleship {

    public static void main(String[] args) throws IOException, InterruptedException  {
    Scanner kk = new Scanner(System.in);
    String[] ship = {"Patrol Boat", "Submarine", "Destroyer", "Battleship","Carrier"};//storage ship type
    int [] ship_size ={2, 3, 3, 4, 5};//storage ship size
    char ch1, ch2;//storage XY as char
	int n1, n2;//storage XY as integer
    String [][] mTable = new String[10][10]; //create player 1 four grids
	for ( int i = 0; i <= 9; i++ )
		for ( int j = 0; j <= 9; j++ )
			mTable[i][j] =".";	
    String p1, p2, p3; // p1 use for Human or, CPU, p2 use for Orientation (horizontal vertical), p3 use for storage XY)
    System.out.println("Battleship Player1 (0 - Human, 1 - CPU, x - Exit):");
    p1=kk.next();
    boolean p1_check= false;
    while (p1_check== false){ //check Player1 is or not correct
    if (p1.length() == 1 && p1.charAt(0)=='x'){
    		xExit(p1);
    } else if (p1.length() == 1 && p1.charAt(0)=='1'){
    		p1_check= true;
    } else if (p1.length() == 1 && p1.charAt(0)=='0'){
    		p1_check= true;
    }else {
    		System.out.println("Please input 0 or 1, x - Exit");//input again
    		p1=kk.next();
    	}
    }
    if (p1.length() == 1 && p1.charAt(0)=='0'){ 
		boolean p2_check= false, p3_check=false, real_p3_check=false; //loop boolean
    	for (int i=4;i>=0;i--) {	//5,4,3,3,2
    		printdata(mTable);
    		System.out.println("Player1: Set the ship: "+ ship[i] +", ship size: " + ship_size[i]);
    		System.out.println("Orientation (0 - horizontal, 1 - vertical), x - Exit:");
    		p2=kk.next();
    		p2_check= false;
    		while (p2_check== false){ //check Orientation is or not correct
    			if (p2.length() == 1 && p2.charAt(0)=='x'){
    				xExit(p2);
    			} else if (p2.length() == 1 && p2.charAt(0)=='0'){
    				p2_check= true;
    			} else if (p2.length() == 1 && p2.charAt(0)=='1'){
    				p2_check= true;
    			}else {
    				System.out.println("Please input 0 or 1, x - Exit");
    				p2=kk.next();
    			}
    		}
    		real_p3_check=false;
    		p3_reallooping://XY looping
    			while (real_p3_check==false) {
					System.out.println("Position of Carrier [XY], x - Exit:");
    				p3_check=false;
    		p3_looping://check input is or not Numeric
    			do{
    				p3=kk.next();
    				p3_check=checking_p3(p3);
    				if (p3_check==false);{
    					continue p3_looping;
    				}
					} while (p3_check == false);
					ch1 = p3.charAt(0);
					ch2 = p3.charAt(1);
					n1 = Character.getNumericValue(ch1);
					n2 = Character.getNumericValue(ch2);
					switch (p2){//over the boundary and overlap checking
						case "0":
							if (n1+ship_size[i]>9){
								System.out.println("The ships cannot be over the boundary!");
									continue p3_reallooping;
							}
							for (int ee =1;ee<=ship_size[i];ee++){
    							if (mTable[n2][n1+ee-1]=="S") {
    								System.out.println("The ships cannot overlap!");
    								continue p3_reallooping;
    							}
							}
							for (int ee =1;ee<=ship_size[i];ee++){
    							mTable[n2][n1+ee-1]="S";
							}
    						real_p3_check=true;
    					break;
    					case "1":
    						if (n2+ship_size[i]>9){
								System.out.println("The ships cannot be over the boundary!");
									continue p3_reallooping;
							}
							for (int ee =1;ee<=ship_size[i];ee++){
    							if (mTable[n2+ee-1][n1]=="S") {
    								System.out.println("The ships cannot overlap!");
    								continue p3_reallooping;
    							}
							}    						
    						for (int ee =1;ee<=ship_size[i];ee++) {
    							mTable[n2+ee-1][n1]="S";
    						}
    						real_p3_check=true;
    					break;	
						} 
    			}
			}
		printdata(mTable);//end human	
    } else if (p1.length() == 1 && p1.charAt(0)=='1'){ // if cpu
    	for (int i=4;i>=0;i--){//random gen 
    		boolean xyisok=false;
    		int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);//auto gen Orientation 
    		xycheck:
    		while(xyisok==false){
    			int randomNum_X = ThreadLocalRandom.current().nextInt(0, 9 + 1);//random gen X
    			int randomNum_Y = ThreadLocalRandom.current().nextInt(0, 9 + 1);//random gen Y 
    			switch (randomNum){//over the boundary and overlap checking
					case 0:
						if (randomNum_X+ship_size[i]>9){
								continue xycheck;
						}
						for (int ee =1;ee<=ship_size[i];ee++){
    						if (mTable[randomNum_Y][randomNum_X+ee-1]=="S") {
    							continue xycheck;
    							}
							}
						for (int ee =1;ee<=ship_size[i];ee++){
    						mTable[randomNum_Y][randomNum_X+ee-1]="S";
							}
    						xyisok=true;
    				break;
    				case 1:
    					if (randomNum_Y+ship_size[i]>9){
								continue xycheck;
							}
						for (int ee =1;ee<=ship_size[i];ee++){
    						if (mTable[randomNum_Y+ee-1][randomNum_X]=="S") {
    							continue xycheck;
    						}
						}    						
    					for (int ee =1;ee<=ship_size[i];ee++) {
    						mTable[randomNum_Y+ee-1][randomNum_X]="S";
    					}
    					xyisok=true;
    				break;	
						} 
    		}
    	}
    	//printdata(mTable);	
    }
    System.out.println("Press Enter for Player2 to start ...");//start 2p
	System.in.read();
	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();//clean the screen
	System.out.println();
	String [][] mTable_p2 = new String[10][10];//create game console for player 2
	for ( int i = 0; i <= 9; i++ )
		for ( int j = 0; j <= 9; j++ )
			mTable_p2[i][j] =".";	
	int noship=0, int_Launched=0;//noship use for storage hit, int_Launched use for storage Launched
	String player2_xy;//player 2 input 
	player2_xy_looping2:// game looping
	while (noship<17){
		boolean player2_xy_looping1_check = false;
		printdata(mTable_p2);
		System.out.println("Player2: Set your missile [XY], x - Exit : ");
			player2_xy_looping1:// check XY is or not Digits
    			do{
    				player2_xy=kk.next();
    				player2_xy_looping1_check=checking_p3(player2_xy);
    				if (player2_xy_looping1_check==false);{
    					continue player2_xy_looping1;
    				}
					} while (player2_xy_looping1_check == false);
				char p2_ch1 = player2_xy.charAt(0);//storage X as char
				char p2_ch2 = player2_xy.charAt(1);//storage Y as char
				int p2_n1 = Character.getNumericValue(p2_ch1);//storage X as char
				int p2_n2 = Character.getNumericValue(p2_ch2);//storage Y as char
				if (mTable_p2[p2_n2][p2_n1]!=".") {
    				System.out.println("You have already fired this area.");
    				continue player2_xy_looping2;
    			}
    			if (mTable[p2_n2][p2_n1]=="S") {
    				System.out.println("It's a hit!!");
    				noship+=1;
    				int_Launched+=1;
    				mTable_p2[p2_n2][p2_n1]="#";
    				System.out.println("Launched:"+int_Launched + ", Hit: " +noship);
    				}
    			else if (mTable[p2_n2][p2_n1]==".") {
    				System.out.println("Missed");
    				int_Launched+=1;
    				mTable_p2[p2_n2][p2_n1]="o";
    				System.out.println("Launched:"+int_Launched + ", Hit: " +noship);
    				}
	}
	printdata(mTable_p2);
	System.out.println("You have hit all battleships!");

    }


	private static void xExit(String x){//exit game
 	if (x.length() == 1 && x.charAt(0)=='x'){
 		System.exit(0);
 		}
 	}
 	
 	
	private static void printdata(String[] [] mTable){ //This use for print game board
	System.out.print( "   " );
	for ( int j = 0; j <= 9; j++ )
		System.out.print( " " + j );
	System.out.print(" <--X");
	System.out.println();
	
	System.out.printf( "--+" );
	for ( int j = 0; j <= 9; j++ )
		System.out.print( "--" );
	System.out.println();
	// print data 
	for ( int i = 0; i<= 9; i++ ) {
		System.out.print(i + " |"); // print the row 
		for ( int j = 0; j <= 9; j++ )
			System.out.print( " " + mTable[i][j] );
		System.out.println();
	}
	System.out.println("^");
	System.out.println("Y");	
	}

	private static boolean ReadAndCheck2Digits( String s ) { //Check xy is or not Digits
		char ch1, ch2;
		int n1, n2;
		// get the first char
		ch1 = s.charAt(0);
		// get the second char
		ch2 = s.charAt(1);
		// Character.isDigit() determines if the specified character is a digit.
		if (!Character.isDigit(ch1) || !Character.isDigit(ch2)) {
			System.out.println("Error in [XY]! Please input again, x - Exit:");
			return false;
		} else {
			return true;
			// Character.getNumericValue() returns the int value that the specified character represents.
			/*
			n1 = Character.getNumericValue(ch1);  // or n1 = ch1 - 48;
			n2 = Character.getNumericValue(ch2);  // or n2 = ch2 - 48;
			
			System.out.println("The first number is " + n1);
			System.out.println("The second number is " + n2);	*/
	
	
		}
	}
	private static boolean checking_p3 (String p31){//check xy length
				boolean p31_check = false;
    			if (p31.length() == 1 && p31.charAt(0)=='x'){
    				xExit(p31);
    			}else if (p31.length() > 2){
    				System.out.println("Error in [XY]! Please input again, x - Exit:");
    				p31_check= false;
    			}else if (p31.length() <= 1){
    				System.out.println("Error in [XY]! Please input again, x - Exit:");
    				p31_check= false;
    			}else if (p31.length() == 2){
    				p31_check= ReadAndCheck2Digits(p31);
    			}else {
    				System.out.println("Error in [XY]! Please input again, x - Exit:");
    				p31_check= false;
    			}
    			return p31_check;	 		
    		
	}
	

}
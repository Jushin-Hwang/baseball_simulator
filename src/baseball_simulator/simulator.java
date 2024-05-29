package baseball_simulator;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class simulator {
	
	public static int numberOfBatter = 9;
	public static int numberOfBase = 4;

	public static void main(String[] args) {
		// Start page
		System.out.println("------- Baseball Simulator -------");
		
		// Read match infomation from file and show it
		String filePath = "C:/수업/1. 객체지향프로그래밍/프로젝트/matchInfo.txt";

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        // make new batters array
        batter[] batters = new batter[9];
		
		batters[0] = new batter(1, "허경민", 63, 12, 0, 2, 12, 9, 0.449);
		batters[1] = new batter(2, "홍성호", 4, 2, 0, 0, 1, 0, 0.417);
		
		// make new team
		team Team1 = new team("<두산>", batters);
		
		// print name of batters
		System.out.println("Batters of team " + Team1.name);
		for(int i=0; i<2; i++) {
			System.out.print(Team1.Batters[i].getBattingOrder() + "." + Team1.Batters[i].getName() + " ");
		}
		System.out.println();
		
		// game start
		for(int i=0; i<20; i++) {
			System.out.println("base= " + Team1.Batters[0].getBase());
			Team1.score += Team1.Batters[0].runAndScore(Team1.Batters[0].bat());
		}
		System.out.println("score= " + Team1.score);
		
//		System.out.println(myBatter.getName());
//		System.out.println(myBatter.bat());
	}

}

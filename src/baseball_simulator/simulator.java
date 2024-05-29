package baseball_simulator;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class simulator {
	
	public static int numberOfBatter = 9;
	public static int numberOfBase = 4;

	public static void main(String[] args) {
		int t1_score = 0;
		int t2_score = 0;
		
		batter[] t1_batters = new batter[numberOfBatter];
		batter[] t2_batters = new batter[numberOfBatter];
		
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
        
        // print name of batters
        
        
        
		
		batter myBatter = new batter(1, "허경민", 63, 12, 0, 2, 12, 9, 0.449);
		System.out.println(myBatter.getName());
		System.out.println(myBatter.bat());
	}

}

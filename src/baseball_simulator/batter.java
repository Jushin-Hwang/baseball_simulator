package baseball_simulator;
import java.util.Random;

public class batter {

	private String name;
	private int battingOrder;
	private int base;
	private int single;
	private int twoB;
	private int threeB;
	private int homeRun;
	private double obp;

	// init
	public batter(int num, String name, int H, int twoB, int threeB, int HR, int BB, int HBP, double OBP) {
		battingOrder = num;
		this.name = name;
		base = 0;
		single = H + BB + HBP;
		this.twoB = twoB;
		this.threeB = threeB;
		homeRun = HR;
		obp = OBP;
	}
	
	// 이 리턴값은 주자가 몇개의 루를 가는지에 쓰임.
	public int bat() {
		// Calculate Rates
		double sumOfRecords = (double) single + twoB + threeB + homeRun;
		double t = sumOfRecords / obp;
		
		double singleRate = (double)single / t;
		double twoBRate = (double)twoB / t;
		double threeBRate = (double)threeB / t;
		double homeRunRate = (double)homeRun / t;
		double outRate = 1-obp;
		
		double sumOfRates = singleRate + twoBRate + threeBRate + homeRunRate + outRate;
		
//		System.out.println(singleRate);
//		System.out.println(twoBRate);
//		System.out.println(threeBRate);
//		System.out.println(homeRunRate);
//		System.out.println(outRate);
//		System.out.println(sumOfRates);
		
		Random random = new Random();
		double randomNumber = random.nextDouble();
//		System.out.println("random= " + randomNumber);
		
		// Single
		if(randomNumber < singleRate) {
			System.out.println("Single!");
			return 1;
		}
		// Two-base hit
		else if(randomNumber < singleRate + twoBRate) {
			System.out.println("Two-base hit!");
			return 2;
		}
		// Three-base hit
		else if(randomNumber < singleRate + twoBRate + threeBRate){
			System.out.println("Three-base hit!");
			return 3;
		}
		// Homerun
		else if(randomNumber < singleRate + twoBRate + threeBRate + homeRunRate){
			System.out.println("Homerun!");
			return 4;
		}
		// Out
		else {
			System.out.println("Out!");
			return 0;
		}
		
	}
	
	public int runAndScore(int howManyBases) {
		base += howManyBases;
		if(base>=4) {
			base=0;
			System.out.println("Score!");
			return 1;
		}
		return 0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getBattingOrder() {
		return battingOrder;
	}
	
	public int getBase() {
		return base;
	}
	
}

package baseball_simulator;

public class team {
	public String name;
	public int score;
	public batter[] Batters;
	public int nextBatter;
	
	public team(String name, batter[] Batters) {
		this.name = name;
		this.score = 0;
		this.Batters = Batters;
	}
}


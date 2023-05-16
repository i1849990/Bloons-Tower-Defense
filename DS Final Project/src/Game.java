import java.util.ArrayList;

public class Game {
	public int money;
	public int lives;
	public int round;
	
	ArrayList<Bloon> bloons = new ArrayList<Bloon>();
	ArrayList<Monkey> monkeys = new ArrayList<Monkey>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	Track track;
	
	public Game(Track track) {
		money = 650;
		lives = 40;
		this.track = track;
	}
	
	public Game() { // constructor only used for testing until we have the x,y points to make a real track object
		money = 650;
		lives = 40;
	}
	
	public void bombExplosion() {
		// acts differently from regular darts, see trello
	}
	
}
 
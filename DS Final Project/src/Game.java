import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Game {
	public int cash;
	public int lives;
	public int round;

	ArrayList<Bloon> bloons = new ArrayList<Bloon>();
	ArrayList<Monkey> monkeys = new ArrayList<Monkey>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	Track track;
	
	private long currFrame;
	
	public Game(Track track) {
		cash = 650;
		lives = 40;
		round = 1;
		this.track = track;
		currFrame = 0;
		
		Bloon.initializeImages();
	}
	
	public void nextFrame() {
		moveBloons();
		moveProjectiles();
		handleCollisions();
		currFrame++;
	}
	
	public void bombExplosion() {
		// acts differently from regular darts, see trello
	}
	
	public void iceshot() {
		// similar case to bomb explosion
	}
	
	public void advanceRound() {
		cash += 100;
		round++;
	}
	
	public void handleCollisions() {
		for (Bloon b: bloons) {
			for(Projectile p : projectiles) {
				if(p.hasPiercedBloon(b)) {
					return;
				}
				if(!b.getHitbox().intersects(p.getHitbox())) {
					return;
				}
				
				cash += 1;
				
				if(p.handleCollision(b)) {
					projectiles.remove(p);
				}
				
				if(b.popLayer()) {
					bloons.remove(b);
				}
			}
		}
	}
	
	public void moveBloons() {
		for(Bloon b: bloons) {
			if(b.move()) {
				bloonReachesEnd(b);
			}
		}
	}
	
	public void moveProjectiles() {
		for(Projectile p : projectiles) {
			p.move();
		}
	}
	
	public Bloon getFurthestBloonInCircle(Monkey m) {
		Collections.sort(bloons, Collections.reverseOrder());
		for(Bloon b : bloons) {
			if(bloonLiesInRangeOfMonkey(b,m)) {
				return b;
			}
		}
		return null;
	}
	
	private boolean bloonLiesInRangeOfMonkey(Bloon b, Monkey m) {
		return Math.sqrt(Math.pow(m.getCenteredX() - b.getCenteredX(), 2) + Math.pow(m.getCenteredY() - b.getCenteredY(), 2)) <= m.getRange();
	}
	
	public void bloonReachesEnd(Bloon b) {
		lives -= b.getLayer() + 1;
		bloons.remove(b);
	}
	
	public void startGame() {
		
	}
	
}
 
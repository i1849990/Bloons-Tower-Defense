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
	
	public Game(Track track) {
		cash = 650;
		lives = 40;
		round = 1;
		this.track = track;
		
		// FOR TESTING:
		bloons.add(new Bloon(track, 0));
	}
	
	public void nextFrame() {
		moveBloons();
		moveProjectiles();
		handleCollisions();
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
				
				int dmg = p.getDamage();
				cash += b.getLayersToBePopped(dmg);
				
				if(b.popNumLayers(dmg)) {
					bloons.remove(b);
				}
				if(p.handleCollision(b)) {
					projectiles.remove(p);
				}
				
			}
		}
	}
	
	public void moveBloons() {
		for(Bloon b: bloons) {
			if(b.move(track)) {
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
	
}
 
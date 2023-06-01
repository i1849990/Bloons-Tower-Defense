import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
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
	
	private int currFrame;
	boolean roundInProgress;
	
	public Game(Track track) {
		cash = 650;
		lives = 40;
		round = 0;
		this.track = track;
		currFrame = 0;
		
		roundInProgress = false;
		
		Bloon.initializeImages();
		Monkey.initializeImages();
		
		//bloons.add(new Bloon(track, 0));
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
	
	public void tryToAdvanceRound() {
		if(!roundInProgress) {
			roundInProgress = true;
			round++;
		}
	}
	
	private void endRound() {
		cash += 100;
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
	
	public void tryToPurchaseUpgrade0(Monkey m) {
		if(m.getUpgradeCosts()[0] > cash || m.getUpgradesPurchased()[0] || m == null) {
			return;
		}
		
		cash -= m.getUpgradeCosts()[0];
		m.purchaseUpgrade0();
	}
	
	public void tryToPurchaseUpgrade1(Monkey m) {
		if(m.getUpgradeCosts()[1] > cash || m.getUpgradesPurchased()[1] || m == null) {
			return;
		}
		
		cash -= m.getUpgradeCosts()[1];
		m.purchaseUpgrade1();
	}
	
	public void sellMonkey(Monkey m) {
		if(m != null) {
			cash += m.getSellPrice();
			monkeys.remove(m);
		}
	}
	
	// used to display the range of a tower that is going to be placed for ScreenGUI
	public int getDisplayRange(String monkeyName) {
		switch(monkeyName) {
		case"dart":
			return 100;
		case"tack":
			return 70;
		case"ice":
			return 70;
		case"bomb":
			return 120;
		case"super":
			return 140;
		}
		
		return 0;
	}
	
	public boolean intersectsObjects(Rectangle r) {
		for(Monkey m : monkeys) {
			if(r.intersects(m.hitbox)) {
				return true;
			}
		}
		
		return track.intersectsRect((Rectangle2D) r);
	}
	
	public void addMonkey(Monkey m) {
		monkeys.add(m);
		cash -= m.getCost();
	}
	
	public boolean canAffordTower(String str) {
		int cost = 0;
		switch(str) {
		case"dart":
			cost = 250;
			break;
		case"tack":
			cost = 400;
			break;
		case"ice":
			cost = 850;
			break;
		case"bomb":
			cost = 900;
			break;
		case"super":
			cost = 4000;
			break;
		}
		return cost <= cash;
	}
	
	public boolean getRoundInProgress() {
		return roundInProgress;
	}
	
	public int getRound() {
		return round;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getCash() {
		return cash;
	}
	
}
 
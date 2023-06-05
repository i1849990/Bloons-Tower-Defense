import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
	public int cash;
	public int lives;
	public int round;

	public ArrayList<Bloon> bloons;
	public ArrayList<Monkey> monkeys;
	public ArrayList<Projectile> projectiles;
	public ArrayList<VisualEffect> effects;
	private ArrayList<Bloon> bloonsToSpawn;
	
	public Track track;
	private RoundScanner s;
	
	private int currFrame;
	private boolean roundInProgress;
	private int framesBetweenBloonSpawns;
	private int lastFrameSpawned;
	
	public boolean won;
	public boolean lost;
	
	public Game(Track track) {
		this.track = track;
		Bloon.initalizeTrack(this.track);
		s = new RoundScanner("Bloons per Round");
		
		Bloon.initializeImages();
		Monkey.initializeImages();
		VisualEffect.intitializeImages();
		Projectile.initializeImages();
		
		startGame();
	}
	
	public void startGame() {
		cash = 650;
		lives = 40;
		round = 0;
		s = new RoundScanner("Bloons per Round");
		currFrame = 0;
		roundInProgress = false;
		
		// test different values
		framesBetweenBloonSpawns = 10;
		lastFrameSpawned = 0 - framesBetweenBloonSpawns;
		
		bloons = new ArrayList<Bloon>();
		monkeys = new ArrayList<Monkey>();
		projectiles = new ArrayList<Projectile>();
		effects = new ArrayList<VisualEffect>();
		bloonsToSpawn = new ArrayList<Bloon>();
		
		won = false;
		lost = false;
	}
	
	public void nextFrame() {
		moveBloons();
		moveProjectiles();
		handleCollisions();
		monkeysShoot();
		checkIfRoundIsOver();
		tryToSpawnNextBloon();
		updateEffects();
	}
	
	public void bombExplosion(Projectile p) {
		// acts differently from regular darts, see trello
		int popCount = p.getPierce();
		int explosionX = p.getX() - 12;
		int explosionY = p.getY() - 21;
		int explosionDist = 80;
		
		for(int i = 0; i < bloons.size(); i++) {
			if(popCount <= 0) {
				return;
			}
			
			Bloon b = bloons.get(i);
			int bloonX = b.getX();
			int bloonY = b.getY();
			
			if((Math.sqrt(Math.pow(explosionX - bloonX, 2) + Math.pow(explosionY - bloonY, 2)) > explosionDist) || b.getLayer() == 5) {
				continue;
			}
			
			popCount--;
			cash += 1;
			// add pop sound
			
			if(b.popLayer(true)) {
				bloons.remove(b);
				i--;
			}
			
		}
	}
	
	public void iceshot(Monkey m) {
		// similar case to bomb explosion
		int monkeyX = m.getCenteredX();
		int monkeyY = m.getCenteredY();
		int radius = m.getRange();
		
		for(int i = 0; i < bloons.size(); i++) {
			Bloon b = bloons.get(i);
			int bloonX = b.getCenteredX();
			int bloonY = b.getCenteredY();
			
			if((Math.sqrt(Math.pow(monkeyX - bloonX, 2) + Math.pow(monkeyY - bloonY, 2)) > radius)) {
				continue;
			}
			
			b.freeze(currFrame, ((IceMonkey) m).freezeTime);
			
		}
	}
	
	public void tryToAdvanceRound() {
		if(roundInProgress) {
			return;
		}
		
		round++;
		bloonsToSpawn = s.getNextRoundBloonArray();
		lastFrameSpawned = 0 - framesBetweenBloonSpawns;
		roundInProgress = true;
	}
	
	private void endRound() {
		roundInProgress = false;
		cash += 100;
		framesBetweenBloonSpawns = 10 - (round / 10);
		if(round == 50) {
			won = true;
		}
	}
	
	private void tryToSpawnNextBloon() {
		if(currFrame - lastFrameSpawned < framesBetweenBloonSpawns || !roundInProgress || bloonsToSpawn.size() == 0) {
			return;
		}
		
		lastFrameSpawned = currFrame;
		bloons.add(bloonsToSpawn.remove(0));
	}
	
	private void checkIfRoundIsOver() {
		if(bloonsToSpawn.size() == 0 && bloons.size() == 0 && roundInProgress) {
			endRound();
		}
		
		if(lives <= 0) {
			lost = true;
		}
	}
	
	public void handleCollisions() {
		for (int i = 0; i < bloons.size(); i++) {
			Bloon b = bloons.get(i);
			for(int j = 0; j < projectiles.size(); j++) {
				Projectile p = projectiles.get(j);
				if(p.hasPiercedBloon(b)) {
					continue;
				}
				if(!b.getHitbox().intersects(p.getHitbox())) {
					continue;
				}
				
				if(!b.isFrozen()) {
					cash += 1;	
					// add pop sound
				}
				
				if(p.getName().equals("bomb")) {
					bombExplosion(p);
					effects.add(new VisualEffect(currFrame, "bomb", b.getCenteredX(), b.getCenteredY()));
					projectiles.remove(p);
					j--;
					continue;
				}
				
				if(p.handleCollision(b)) {
					
					projectiles.remove(p);
					
					j--;
				}

				effects.add(new VisualEffect(currFrame, "pop", b.getCenteredX(), b.getCenteredY()));
				if(b.popLayer(false)) {
					bloons.remove(b);
					i--;
					break;
				}
			}
		}
	}
	
	public void moveBloons() {
		for(int i = 0; i < bloons.size(); i++) {
			Bloon b = bloons.get(i);
			if(b.move(currFrame)) {
				bloonReachesEnd(b);
				i--;
			}
		}
	}
	
	public void moveProjectiles() {
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.move();
			if(p.outOfBounds()) {
				projectiles.remove(p);
				i--;
			}
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
	
	private void monkeysShoot() {
		for(Monkey m : monkeys) {
			Bloon b = getFurthestBloonInCircle(m);
			
			if(!m.canAttack(currFrame) || b == null) {
				continue;
			}
			
			if(m.getName().equals("Ice Monkey")) {
				m.pointTowardsBloonAndCreateProjectile(b, currFrame);
				iceshot(m);
				continue;
			}
			
			Projectile[] output = (m.pointTowardsBloonAndCreateProjectile(b, currFrame));
			if (output != null) {
				for (Projectile p : output) {
					projectiles.add(p);
				} 
			}
		}
	}
	
	public void bloonReachesEnd(Bloon b) {
		lives -= b.getLayer() + 1;
		bloons.remove(b);
	}
	
	public void updateFrame(int frame) {
		currFrame = frame;
	}
	
	public void resetGame() {
		startGame();
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
	
	private void updateEffects() {
		for(int i = 0; i < effects.size(); i++) {
			VisualEffect effect = effects.get(i);
			if(effect.isDone(currFrame)) {
				effects.remove(effect);
				i--;
			}
		}
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
 
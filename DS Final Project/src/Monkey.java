import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Monkey {
	protected int pierce;
	protected long attackSpeed;
	protected int cost;
	protected int range;
	protected int x;
	protected int y;
	protected int centeredX;
	protected int centeredY;
	
	protected BufferedImage image;
	protected double rotation; // in radians
	
	protected Rectangle hitbox; // hitbox used for checking if it overlaps with another monkey the user is trying to place
	protected int[] upgradeCosts; // should contain 2 values for a monkey's upgrades
	protected BufferedImage[] upgradeImages; // contains the image icons for the monkey upgrades
	protected String[] upgradeDescriptions; // contains the upgrade flavor text
	protected boolean[] upgradesPurchased; // are upgrades purchased
	protected int lastAttackFrame;
	
	public Monkey(int pCost, int pRange, int pPierce, long pAttackSpeed, int x, int y, int currFrame) {
		pierce = pPierce;
		cost = pCost;
		range = pRange;
		attackSpeed = pAttackSpeed;
		
		upgradesPurchased = new boolean[]{false, false};
		
		lastAttackFrame = currFrame;
	}
	
	public boolean canAttack(int currFrame) {
		return currFrame - lastAttackFrame >= attackSpeed;
	}
	
	
	public int getPierce() {
		return pierce;
	}
	public void setPierce(int pierce) {
		this.pierce = pierce;
	}
	public long getAttackSpeed() {
		return attackSpeed;
	}
	public void setAttackSpeed(long attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	public int getCost() {
		return cost;
	}
	
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCenteredX() {
		return centeredX;
	}

	public void setCenteredX(int centeredX) {
		this.centeredX = centeredX;
	}

	public int getCenteredY() {
		return centeredY;
	}

	public void setCenteredY(int centeredY) {
		this.centeredY = centeredY;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public int[] getUpgradeCosts() {
		return upgradeCosts;
	}

	public void setUpgradeCosts(int[] upgradeCosts) {
		this.upgradeCosts = upgradeCosts;
	}

	public BufferedImage[] getUpgradeImages() {
		return upgradeImages;
	}

	public void setUpgradeImages(BufferedImage[] upgradeImages) {
		this.upgradeImages = upgradeImages;
	}

	public String[] getUpgradeDescriptions() {
		return upgradeDescriptions;
	}

	public void setUpgradeDescriptions(String[] upgradeDescriptions) {
		this.upgradeDescriptions = upgradeDescriptions;
	}

	public boolean[] getUpgradesPurchased() {
		return upgradesPurchased;
	}

	public void setUpgradesPurchased(boolean[] upgradesPurchased) {
		this.upgradesPurchased = upgradesPurchased;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
}

class DartMonkey extends Monkey{

	public DartMonkey(int x, int y, int currFrame) {
		super(250, 100, 1, 30, x, y, currFrame);
		upgradeCosts = new int[] {210, 100};
	}
	
	public void updateUpgrades() {
		if(upgradesPurchased[0]) {
			pierce = 2;
		}
		if(upgradesPurchased[1]) {
			range = 125;
		}
	}

}

class TackShooter extends Monkey{

	public TackShooter(int x, int y, int currFrame) {
		super(400, 70, 1, 30, x, y, currFrame);
		upgradeCosts = new int[] {250, 150};
	}
	
	public void updateUpgrades() {
		if(upgradesPurchased[0]) {
			// increase attack speed
		}
		if(upgradesPurchased[1]) {
			range = 80;
		}
	}
	
}
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Monkey {
	private int damage;
	private int attackSpeed;
	private int cost;
	private int range;
	private int x;
	private int y;
	private int centeredX;
	private int centeredY;
	
	public BufferedImage image;
	public double rotation; // in radians
	
	private Rectangle hitbox; // hitbox used for checking if it overlaps with another monkey the user is trying to place
	private int[] upgradeCosts; // should contain 2 values for a monkey's upgrades
	private BufferedImage[] upgradeImages; // contains the image icons for the monkey upgrades
	private String[] upgradeDescriptions; // contains the upgrade flavor text
	private boolean[] upgradesPurchased; // are upgrades purchased
	private long lastAttackTime;
	
	public Monkey(int pCost, int pRange, int pDamage, int pAttackSpeed, int x, int y) {
		damage = pDamage;
		cost = pCost;
		range = pRange;
		attackSpeed = pAttackSpeed;
		
		upgradesPurchased = new boolean[]{false,false};
		lastAttackTime = System.currentTimeMillis();
	}
	
	public boolean canAttack(long currTime) {
		return false;
	}
	
	
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getAttackSpeed() {
		return attackSpeed;
	}
	public void setAttackSpeed(int attackSpeed) {
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

	public DartMonkey(int x, int y) {
		super(200, 50, 1, 2, x, y);
		// TODO Auto-generated constructor stub
	}

}
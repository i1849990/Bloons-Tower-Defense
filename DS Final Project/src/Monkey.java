import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Monkey {
	private int damage;
	private int attackSpeed;
	private int cost;
	private int range;
	private int x;
	private int y;
	
	Rectangle hitbox; // hitbox used for checking if it overlaps with another monkey the user is trying to place
	int[] upgradeCosts; // should contain 2 values for a monkey's upgrades
	BufferedImage[] upgradeImages; // contains the image icons for the monkey upgrades
	String[] upgradeDescriptions; // contains the upgrade flavor text
	boolean[] upgradesPurchased; // are upgrades purchased
	
	public Monkey(int pCost, int pRange, int pDamage, int pAttackSpeed, int x, int y) {
		damage = pDamage;
		cost = pCost;
		range = pRange;
		attackSpeed = pAttackSpeed;
		
		upgradesPurchased = new boolean[]{false,false};
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
}

class DartMonkey extends Monkey{

	public DartMonkey(int x, int y) {
		super(200, 50, 1, 2, x, y);
		// TODO Auto-generated constructor stub
	}

}

public class Monkey {
	private int damage;
	private int attackSpeed;
	private int cost;
	private int range;
	private boolean canPopLead;
	private boolean canPopIce;
	private boolean canPopCamo;
	private int x;
	private int y;
	
	
	public Monkey(int pCost, int pRange, int pDamage, int pAttackSpeed) {
		damage = pDamage;
		cost = pCost;
		range = pRange;
		attackSpeed = pAttackSpeed;
		canPopLead = false;
		canPopIce = false;
		canPopCamo = false;
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
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
}

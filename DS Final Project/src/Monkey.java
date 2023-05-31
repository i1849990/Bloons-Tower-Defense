import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Monkey {
	protected int pierce;
	protected int attackSpeed;
	protected int cost;
	protected int range;
	protected int x;
	protected int y;
	protected int centeredX;
	protected int centeredY;
	protected String name;
	protected double rotation; // in radians
	protected Rectangle hitbox; // hitbox used for checking if it overlaps with another monkey the user is trying to place
	protected int[] upgradeCosts; // should contain 2 values for a monkey's upgrades
	protected static BufferedImage[][] upgradeImages; // contains the image icons for the monkey upgrades
	protected String[] upgradeDescriptions; // contains the upgrade flavor text
	protected boolean[] upgradesPurchased; // are upgrades purchased
	protected int lastAttackFrame;
	protected int sellPrice;
	protected int delayBetweenFrames;
	protected BufferedImage image;
	protected BufferedImage[] images; // meant to be overriden
	
	public Monkey(int pCost, int pRange, int pPierce, int pAttackSpeed, int x, int y, int currFrame, Rectangle hitbox) {
		this.x = x;
		this.y = y;
		pierce = pPierce;
		cost = pCost;
		range = pRange;
		attackSpeed = pAttackSpeed;
		upgradesPurchased = new boolean[]{false, false};
		lastAttackFrame = currFrame;
		this.hitbox = hitbox;
	}
	
	public static void initializeImages() {
		try {
			upgradeImages = new BufferedImage[5][2];
			File file;
			
			file = new File("dartMonkeyUpgrade0.png");
			upgradeImages[0][0] = ImageIO.read(file);
			file = new File("dartMonkeyUpgrade1.png");
			upgradeImages[0][1] = ImageIO.read(file);
			
			file = new File("tackShooterUpgrade0.png");
			upgradeImages[1][0] = ImageIO.read(file);
			file = new File("tackShooterUpgrade1.png");
			upgradeImages[1][1] = ImageIO.read(file);
			
			file = new File("iceMonkeyUpgrade0.png");
			upgradeImages[2][0] = ImageIO.read(file);
			file = new File("iceMonkeyUpgrade1.png");
			upgradeImages[2][1] = ImageIO.read(file);
			
			file = new File("bombTowerUpgrade0.png");
			upgradeImages[3][0] = ImageIO.read(file);
			file = new File("bombTowerUpgrade1.png");
			upgradeImages[3][1] = ImageIO.read(file);
			
			file = new File("superMonkeyUpgrade0.png");
			upgradeImages[4][0] = ImageIO.read(file);
			file = new File("superMonkeyUpgrade1.png");
			upgradeImages[4][1] = ImageIO.read(file);
			
			TackShooter.initialize();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getUpgradeStatus(int cash) {
		String[] res = new String[2];
		for(int i = 0; i < 2; i++) {
			String str = "";
			if(upgradesPurchased[i]) {
				str = "purchased";
			}else if(cash >= upgradeCosts[i]) {
				str = "purchasable";
			}else {
				str = "unpurchasable";
			}
			res[i] = str;
		}
		
		return res;
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
	
	public String getName() {
		return name;
	}
	
	public int getSellPrice() {
		return sellPrice;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	// returns null, is meant to be overriden
	public BufferedImage[] getUpgradeImages() {
		return null;
	}
	
	// meant to be overriden
	public void updateUpgrades() {
		
	}
	
	// meant to be overriden
	public void updateImage(int num) {
		System.out.println("something wrong");
	}
	
	public void purchaseUpgrade0() {
		upgradesPurchased[0] = true;
		updateUpgrades();
	}
	
	public void purchaseUpgrade1() {
		upgradesPurchased[1] = true;
		updateUpgrades();
	}
	
}

class DartMonkey extends Monkey{
	public static BufferedImage[] images;

	public DartMonkey(int x, int y, int currFrame, Rectangle hitbox) {
		super(250, 100, 1, 30, x, y, currFrame, hitbox);
		name = "Dart Monkey";
		upgradeCosts = new int[] {210, 100};
		upgradeDescriptions = new String[] {"Piercing Darts", "Extra Range"};
		sellPrice = 200;
		delayBetweenFrames = 1;
	}
	
	public void updateImage(int currFrame) {
		int framesPassed = currFrame - lastAttackFrame;
		
		if(framesPassed >= images.length * delayBetweenFrames) {
			image = images[0];
			return;
		}
		
		image = images[1 + framesPassed / delayBetweenFrames];
	}
	
	public void updateUpgrades() {
		if(upgradesPurchased[0]) {
			pierce = 2;
			sellPrice = 368;
		}
		if(upgradesPurchased[1]) {
			range = 125;
			sellPrice = 280;
		}
		if(upgradesPurchased[0] && upgradesPurchased[1]) {
			sellPrice = 448;
		}
	}
	
	public static void initialize() {
		try {
			images = new BufferedImage[5];
			File file;
			
			file = new File("DMonkey.png");
			BufferedImage dartSprites = ImageIO.read(file);
			
			for(int i = 0; i < 5; i++) {
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage[] getUpgradeImages() {
		return upgradeImages[0];
	}
}

class TackShooter extends Monkey{
	public static BufferedImage[] images;

	public TackShooter(int x, int y, int currFrame, Rectangle hitbox) {
		super(400, 70, 1, 30, x, y, currFrame, hitbox);
		name = "Tack Shooter";
		upgradeCosts = new int[] {250, 150};
		upgradeDescriptions = new String[] {"Faster Shooting", "Extra Range"};
		sellPrice = 320;
		delayBetweenFrames = 3;
	}
	
	public void updateImage(int currFrame) {
		int framesPassed = currFrame - lastAttackFrame;
		
		if(framesPassed >= (images.length - 1) * delayBetweenFrames) {
			image = images[0];
			return;
		}
		image = images[1 + framesPassed / delayBetweenFrames];
	}
	
	public static void initialize() {
		
		try {
			images = new BufferedImage[5];
			File file;
			
			file = new File("tackShooter250x50.png");
			BufferedImage tackSprites = ImageIO.read(file);
			
			for(int i = 0; i < 5; i++) {
				images[i] = tackSprites.getSubimage(50 * i, 0, 50, 50);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateUpgrades() {
		if(upgradesPurchased[0]) {
			// increase attack speed
			sellPrice = 520;
		}
		if(upgradesPurchased[1]) {
			range = 80;
			sellPrice = 440;
		}
		if(upgradesPurchased[0] && upgradesPurchased[1]) {
			sellPrice = 640;
		}
	}
	
	public BufferedImage[] getUpgradeImages() {
		return upgradeImages[1];
	}
}

class IceMonkey extends Monkey{

	public IceMonkey(int x, int y, int currFrame, Rectangle hitbox) {
		super(850, 70, 1, 30, x, y, currFrame, hitbox);
		name = "Ice Monkey";
		upgradeCosts = new int[] {250, 150};
		upgradeDescriptions = new String[] {"Longer Freeze", "Wider Freeze"};
		sellPrice = 680;
	}
	
	public void updateUpgrades() {
		if(upgradesPurchased[0]) {
			//long freeze time
			sellPrice = 880;
		}
		if(upgradesPurchased[1]) {
			range = 75;
			sellPrice = 800;	
		}
		if(upgradesPurchased[0] && upgradesPurchased[1]) {
			sellPrice = 1000;
		}
	}
	
	public BufferedImage[] getUpgradeImages() {
		return upgradeImages[2];
	}
}

class BombTower extends Monkey{

	public BombTower(int x, int y, int currFrame, Rectangle hitbox) {
		super(900, 120, 1, 30, x, y, currFrame, hitbox); //customize pierce
		name = "Bomb Tower";
		upgradeCosts = new int[] {650, 250};
		upgradeDescriptions = new String[] {"Bigger Bombs", "Extra Range"};
		sellPrice = 720;
	}
	
	public void updateUpgrades() {
		if(upgradesPurchased[0]) {
			//bigger projectile
			// update sell price 
		}
		if(upgradesPurchased[1]) {
			range = 140;
			// update sell price 
		}
		if(upgradesPurchased[0] && upgradesPurchased[1]) {
			// update sell price 
		}
	}
	
	public BufferedImage[] getUpgradeImages() {
		return upgradeImages[3];
	}
}

class SuperMonkey extends Monkey{

	public SuperMonkey(int x, int y, int currFrame, Rectangle hitbox) {
		super(4000, 140, 1, 30, x, y, currFrame, hitbox); //get atk spd frames
		name = "Super Monkey";
		upgradeCosts = new int[] {4500, 2400};
		upgradeDescriptions = new String[] {"Laser Blasts", "Epic Range"};
		sellPrice = 3200;
	}
	
	public void updateUpgrades() {
		if(upgradesPurchased[0]) {
			pierce = 2; 
			sellPrice = 6800;
		}
		if(upgradesPurchased[1]) {
			range = 175;
			sellPrice = 5120;
		}
		if(upgradesPurchased[0] && upgradesPurchased[1]) {
			sellPrice = 8720;
		}
	}
	
	public BufferedImage[] getUpgradeImages() {
		return upgradeImages[4];
	}
}

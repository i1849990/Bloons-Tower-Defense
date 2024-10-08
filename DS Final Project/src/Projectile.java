import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Projectile {
	private String name;
	private BufferedImage image;
	private int x;
	private int y;
	private double exactX;
	private double exactY;
	private int speed;
	private int damage;
	private double rotation; // radians
	private int pierce;
	private Rectangle hitbox;
	private ArrayList<Bloon> bloonsPierced;
	public static BufferedImage dartImage;
	public static BufferedImage tackImage;
	public static BufferedImage bombImage;
	private int framesActiveFor;

	public Projectile(String name, int centeredX, int centeredY, int speed, double rotation, int pierce) {
		framesActiveFor = 0;
		this.name = name;
		
		switch(name) {
		case"dart":
			x = centeredX - 5;
			y = centeredY - 21;
			break;
		case"tack":
			x = centeredX - 6;
			y = centeredY - 15;
			break;
		case"bomb":
			x = centeredX - 12;
			y = centeredY - 15;
			break;
		}
		exactX = x;
		exactY = y;
		this.speed = speed;
		this.rotation = rotation;
		this.pierce = pierce;
		bloonsPierced = new ArrayList<Bloon>();
		
		handleImage();
		updateXY();
		updateHitbox();
	}
	
	public static void initializeImages() {
		try {
			dartImage = ImageIO.read(new File("dart10x43.png"));
			tackImage = ImageIO.read(new File("tack13x30.png"));
			bombImage = ImageIO.read(new File("bomb25x30.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleImage() {
		switch(name) {
		case"dart":
			image = ScreenGUI.rotate(dartImage, rotation + (Math.PI / 2));
			break;
		case"tack":
			image = ScreenGUI.rotate(tackImage, rotation + (Math.PI / 2));
			break;
		case"bomb":
			image = bombImage;
			break;
		}
	}
	
	public void move() {
		exactX += Math.cos(rotation) * speed;
		exactY += Math.sin(rotation) * speed;
		updateXY();
		updateHitbox();
		framesActiveFor++;
	}
	
	private void updateXY() {
		x = (int) exactX;
		y = (int) exactY;
	}
	
	public boolean handleCollision(Bloon b) {
		pierce--;
		if(pierce <= 0) {
			return true;
		}
		
		bloonsPierced.add(b);
		
		return false;
	}
	
	private void updateHitbox() {
		hitbox = new Rectangle(x, y, image.getWidth(), image.getHeight());
	}
	
	public boolean outOfBounds() {
		return (x <= -50 || x >= 650 || y <= -50 || y >= 650 || framesActiveFor >= 15);
	}
	
	public boolean hasPiercedBloon(Bloon b) {
		return bloonsPierced.contains(b);
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public int getPierce() {
		return pierce;
	}

	public void setPierce(int pierce) {
		this.pierce = pierce;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public double getExactX() {
		return exactX;
	}

	public void setExactX(double exactX) {
		this.exactX = exactX;
	}

	public double getExactY() {
		return exactY;
	}

	public void setExactY(double exactY) {
		this.exactY = exactY;
	}	
	
	public String getName() {
		return name;
	}
	
}

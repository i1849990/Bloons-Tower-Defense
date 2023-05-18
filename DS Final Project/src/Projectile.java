import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Projectile {
	public BufferedImage image;
	public int x;
	public int y;
	private int speed;
	private int damage;
	private double direction; // radians
	private int pierce;
	private Rectangle hitbox;
	private ArrayList<Bloon> bloonsPierced;
	

	public Projectile(BufferedImage image, int x, int y, int speed, int direction, int pierce) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = direction;
		this.pierce = pierce;
		bloonsPierced = new ArrayList<Bloon>();
	}
	
	public void move() {
		x += (int) (Math.sin(direction) * speed);
		y += (int) (Math.cos(direction) * speed);
	}
	
	public boolean handleCollision(Bloon b) {
		pierce--;
		if(pierce <= 0) {
			return true;
		}
		
		bloonsPierced.add(b);
		
		return false;
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

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
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
	
}

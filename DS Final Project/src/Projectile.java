import java.awt.image.BufferedImage;
import java.io.File;

public class Projectile {
	public BufferedImage image;
	public int x;
	public int y;
	private int speed;
	public double direction; // radians
	public int pierce;
	
	public Projectile(BufferedImage image, int x, int y, int speed, int direction, int pierce) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = direction;
		this.pierce = pierce;
	}
	
	private void move() {
		x += (int) (Math.sin(direction) * speed);
		y += (int) (Math.cos(direction) * speed);
	}
	
}

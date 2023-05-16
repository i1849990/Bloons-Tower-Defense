import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Bloon {
	private int x;
	private int y;
	private int trackPoint;
	private int speed;
	private int layer;
	private boolean isFrozen;
	public BufferedImage image;
	
	
	public Bloon(int x, int y, int l, int s) {
		this.x = x;
		this.y = y;
		layer = l;
		speed = s;
		
		isFrozen = false;
		
	}
	
	private int getTrackPoint() {
		return trackPoint;
	}
	
	private int getX() {
		return x;
	}
	
	private int getY() {
		return x;
	}
	
	private void setX(int n) {
		x = n;
	}
	
	private void setY(int n) {
		y = n;
	}
	
	private void setSpeed(int s) {
		speed = s;
	}
	
	private void update() {
		// should update the bloon's speed and image based on layer count
	}

}

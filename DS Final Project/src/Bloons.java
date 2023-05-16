import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Bloons {
	
	
	private int x;
	private int y;
	private int trackPoint;
	private int speed;
	private int layer;
	private boolean isCamo;
	private boolean isSteel;
	
	
	
	public Bloons(int x, int y, int l, int s, boolean camo, boolean steel) {
		
		this.x = x;
		this.y = y;
		layer = l;
		speed = s;
		isCamo = camo;
		isSteel = steel;
		
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

}

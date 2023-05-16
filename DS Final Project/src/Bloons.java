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
	private String color;
	private boolean isCamo;
	private boolean isSteel;
	
	
	
	public Bloons(int x, int y, int s, String c, boolean camo, boolean steel) {
		
		this.x = x;
		this.y = y;
		speed = s;
		color = c;
		isCamo = camo;
		isSteel = steel;
		
	}
	
	private int getTrackPoint() {
		return trackPoint;
	}
	//method
	

}

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
	private int centeredX;
	private int centeredY;
	private int trackPoint;
	private int layer;
	private boolean isFrozen;
	public static BufferedImage[] images;
	public static int[] speeds;
	private int speed;
	public Rectangle hitbox;
	
	public Bloon(Track t, int layer) {
		trackPoint = 1;
		x = t.xPoints[0];
		y = t.yPoints[0];
		this.layer = layer;
		speed = speeds[layer];
	}
	
	// returns true if the bloon reaches the end of the track
	private boolean moveTowardsNextPoint(Track t) {
		int nextGoalX = t.xPoints[trackPoint];
		int nextGoalY = t.yPoints[trackPoint];
		
		String dir1 = getNextDirection(centeredX, centeredY, nextGoalX, nextGoalY);
		
		switch(dir1) {
		case"right":
			centeredX += speed;
			break;
		case "left":
			centeredX -= speed;
			break;
		case "down":
			centeredY += speed;
			break;
		case "up":
			centeredY -= speed;
			break;
		}
		
		String dir2 = getNextDirection(centeredX, centeredY, nextGoalX, nextGoalY);
		
		
		if(!dir1.equals(dir2) || (centeredX == nextGoalX && centeredY == nextGoalY)) {
			// the bloon has passed the point, needs to
			trackPoint++;
			if(trackPoint > t.xPoints.length) {
				// bloon has passed the point
				return true;
			}
			
			int distOvershot = 0;
			
			switch(dir1) {
			case"right":
				distOvershot = centeredX - nextGoalX;
				break;
			case "left":
				distOvershot = nextGoalX - centeredX;
				break;
			case "down":
				distOvershot = centeredY - nextGoalY;
				break;
			case "up":
				distOvershot = nextGoalY - centeredY;
				break;
			}
			
			switch(dir2) {
			case"right":
				centeredX += distOvershot;
				break;
			case "left":
				centeredX -= distOvershot;
				break;
			case "down":
				centeredY += distOvershot;
				break;
			case "up":
				centeredY -= distOvershot;
				break;
			}
			
		}
		
		return false;
	}
	
	private String getNextDirection(int centeredX, int centeredY, int goalX, int goalY) {
		String dir = "";
		
		if(centeredX < goalX) {
			dir = "right";
		}else if(centeredX > goalX) {
			dir = "left";
		}else if(centeredY < goalY) {
			dir = "down";
		}else  {
			dir = "up";
		}
		
		return dir;
	}
	
	private void updatePositionFromCenteredXY() {
		int imgWidth = images[layer].getWidth();
		int imgHeight = images[layer].getHeight();
		x = centeredX - imgWidth / 2;
		y = centeredY - imgHeight / 2;
	}
	
	private void updateHitbox() {
		int imgWidth = images[layer].getWidth();
		int imgHeight = images[layer].getHeight();
		hitbox = new Rectangle(x, y, imgWidth, imgHeight);
	}
	
	private void updateSpeed() {
		speed = speeds[layer];
	}
	
	public void update() {
		updateSpeed();
		updatePositionFromCenteredXY();
		updateHitbox();
	}
	
	public void move(Track t) {
		if(!isFrozen) {
			moveTowardsNextPoint(t);
		}
		update();
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Collection;

public class Bloon implements Comparable<Bloon>{
	private int x;
	private int y;
	private int centeredX;
	private int centeredY;
	private int trackPoint;
	private double trackDist;
	private int layer;
	private boolean isFrozen;
	private static Track t;
	public static BufferedImage[] images;
	public static double[] speeds = {2,2,3,4,3,3};
	private double speed;
	public Rectangle hitbox;
	
	public Bloon(Track t, int layer) {
		this.t = t;
		trackPoint = 1;
		centeredX = t.xPoints[0];
		centeredY = t.yPoints[0];
		this.layer = layer;
		speed = speeds[layer];
		trackDist = 0;
	}
	
	public void initializeImages() {
		images = new BufferedImage[6];
	}
	
	// returns true if the bloon reaches the end of the track
//	private boolean moveTowardsNextPoint(Track t) {
//		if(trackPoint >= t.xPoints.length) {
//			// bloon has passed the point
//			return true;
//		}
//		
//		int nextGoalX = t.xPoints[trackPoint];
//		int nextGoalY = t.yPoints[trackPoint];
//		
//		trackDist += speed;
//		
//		String dir1 = getNextDirection(nextGoalX, nextGoalY);
//		
//		switch(dir1) {
//		case"right":
//			centeredX += speed;
//			break;
//		case "left":
//			centeredX -= speed;
//			break;
//		case "down":
//			centeredY += speed;
//			break;
//		case "up":
//			centeredY -= speed;
//			break;
//		}
//		
//		String dir2 = getNextDirection(nextGoalX, nextGoalY);
//		
//		if(!dir1.equals(dir2) || (centeredX == nextGoalX && centeredY == nextGoalY)) {
//			// the bloon has passed the point, needs to
//			trackPoint++;
//			if(trackPoint >= t.xPoints.length) {
//				// bloon has passed the point
//				return true;
//			}
//			
//			int distOvershot = 0;
//			
//			switch(dir1) {
//			case"right":
//				distOvershot = centeredX - nextGoalX;
//				break;
//			case "left":
//				distOvershot = nextGoalX - centeredX;
//				break;
//			case "down":
//				distOvershot = centeredY - nextGoalY;
//				break;
//			case "up":
//				distOvershot = nextGoalY - centeredY;
//				break;
//			}
//			
//		}
//		
//		return false;
//	}
	
	private boolean moveRec(double distRemaining, String nextDir) {
		if(trackPoint >= t.xPoints.length) {
			return true;
		}
		
		int nextGoalX = t.xPoints[trackPoint];
		int nextGoalY = t.yPoints[trackPoint];
		
		String dir1 = getNextDirection(nextGoalX, nextGoalY);
		
		switch(dir1) {
		case"right":
			centeredX += distRemaining;
			break;
		case "left":
			centeredX -= distRemaining;
			break;
		case "down":
			centeredY += distRemaining;
			break;
		case "up":
			centeredY -= distRemaining;
			break;
		}
		
		String dir2 = getNextDirection(nextGoalX, nextGoalY);
		
		if(dir1.equals(dir2)) {
			return false;
		}
		
		trackPoint++;
		double distOvershot = 0;
		
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
		
		return moveRec(distOvershot, dir2);
	}
	
	private boolean startMoveRec() {
		centeredX = -50;
		centeredY = 280;
		
		System.out.println(trackDist);
		return moveRec(trackDist,"right");
	}
	
	private String getNextDirection(int goalX, int goalY) {
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
		// int imgWidth = images[layer].getWidth();
		// int imgHeight = images[layer].getHeight();
		// x = centeredX - imgWidth / 2;
		// y = centeredY - imgHeight / 2;
		// TEST ONLY:
	}
	
	private void updateHitbox() {
		// int imgWidth = images[layer].getWidth();
		// int imgHeight = images[layer].getHeight();
		// hitbox = new Rectangle(x, y, imgWidth, imgHeight);
	}
	
	private void updateSpeed() {
		speed = speeds[layer];
	}
	
	public void update() {
		updateSpeed();
		updatePositionFromCenteredXY();
		updateHitbox();
	}
	
	public boolean move() {
		if(!isFrozen) {
			update();
			trackDist += speed;
			return startMoveRec();
		}
		update();
		return false;
	}
	
	public boolean popLayer() {
		if(layer == 5) {
			layer -= 2; // white bloon should skip to yellow
		}else {
			layer--;
		}
		
		if(layer < 0) {
			return true;
		}
		
		update();
		return false;
	}
	
	public int getLayersToBePopped() {
		return 1;
	}
	
	private int getTrackPoint() {
		return trackPoint;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return x;
	}
	
	private void setX(int n) {
		x = n;
	}
	
	private void setY(int n) {
		y = n;
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

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	public static BufferedImage[] getImages() {
		return images;
	}

	public static void setImages(BufferedImage[] images) {
		Bloon.images = images;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public void setTrackPoint(int trackPoint) {
		this.trackPoint = trackPoint;
	}

	@Override
	public int compareTo(Bloon b) {
		// TODO Auto-generated method stub
		if(trackDist == b.trackDist) {
			return 0;
		}else if(trackDist > b.trackDist) {
			return 1;
		}else {
			return -1;
		}
	}

}

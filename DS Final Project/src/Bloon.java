import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import javax.imageio.ImageIO;

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
	public static double[] speeds = {1.8,2.0,3,4,3,3};
	private double speed;
	public Rectangle hitbox;
	
	public Bloon(Track t, int layer) {
		this.t = t;
		trackPoint = 1;
		centeredX = t.xPoints[0];
		centeredY = t.yPoints[0];
		updatePositionFromCenteredXY();
		this.layer = layer;
		speed = speeds[layer];
		trackDist = 0;
	}
	
	public static void initializeImages() {
		images = new BufferedImage[6];
		
		try {
			File file;
			file = new File("redbloon.png");
			images[0] = ImageIO.read(file);
			file = new File("bluebloon.png");
			images[1] = ImageIO.read(file);
			file = new File("greenbloon.png");
			images[2] = ImageIO.read(file);
			file = new File("yellowbloon.png");
			images[3] = ImageIO.read(file);
			file = new File("blackbloon.png");
			images[4] = ImageIO.read(file);
			file = new File("whitebloon.png");
			images[5] = ImageIO.read(file);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// returns true if the bloon reaches the end of the track
	private boolean moveRec(double distRemaining, String nextDir) {
		int nextGoalX = t.xPoints[trackPoint];
		int nextGoalY = t.yPoints[trackPoint];
		
		// move the bloon towards the next track point
		double distOvershot = 0;
		switch(nextDir) {
		case"right":
			centeredX += distRemaining;
			distOvershot = centeredX - nextGoalX;
			break;
		case "left":
			centeredX -= distRemaining;
			distOvershot = nextGoalX - centeredX;
			break;
		case "down":
			centeredY += distRemaining;
			distOvershot = centeredY - nextGoalY;
			break;
		case "up":
			centeredY -= distRemaining;
			distOvershot = nextGoalY - centeredY;
			break;
		}
		
		// if a bloon moves towards its next point and does not overshoot
		// then it is done wit3h the process, and it returns false (it hasn't reached the end)
		String dir2 = getNextDirection(nextGoalX, nextGoalY);
		if(nextDir.equals(dir2)) {
			return false;
		}
		
		// a bloon has reached the end of the track, return true
		trackPoint++;
		if(trackPoint >= t.xPoints.length) {
			return true;
		}
		
		// set the bloon to the track point and prepare for the next recursion
		centeredX = nextGoalX;
		centeredY = nextGoalY;
		String dir3 = getNextDirection(t.xPoints[trackPoint], t.yPoints[trackPoint]);
		
		return moveRec(distOvershot, dir3);
	}
	
	private boolean startMoveRec() {
		// starting point of the track, offscreen to the left
		centeredX = -50;
		centeredY = 280;
		
		// starts moving towards the SECOND point, because it starts at the first one
		// (second point is trackpoint 1)
		trackPoint = 1;
		
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
	
	public boolean move() {
		if(!isFrozen) {
			trackDist += speed;
			boolean res = startMoveRec();
			update();
			return res;
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
	
	public BufferedImage getImage() {
		return images[layer];
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
		return y;
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
	// used for sorting 
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

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class Track {
	public int[] xPoints;
	public int[] yPoints;
	
	public Polygon polygon;
	
	public Track() {
		this.xPoints = new int[] {-50, 120, 120, 270, 270, 70, 70, 530, 530, 380, 380, 530, 530, 330, 330};
		this.yPoints = new int[] {280, 280, 130, 130, 430, 430, 540, 540, 370, 370, 230, 230, 70, 70, -50};
		
		int[] pXPoints = new int[] {-50, 90, 90, 290, 290, 95, 95, 500, 500, 355, 355, 500, 500, 300, 300, 355, 355, 560, 560, 405, 405, 560, 560, 40, 40, 235, 235, 145, 145, -50};
		int[] pYPoints = new int[] {260, 260, 100, 100, 460, 460, 505, 505, 400, 400, 200, 200, 100, 100, -50, -50, 40, 40, 260, 260, 345, 345, 560, 560, 405, 405, 155, 155, 310, 310};
		
		polygon = new Polygon(pXPoints, pYPoints, 30);
	}
	
	public boolean intersectsRect(Rectangle2D rect) {
		return polygon.intersects(rect);
	}
	
}

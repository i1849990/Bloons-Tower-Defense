import java.awt.image.BufferedImage;
import java.io.File;

public class Track {
	public int[] xPoints;
	public int[] yPoints;
	
	private BufferedImage image;
	
	public Track(int[] xPoints, int[] yPoints, BufferedImage image) {
		this.xPoints = xPoints;
		this.yPoints = yPoints;
		this.image = image;
	}
	
}

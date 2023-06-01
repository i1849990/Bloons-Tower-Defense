import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VisualEffect {
	private int startFrame;
	private int endFrame;
	private static BufferedImage bloonPop;
	private static BufferedImage bombExplosionImages[];
	
	public static void intitializeImages() {
		 try {
			bloonPop = ImageIO.read(new File("bloonpop.png"));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VisualEffect {
	private int startFrame;
	private int endFrame;
	private static BufferedImage bloonPop;
	private static BufferedImage bombExplosionImages[];
	private String effectName;
	private int x;
	private int y;
	
	private int popSpeed = 10;
	private int bombSpeed = 40;
	
	public static void intitializeImages() {
		 try {
			bloonPop = ImageIO.read(new File("pop22x24.png"));
			
			bombExplosionImages = new BufferedImage[6];
			BufferedImage bombSpriteImage = ImageIO.read(new File("bombExploding600x100.png"));
			for(int i = 0; i < 6; i++) {
				bombExplosionImages[i] = bombSpriteImage.getSubimage(i*100, 0, 100, 100);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public VisualEffect(int currFrame, String effectName, int centeredX, int centeredY) {
		this.effectName = effectName;
		startFrame = currFrame;
		switch(effectName) {
		case"pop":
			x = centeredX - 11;
			y = centeredY - 12;
			endFrame = startFrame + popSpeed;
			break;
		case"bomb":
			x = centeredX - 50;
			y = centeredY - 50;
			endFrame = startFrame + 6 * bombSpeed;
			break;
		}
	}
	
	public BufferedImage getImage(int currFrame) {
		switch(effectName) {
		case"pop":
			return bloonPop;
		case"bomb":
			return bombExplosionImages[currFrame / bombSpeed];
		}
		return null;
	}
	
	public boolean isDone(int currFrame) {
		switch(effectName) {
		case"pop":
			return currFrame >= popSpeed;
		case"bomb":
			return currFrame >= 6 * bombSpeed;
		}
		return true;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}

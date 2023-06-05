import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VisualEffect {
	private int startFrame;
	private static BufferedImage bloonPop;
	private static BufferedImage bombExplosionImages[];
	private String effectName;
	private int x;
	private int y;
	
	private int popSpeed = 4;
	private int bombSpeed = 2;
	
	public static void intitializeImages() {
		 try {
			bloonPop = ImageIO.read(new File("pop44x47.png"));
			
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
			x = centeredX - 22;
			y = centeredY - 23;
			//endFrame = startFrame + popSpeed;
			break;
		case"bomb":
			x = centeredX - 50;
			y = centeredY - 50;
			//endFrame = startFrame + 6 * bombSpeed;
			break;
		}
	}
	
	public BufferedImage getImage(int currFrame) {
		switch(effectName) {
		case"pop":
			return bloonPop;
		case"bomb":
			return bombExplosionImages[(currFrame - startFrame) / bombSpeed];
		}
		return null;
	}
	
	public boolean isDone(int currFrame) {
		switch(effectName) {
		case"pop":
			return currFrame - startFrame >= popSpeed;
		case"bomb":
			return currFrame - startFrame >= 6 * bombSpeed - 1;
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

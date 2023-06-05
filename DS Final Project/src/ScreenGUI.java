import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ScreenGUI extends JPanel implements MouseMotionListener, MouseListener, ActionListener {
	private Game game;
	private Timer t;
	private int mouseX;
	private int mouseY;
	
	BufferedImage introMessage;
	BufferedImage map;
	BufferedImage[] towerDesc;
	private Monkey selectedMonkey;
	private String monkeyToBePlaced;
	private String hoveredMonkey;
	int currFrame;
	
	long sum; int nums;
	
	public ScreenGUI() {
		JFrame frame = new JFrame("Bloons Tower Defense");
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		
		game = new Game(new Track());
		
		frame.setSize(800 + 25, 600 + 50); // handles size of the frame
		
		t = new Timer(32, this);
		t.start();
		
		try {
			towerDesc = new BufferedImage[5];
			towerDesc[0] = ImageIO.read(new File("dartDesc.png"));
			towerDesc[1] = ImageIO.read(new File("tackDesc.png"));
			towerDesc[2] = ImageIO.read(new File("iceDesc.png"));
			towerDesc[3] = ImageIO.read(new File("bombDesc.png"));
			towerDesc[4] = ImageIO.read(new File("superDesc.png"));
			
			introMessage = ImageIO.read(new File("introMessage.png"));
			
			map = ImageIO.read(new File("map.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		currFrame = 0;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 8000, 6000);
		
		game.nextFrame();
		currFrame++;
		game.updateFrame(currFrame);
		
		drawBackground(g);
		drawSelectedMonkeyRange(g);
		drawProjectiles(g);
		drawMonkeys(g);
		drawBloons(g);
		drawEffects(g);
		drawGUI(g);
		drawMonkeyToBePlaced(g);
		drawMonkeyDescription(g);
		
		if(game.getRound() == 0) {
			drawIntroMessage(g);
		}
	}
	
	public void drawBackground(Graphics g) {
		g.drawImage(map,0,0,800,600, this);
	}
	
	public void drawProjectiles(Graphics g) {
		for(Projectile p : game.projectiles) {
			g.drawImage(p.getImage(), p.getX(), p.getY(), this);
		}
	}
	
	public void drawVisualEffects(Graphics g) {
		for(VisualEffect v : game.effects) {
			g.drawImage(v.getImage(currFrame), v.getX(), v.getY(), this);
		}
	}
	
	public void drawMonkeys(Graphics g) {
		for(Monkey m : game.monkeys) {
			m.updateImage(currFrame);
			double rotation =  m.getRotation();
			
			//test: 
			BufferedImage toDraw = null;
			switch(m.getName()) {
			case"Dart Monkey":
				toDraw = getScaledInstance(m.getImage(), 49, 45);
				break;
			case"Tack Shooter":
				rotation = 0;
				toDraw =  m.getImage();
				break;
			case"Ice Monkey":
				rotation = 0;
				toDraw =  m.getImage();
				break;
			case"Bomb Tower":
				rotation += Math.PI / 2;
				toDraw =  m.getImage();
				break;
			case"Super Monkey":
				toDraw = getScaledInstance(m.getImage(), 57, 53);
				break;
			}
			
			toDraw = rotate(toDraw, rotation);
			g.drawImage((Image) toDraw,m.getX(), m.getY(), this);
		}
	}
	
	public void drawBloons(Graphics g) {
		for (Bloon b : game.bloons) {
			g.drawImage(b.getImage(), b.getX(), b.getY(), this);
		}
	}
	
	public void drawGUI(Graphics g) {
		drawMonkeyGUI(g);
		drawMenuGUI(g);
	}
	
	public void drawMonkeyGUI(Graphics g) {
		if(selectedMonkey == null) {
			return;
		}
		
		g.setColor(new Color(185, 220, 184));
		g.fillRect(600, 198, 182, 297);
		g.setColor(new Color(7, 124, 5));
		g.fillRect(609, 223, 164, 4);
		
		Font bold = new Font("Trebuchet MS", 1, 20);
		Font boldSmall = new Font("Trebuchet MS", 1, 18);
		Font small = new Font("Trebuchet MS", 0, 18);
		
		int height0 = 245;
		int height1 = 265;
		
		g.setFont(bold);
		String name = selectedMonkey.getName();
		switch(name) {
		case "Dart Monkey":
			g.drawString(name, 635, 218);
			break;
		case "Tack Shooter":
			g.drawString(name, 635, 218);
			break;
		case "Ice Monkey":
			g.drawString(name, 635, 218);
			break;
		case "Bomb Tower":
			g.drawString(name, 635, 218);
			break;
		case "Super Monkey":
			g.drawString(name, 635, 218);
			break;
		}
		
		g.setFont(boldSmall);
		g.drawString("Speed:", 620, height0);
		g.drawString("Range:", 620, height1);
		
		g.setFont(small);
		switch(name) {
		case "Dart Monkey":
			g.drawString("Fast", 710, height0);
			break;
		case "Tack Shooter":
			g.drawString("Medium", 695, height0);
			break;
		case "Ice Monkey":
			g.drawString("Slow", 710, height0);
			break;
		case "Bomb Tower":
			g.drawString("Medium", 695, height0);
			break;
		case "Super Monkey":
			g.drawString("Hypersonic", 683, height0);
			break;
		}
		
		int range = selectedMonkey.getRange();
		g.drawString("" + range, 710, height1);
		
		Color[] upgradeColors = new Color[2];
		Color[] textColors = new Color[2];
		String[] upgradeStatus = selectedMonkey.getUpgradeStatus(game.getCash());
		
		for(int i = 0; i < 2; i++) {
			String guiStatus0 = "";
			String guiStatus1 = "";
			int xOffset0 = 0;
			int xOffset1 = 0;
			int yOffset = 0;
			
			switch(upgradeStatus[i]) {
			case "purchased":
				upgradeColors[i] = new Color(51, 204, 51);
				textColors[i] = new Color(172, 225, 175);
				guiStatus0 = "Already";
				guiStatus1 = "Bought";
				yOffset = 400;
				xOffset0 = 3;
				xOffset1 = 5;
				break;
			case "purchasable":
				if(mouseHoveringUpgrades()[i]) {
					upgradeColors[i] = new Color(74, 240, 74);
					textColors[i] = new Color(172, 249, 175);
				}else {
					upgradeColors[i] = new Color(74, 180, 74);
					textColors[i] = new Color(172, 225, 175);
				}
				guiStatus0 = "Buy for:";
				xOffset0 = 2;
				yOffset = 420;
				break;
			case "unpurchasable":
				upgradeColors[i] = new Color(167, 58, 45);
				textColors[i] = new Color(172, 225, 175);
				guiStatus0 = "Can't";
				guiStatus1 = "Afford";
				xOffset0 = 15;
				xOffset1 = 8;
				yOffset = 400;
				break;
			}
			
			// upgrade box color
			g.setColor(upgradeColors[i]);
			g.fillRect(605 + 88 * i, 274, 85, 180);
			
			// upgrade images
			g.drawImage(selectedMonkey.getUpgradeImages()[i],605 + 88 * i,274,this);
			
			// upgrade affording status
			g.setColor(textColors[i]);
			g.setFont(bold);
			g.drawString(guiStatus0, 610 + 88 * i + xOffset0, yOffset);
			g.drawString(guiStatus1, 610 + 88 * i + xOffset1, yOffset + 20);
			
			// upgrade costs
			g.setColor(new Color(238, 255, 243));
			g.drawString("" + selectedMonkey.upgradeCosts[i], 633 + 88 * i, 445);
			
			// upgrade descriptions
			g.setFont(small);
			g.setColor(new Color(218, 240, 218));
			drawStringMultiLine((Graphics2D) g, selectedMonkey.upgradeDescriptions[i], 70, 615 + 88 * i, 360);
		}
		
		// sell button
		g.setColor(new Color(168, 70, 46));
		g.fillRect(605, 460, 173, 29);
		
		// "Sell for:" text
		g.setColor(new Color(210, 180, 164));
		g.drawString("Sell for:", 620, 481);
		
		// sell price text
		g.setColor(new Color(238, 255, 243));
		g.drawString("" + selectedMonkey.getSellPrice(), 715, 481);
	}
	
	public void drawMenuGUI(Graphics g) {
		// round, cash, lives text
		g.setFont(new Font("Trebuchet MS", 0, 25));
		g.drawString("" + game.getRound(), 698, 44);
		g.drawString("" + game.getCash(), 702, 75);
		g.drawString("" + game.getLives(), 684, 103);
		
		if(!game.getRoundInProgress()){
			// round start button
			g.setColor(new Color(162, 216, 162));
			g.fillRect(600, 500, 182, 63);
			
			// "Start Round" text
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("Trebuchet MS", 1, 30));
			g.drawString("Start Round", 608, 540);
		}
	}
	
	public void drawSelectedMonkeyRange(Graphics g) {
		if(selectedMonkey == null) {
			return;
		}
		
		Color transparentWhite = new Color(255, 255, 255, 100);
		g.setColor(transparentWhite);
		
		int radius = selectedMonkey.getRange();
		g.fillOval(selectedMonkey.centeredX - radius, selectedMonkey.centeredY - radius, radius * 2, radius * 2);
	}
	
	public void drawMonkeyToBePlaced(Graphics g){
		if(monkeyToBePlaced == null) {
			return;
		}
		int width = 30;
		int height = 30;
		
		//g.drawRect(mouseX, height, width, height);
		
		if(!isValidSpotToPlace()) {
			Color transparentRed = new Color(255, 0, 0, 100);
			g.setColor(transparentRed);
		}else {
			Color transparentWhite = new Color(255, 255, 255, 100);
			g.setColor(transparentWhite);
		}
		
		int radius = game.getDisplayRange(monkeyToBePlaced);
		g.fillOval(mouseX - radius, mouseY - radius, radius * 2, radius * 2);
		
		Image img = null;
		int imageW = 0;
		int imageH = 0;
		
		switch(monkeyToBePlaced) {
		case"dart":
			imageW = 49;
			imageH = 45;
			img = getScaledInstance(DartMonkey.images[0],49,45);
			break;
		case"tack":
			imageW = TackShooter.images[0].getWidth();
			imageH = TackShooter.images[0].getHeight();
			img = TackShooter.images[0];
			break;
		case"ice":
			imageW = IceMonkey.images[0].getWidth();
			imageH = IceMonkey.images[0].getHeight();
			img = IceMonkey.images[0];
			break;
		case"bomb":
			imageW = BombTower.images[0].getWidth();
			imageH = BombTower.images[0].getHeight();
			img = BombTower.images[0];
			break;
		case"super":
			imageW = 57;
			imageH = 53;
			img = getScaledInstance(SuperMonkey.images[0],57,53);
			break;
		}
		
		g.drawImage(img, mouseX - imageW / 2, mouseY - imageH / 2, this);
	}
	
	private void addMonkeyToBePlaced() {
		Monkey m = null;
		int width = 30;
		int height = 30;
		
		int imageW;
		int imageH;
		Rectangle toBePlacedHitbox = new Rectangle(mouseX - width / 2, mouseY - height / 2, width, height);
		switch(monkeyToBePlaced) {
		case"dart":
			imageW = 49;
			imageH = 45;
			m = new DartMonkey(mouseX - imageW / 2, mouseY - imageH / 2, currFrame, toBePlacedHitbox);
			break;
		case"tack":
			imageW = TackShooter.images[0].getWidth();
			imageH = TackShooter.images[0].getHeight();
			m = new TackShooter(mouseX - imageW / 2, mouseY - imageH / 2, currFrame, toBePlacedHitbox);
			break;
		case"ice":
			imageW = IceMonkey.images[0].getWidth();
			imageH = IceMonkey.images[0].getHeight();
			m = new IceMonkey(mouseX - imageW / 2, mouseY - imageH / 2, currFrame, toBePlacedHitbox);
			break;
		case"bomb":
			imageW = BombTower.images[0].getWidth();
			imageH = BombTower.images[0].getHeight();
			m = new BombTower(mouseX - imageW / 2, mouseY - imageH / 2, currFrame, toBePlacedHitbox);
			break;
		case"super":
			imageW = 57;
			imageH = 53;
			m = new SuperMonkey(mouseX - imageW / 2, mouseY - imageH / 2, currFrame, toBePlacedHitbox);
			break;
		}
		
		game.addMonkey(m);
		selectedMonkey = m;
		monkeyToBePlaced = null;
	}
	
	private void drawMonkeyDescription(Graphics g) {
		if(hoveredMonkey == null) {
			return;
		}
		int index = 0;
		switch(hoveredMonkey) {
		case"dart":
			index = 0;
			break;
		case"tack":
			index = 1;
			break;
		case"ice":
			index = 2;
			break;
		case"bomb":
			index = 3;
			break;
		case"super":
			index = 4;
			break;
		}
		BufferedImage desc = towerDesc[index];
		
		g.drawImage(desc, 600, 198, this);
		
	}
	
	private void drawEffects(Graphics g) {
		for(int i = 0; i < game.effects.size(); i++) {
			VisualEffect effect = game.effects.get(i);
			g.drawImage(effect.getImage(currFrame), effect.getX(), effect.getY(), this);
		}
	}
	
	private void drawIntroMessage(Graphics g) {
		g.drawImage(introMessage, 47, 496, this);
	}
	
	private boolean isValidSpotToPlace() {
		int width = 30;
		int height = 30;
		Rectangle toBePlacedHitbox = new Rectangle(mouseX - width / 2, mouseY - height / 2, width, height);
		
		return !game.intersectsObjects(toBePlacedHitbox) && mouseX <= 550 && mouseY <= 550;
	}
	
	public boolean[] mouseHoveringUpgrades() {
		Rectangle rect0 = new Rectangle(605, 274, 85, 180);
		Rectangle rect1 = new Rectangle(693, 274, 85, 180);
		if(rect0.contains(mouseX, mouseY)) {
			return new boolean[] {true, false};
		}else if(rect1.contains(mouseX, mouseY)) {
			return new boolean[] {false, true};
		}else {
			return new boolean[] {false, false};
		}
	}
	
	public String getMonkeyButtonClickedOn() {
		int[] xLocations = {619, 656, 693, 729, 766};
		String[] monkeys = {"dart", "tack", "ice", "bomb", "super"};
		
		for(int i = 0; i < xLocations.length; i++) {
			int n = xLocations[i];
			if(mouseLiesInCircle(n,171,17)) {
				return monkeys[i];
			}
		}
		
		return null;
	}
	
	private boolean mouseLiesInCircle(int circleX, int circleY, int radius) {
		return Math.pow(mouseX - circleX, 2) + Math.pow(mouseY - circleY, 2) <= Math.pow(radius, 2);
	}
	
	private Monkey getMonkeyClickedOn() {
		for(Monkey m : game.monkeys) {
			if(m.getHitbox().contains(mouseX, mouseY)) {
				return m;
			}
		}
		
		return null;
	}
	
	private boolean mouseClickedOnUpgrade0() {
		Rectangle left = new Rectangle(605, 274, 85, 180);
		return left.contains(mouseX, mouseY);
	}
	
	private boolean mouseClickedOnUpgrade1() {
		Rectangle right = new Rectangle(693, 274, 85, 180);
		return right.contains(mouseX, mouseY);
	}
	
	private boolean mouseClickedOnSell() {
		Rectangle sell = new Rectangle(605, 460, 173, 29);
		return sell.contains(mouseX, mouseY);
	}
	
	private boolean mouseClickedOnNextRound() {
		Rectangle nextRound = new Rectangle(600, 500, 182, 63);
		return nextRound.contains(mouseX, mouseY);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == t) { 
			repaint();
		}
		
	}
	
	public void mouseMoved(MouseEvent e) {
		// magic numbers that returns the mouse's real position
		mouseX = e.getX() - 6;
		mouseY = e.getY() - 29;
		hoveredMonkey = getMonkeyButtonClickedOn();	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 3) {
			monkeyToBePlaced = null;
			return;
		}
		
		// handle clicks on screengui, then monkeys
		handleSelectedMonkey();
		
		if(mouseClickedOnUpgrade0()) {
			game.tryToPurchaseUpgrade0(selectedMonkey);
		}
		
		if(mouseClickedOnUpgrade1()) {
			game.tryToPurchaseUpgrade1(selectedMonkey);
		}
		
		if(mouseClickedOnSell()) {
			game.sellMonkey(selectedMonkey);
			selectedMonkey = null;
		}
		
		if(mouseClickedOnNextRound()) {
			game.tryToAdvanceRound();
		}
		
		String buttonClicked = getMonkeyButtonClickedOn();
		if(buttonClicked != null) {
			if (game.canAffordTower(buttonClicked)) {
				monkeyToBePlaced = buttonClicked;
			}
		}
		
		if(monkeyToBePlaced != null && isValidSpotToPlace()) {
			addMonkeyToBePlaced();
		}
	}
	
	public void handleSelectedMonkey() {
		if(mouseClickedOnUpgrade0() || mouseClickedOnUpgrade1() || mouseClickedOnSell() || mouseClickedOnNextRound()) {
			// do nothing, selected monkey should still be the same
		}else {
			selectedMonkey = getMonkeyClickedOn();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// copied from stack overflow imma be honest
	// https://stackoverflow.com/a/19864657
	// draws a string at an inputted x,y with line width and automatically makes a new line, like a docs program
	public void drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y) {
	    FontMetrics m = g.getFontMetrics();
	    if(m.stringWidth(text) < lineWidth) {
	        g.drawString(text, x, y);
	    } else {
	        String[] words = text.split(" ");
	        String currentLine = words[0];
	        for(int i = 1; i < words.length; i++) {
	            if(m.stringWidth(currentLine+words[i]) < lineWidth) {
	                currentLine += " "+words[i];
	            } else {
	                g.drawString(currentLine, x, y);
	                y += m.getHeight();
	                currentLine = words[i];
	            }
	        }
	        if(currentLine.trim().length() > 0) {
	            g.drawString(currentLine, x, y);
	        }
	    }
	}
	
	// also copied from stack overflow cause I don't know how to rotate an image
	// https://stackoverflow.com/a/68926993
	// rotates a bufferedImage, returns a bufferedImage - modified to take in radians instead of degrees
	public static BufferedImage rotate(BufferedImage bimg, Double angle) {
	    double sin = Math.abs(Math.sin(angle)),
	           cos = Math.abs(Math.cos(angle));
	    int w = bimg.getWidth();
	    int h = bimg.getHeight();
	    int neww = (int) Math.floor(w*cos + h*sin),
	        newh = (int) Math.floor(h*cos + w*sin);
	    BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
	    Graphics2D graphic = rotated.createGraphics();
	    graphic.translate((neww-w)/2, (newh-h)/2);
	    graphic.rotate(angle, w/2, h/2);
	    graphic.drawRenderedImage(bimg, null);
	    graphic.dispose();
	    return rotated;
	}
	
	// copied from random github repo
	// https://github.com/nguyenq/tess4j/blob/master/src/main/java/net/sourceforge/tess4j/util/ImageHelper.java
	// returns a scaled BufferedImage of a BufferedImage, necessary because the normal getScaledInstance program returns an Image, not a BufferedImage
    public static BufferedImage getScaledInstance(BufferedImage image, int targetWidth, int targetHeight) {
        int type = (image.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage tmp = new BufferedImage(targetWidth, targetHeight, type);
        Graphics2D g2 = tmp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        g2.dispose();
        return tmp;
    }
    

}
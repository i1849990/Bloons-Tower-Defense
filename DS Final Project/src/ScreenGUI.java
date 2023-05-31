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
	
	BufferedImage map;
	private Monkey selectedMonkey;
	private String monkeyToBePlaced;
	int currFrame;
	
	long sum; int nums;
	
	public ScreenGUI() {
		JFrame frame = new JFrame("Title Name");
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
			File file;
			
			file = new File("map.png");
			map = ImageIO.read(file);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		currFrame = 0;
	}
	
	public void paint(Graphics g) {
		long time = System.currentTimeMillis();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 600);
		
		game.nextFrame();
		
		drawBackground(g);
		drawMonkeys(g);
		drawBloons(g);
		drawGUI(g);
		drawMonkeyToBePlaced(g);
		
		if (nums == 100) {
			//System.out.println(sum / 100);
			nums = 1;
			sum = (System.currentTimeMillis() - time);
		}else {
			sum += (System.currentTimeMillis() - time);
			nums++;
		}
	}
	
	public void drawBackground(Graphics g) {
		g.drawImage(map,0,0,800,600, this);
	}
	
	public void drawMonkeys(Graphics g) {
		for(Monkey m : game.monkeys) {
			// TODO: figure out how to rotate images with AffineTransform
			
			 
		}
	}
	
	public void drawBloons(Graphics g) {
		for (Bloon b : game.bloons) {
			System.out.println(b.getX() + " " + b.getY());
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
	
	public void drawMonkeyToBePlaced(Graphics g){
		if(monkeyToBePlaced == null) {
			return;
		}
		
		int width = 30;
		int height = 30;
		
		// test:
		g.setColor(Color.blue);
		g.drawRect(mouseX - width / 2, mouseY - height / 2, width, height);
		
		if(!isValidSpotToPlace()) {
			Color transparentRed = new Color(255, 0, 0, 100);
			g.setColor(transparentRed);
		}else {
			Color transparentWhite = new Color(255, 255, 255, 100);
			g.setColor(transparentWhite);
		}
		
		int radius = game.getDisplayRange(monkeyToBePlaced);
		g.fillOval(mouseX - radius, mouseY - radius, radius * 2, radius * 2);
		//g.drawImage(image, mouseX - imageWidth / 2, mouseY - imageHeight / 2, this);
	}
	
	private void addMonkeyToBePlaced() {
		Monkey m = null;
		int width = 30;
		int height = 30;
		Rectangle toBePlacedHitbox = new Rectangle(mouseX - width / 2, mouseY - height / 2, width, height);
		switch(monkeyToBePlaced) {
		case"dart":
			m = new DartMonkey(mouseX, mouseY, currFrame, toBePlacedHitbox);
			break;
		case"tack":
			m = new TackShooter(mouseX, mouseY, currFrame, toBePlacedHitbox);
			break;
		case"ice":
			m = new IceMonkey(mouseX, mouseY, currFrame, toBePlacedHitbox);
			break;
		case"bomb":
			m = new BombTower(mouseX, mouseY, currFrame, toBePlacedHitbox);
			break;
		case"super":
			m = new SuperMonkey(mouseX, mouseY, currFrame, toBePlacedHitbox);
			break;
		}
		
		game.addMonkey(m);
		selectedMonkey = m;
		monkeyToBePlaced = null;
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
			currFrame++;
			repaint();
		}
		
	}
	
	public void mouseMoved(MouseEvent e) {
		// magic numbers that returns the mouse's real position
		mouseX = e.getX() - 6;
		mouseY = e.getY() - 29;
		
		repaint();		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
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
			monkeyToBePlaced = buttonClicked;
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
	// https://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
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

}
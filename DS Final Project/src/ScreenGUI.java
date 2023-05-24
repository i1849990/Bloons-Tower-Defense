import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
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
		
		if (nums == 100) {
			System.out.println(sum / 100);
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
		drawUpgradeGUI(g);
	}
	
	public void drawUpgradeGUI(Graphics g) {
		selectedMonkey = new DartMonkey(300,300, 0);
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
		
		g.setFont(bold);
		String name = selectedMonkey.getName();
		switch(name) {
		case"Dart Monkey":
			g.drawString(name, 635, 218);
			break;
		}
		
		g.setFont(boldSmall);
		g.drawString("Speed:", 620, 250);
		g.drawString("Range:", 620, 275);
		
		g.setFont(small);
		String attackSpeedText;
		
		int range = selectedMonkey.getRange();
		g.drawString("" + range, 700, 275);
		
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
	
	private Monkey monkeyClickedOn() {
		for(Monkey m : game.monkeys) {
			if(m.getHitbox().contains(mouseX, mouseY)) {
				return m;
			}
		}
		
		return null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == t) { 
			repaint();
		}
		
	}
	
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
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
		
		if(getMonkeyButtonClickedOn() != null) {
			selectedMonkey = null;
		}else if (monkeyClickedOn() != null){
			selectedMonkey = monkeyClickedOn();
		}else {
			selectedMonkey = null;
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

}
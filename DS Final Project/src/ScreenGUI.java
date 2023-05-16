import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ScreenGUI extends JPanel implements MouseMotionListener, MouseListener, ActionListener {
	Game game;
	Timer t;
	
	public ScreenGUI(Game game) {
		JFrame frame = new JFrame("Title Name");
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		
		frame.setSize(800 + 25, 600 + 50); // handles size of the frame
		
		t = new Timer(16, this);
		t.start();
		
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 600);
		
	}
	
	public void drawMonkeys(Graphics g) {
		for(Monkey m : game.monkeys) {
			BufferedImage image = m.image;
			
			// TODO: figure out how to rotate images with AffineTransform
			
			 
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == t) { 
			repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}